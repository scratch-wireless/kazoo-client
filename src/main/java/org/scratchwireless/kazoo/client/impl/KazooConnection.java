/*
 * Copyright (c) 2013-2014 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.digest.DigestUtils;
import org.scratchwireless.kazoo.client.Filter;
import org.scratchwireless.kazoo.client.domain.Account;
import org.scratchwireless.kazoo.client.domain.ApiAuth;
import org.scratchwireless.kazoo.client.domain.RequestEnvelope;
import org.scratchwireless.kazoo.client.domain.ResponseEnvelope;
import org.scratchwireless.kazoo.client.domain.UserAuth;
import org.scratchwireless.kazoo.client.domain.responses.AccountResponse;
import org.scratchwireless.kazoo.client.domain.responses.ApiAuthResponse;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandler;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;

/**
 * TODO Add class summary here
 *
 * @author bpdavis
 */
@Slf4j
public class KazooConnection {
   private static final class JsonHttpRequestInitializer implements HttpRequestInitializer {
      @Override
      public void initialize(final HttpRequest request) {
         request.setParser(new JsonObjectParser(KazooConnection.JSON_FACTORY));
      }
   }

   private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
   private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

   private static final String generateCredentialsHash(final String username, final String password) {
      String rawCredentials = username + ":" + password;
      return DigestUtils.md5Hex(rawCredentials);
   }

   /**
    * Main method, for testing purposes.
    *
    * @param args
    * @throws IOException
    */
   public static void main(final String[] args) throws IOException {
      // Try to connect
      KazooConnection connection = new KazooConnection("http://192.168.100.109:8000/v1", "admin", "admin1234", "sip.scratch.com");
      connection.authorize();
      System.out.println("token=" + connection.authToken);

      // Test fetching account information
      // final String accountId = "1e48496709b63dc388aac1837724bf89";
      // Account account = connection.getAccount(accountId);
      // System.out.println("Account: " + account.toPrettyString());
   }

   private final String authApiKey;
   private final String authPassword;
   private final String authRealm;
   private String authToken;
   private final String authUser;
   private Account loggedInAccount;
   private HttpRequestFactory requestFactory = KazooConnection.HTTP_TRANSPORT.createRequestFactory(new JsonHttpRequestInitializer());
   private final String serverUrl;

   /**
    * Constructor for use with API key authentication method.
    *
    * @param serverUrl
    * @param authApiKey
    */
   public KazooConnection(final String serverUrl, final String authApiKey) {
      this.serverUrl = serverUrl;
      this.authApiKey = authApiKey;
      authPassword = null;
      authRealm = null;
      authUser = null;
   }

   /**
    * Constructor for user with user authentication method.
    *
    * @param serverUrl
    * @param authUser
    * @param authPassword
    * @param authRealm
    */
   public KazooConnection(final String serverUrl, final String authUser, final String authPassword, final String authRealm) {
      this.serverUrl = serverUrl;
      this.authUser = authUser;
      this.authPassword = authPassword;
      this.authRealm = authRealm;
      authApiKey = null;
   }

   /**
    * Authorize this connection (or re-authorize).
    *
    * @throws IOException
    */
   public void authorize() throws IOException {
      // Choose whether to use API key or User authorization
      GenericUrl requestUrl;
      // DomainObject authPayload;
      // Class<? extends DomainObject> authPayloadType;
      if (authApiKey != null) {
         ApiAuth apiAuth = new ApiAuth();
         apiAuth.setApiKey(authApiKey);
         requestUrl = new GenericUrl(serverUrl + "/api_auth");
         // authPayload = apiAuth;
         // authPayloadType = ApiAuth.class;

         // Send the Auth request
         put(requestUrl, apiAuth, ApiAuthResponse.class);
      } else {
         UserAuth userAuth = new UserAuth();
         String credentials = KazooConnection.generateCredentialsHash(authUser, authPassword);
         userAuth.setCredentials(credentials);
         userAuth.setAccountRealm(authRealm);
         requestUrl = new GenericUrl(serverUrl + "/user_auth");
         // authPayload = userAuth;
         // authPayloadType = UserAuth.class;

         // Send the Auth request
         loggedInAccount = put(requestUrl, userAuth, AccountResponse.class);
         KazooConnection.log.debug("Received auth response: {}", loggedInAccount);
      }

      // Send the Auth request
      // put(requestUrl, authPayload, authPayloadType);
   }

   /**
    * Send a DELETE request. Deletes a resource.
    *
    * @param <T>
    * @param requestUrl
    * @param responseType
    * @return deleted object.
    * @throws IOException
    */
   public <U, T extends ResponseEnvelope<U>> U delete(final GenericUrl requestUrl, final Class<T> responseType) throws IOException {
      return delete(requestUrl, responseType, null);
   }

   /**
    * Send a DELETE request. Deletes a resource.
    *
    * @param <T>
    * @param requestUrl
    * @param responseType
    * @param filter
    * @return deleted object.
    * @throws IOException
    */
   public <U, T extends ResponseEnvelope<U>> U delete(final GenericUrl requestUrl, final Class<T> responseType, final Filter filter) throws IOException {
      if (filter != null) {
         Map<String, String> queryParams = filter.getQueryParams();
         for (Entry<String, String> queryParam : queryParams.entrySet()) {
            requestUrl.put(queryParam.getKey(), queryParam.getValue());
         }
      }
      HttpRequest httpRequest = requestFactory.buildDeleteRequest(requestUrl);
      return execute(httpRequest, responseType);
   }

   private <U, T extends ResponseEnvelope<U>> U execute(final HttpRequest httpRequest, final Class<T> responseType) throws IOException {
      httpRequest.setUnsuccessfulResponseHandler(new HttpBackOffUnsuccessfulResponseHandler(new ExponentialBackOff()));
      if (authToken != null) {
         HttpHeaders headers = httpRequest.getHeaders();
         headers.set("X-Auth-Token", authToken);
      }

      HttpResponse httpResponse = httpRequest.execute();
      T response = httpResponse.parseAs(responseType);

      // Update authToken, if necessary
      if (response.getAuthToken() != null) {
         authToken = response.getAuthToken();
      }

      return response.getData();
   }

   /**
    * Send a GET request. Retrieves either a collection of resources or a single resource.
    *
    * @param <U>
    * @param <T>
    * @param requestUrl
    * @param responseType
    * @return fetched object.
    * @throws IOException
    */
   public <U, T extends ResponseEnvelope<U>> U get(final GenericUrl requestUrl, final Class<T> responseType) throws IOException {
      return get(requestUrl, responseType, null);
   }

   /**
    * Send a GET request. Retrieves either a collection of resources or a single resource.
    *
    * @param <U>
    * @param <T>
    * @param requestUrl
    * @param responseType
    * @param filter
    * @return fetched object.
    * @throws IOException
    */
   public <U, T extends ResponseEnvelope<U>> U get(final GenericUrl requestUrl, final Class<T> responseType, final Filter filter) throws IOException {
      if (filter != null) {
         Map<String, String> queryParams = filter.getQueryParams();
         for (Entry<String, String> queryParam : queryParams.entrySet()) {
            requestUrl.put(queryParam.getKey(), queryParam.getValue());
         }
      }
      HttpRequest httpRequest = requestFactory.buildGetRequest(requestUrl);
      return execute(httpRequest, responseType);
   }

   /**
    * @return
    */
   public String getAuthToken() {
      return authToken;
   }

   public Account getLoggedInAccount() {
      return loggedInAccount;
   }

   /**
    * Send a POST request. Updates a resource by completely replacing it with the provided data.
    *
    * @param <S>
    * @param <U>
    * @param <T>
    * @param requestUrl
    * @param payload
    * @param payloadType
    * @return updated object.
    * @throws IOException
    */
   public <S, U, T extends ResponseEnvelope<U>> U post(final GenericUrl requestUrl, final S payload, final Class<T> payloadType) throws IOException {
      RequestEnvelope<S> request = new RequestEnvelope<S>();
      request.setData(payload);
      HttpRequest httpRequest = requestFactory.buildPostRequest(requestUrl, new JsonHttpContent(KazooConnection.JSON_FACTORY, request));
      return execute(httpRequest, payloadType);
   }

   /**
    * Send a PUT request. Creates a new resource with the provided data.
    *
    * @param <S>
    * @param <U>
    * @param <T>
    * @param requestUrl
    * @param payload
    * @param payloadType
    * @return new object.
    * @throws IOException
    */
   public <S, U, T extends ResponseEnvelope<U>> U put(final GenericUrl requestUrl, final S payload, final Class<T> payloadType) throws IOException {
      RequestEnvelope<S> request = new RequestEnvelope<S>();
      request.setData(payload);
      HttpRequest httpRequest = requestFactory.buildPutRequest(requestUrl, new JsonHttpContent(KazooConnection.JSON_FACTORY, request));
      U response = execute(httpRequest, payloadType);
      return response;
   }
}
