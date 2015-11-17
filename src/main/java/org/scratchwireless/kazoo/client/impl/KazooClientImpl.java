/*
 * Copyright (c) 2013-2014 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.impl;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.scratchwireless.kazoo.client.KazooClient;
import org.scratchwireless.kazoo.client.domain.Account;
import org.scratchwireless.kazoo.client.domain.AccountMoveTarget;
import org.scratchwireless.kazoo.client.domain.responses.AccountListResponse;
import org.scratchwireless.kazoo.client.domain.responses.AccountResponse;

import com.google.api.client.http.GenericUrl;

/**
 * TODO Add class summary here
 *
 * @author bpdavis
 */
@Slf4j
public class KazooClientImpl implements KazooClient {
   private final KazooConnection connection;
   private final String serverUrl;

   public KazooClientImpl(final String serverUrl, final String authApiKey) throws IOException {
      connection = new KazooConnection(serverUrl, authApiKey);
      connection.authorize();
      this.serverUrl = serverUrl;
   }

   public KazooClientImpl(final String serverUrl, final String authUser, final String authPassword, final String authRealm) throws IOException {
      connection = new KazooConnection(serverUrl, authUser, authPassword, authRealm);
      connection.authorize();
      this.serverUrl = serverUrl;
   }

   @Override
   public Account createAccount(final Account account) throws IOException {
      KazooClientImpl.log.debug("Creating account {}...", account);
      GenericUrl requestUrl = new GenericUrl(serverUrl + "/accounts");
      return connection.put(requestUrl, account, AccountResponse.class);
   }

   @Override
   public Account deleteAccount(final String accountId) throws IOException {
      KazooClientImpl.log.debug("Deleting account {}...", accountId);
      GenericUrl requestUrl = new GenericUrl(serverUrl + "/accounts/" + accountId);
      return connection.delete(requestUrl, AccountResponse.class);
   }

   @Override
   public Account getAccount(final String accountId) throws IOException {
      KazooClientImpl.log.debug("Getting account {}...", accountId);
      GenericUrl requestUrl = new GenericUrl(serverUrl + "/accounts/" + accountId);
      Account response = connection.get(requestUrl, AccountResponse.class);
      return response;
   }

   @Override
   public List<Account> getAccountChildren(final String accountId) throws IOException {
      KazooClientImpl.log.debug("Getting account children for {}...", accountId);
      GenericUrl requestUrl = new GenericUrl(serverUrl + "/accounts/" + accountId + "/children");
      List<Account> response = connection.get(requestUrl, AccountListResponse.class);
      return response;
   }

   @Override
   public List<Account> getAccountTree(final String accountId) throws IOException {
      KazooClientImpl.log.debug("Getting account tree for {}...", accountId);
      GenericUrl requestUrl = new GenericUrl(serverUrl + "/accounts/" + accountId + "/tree");
      List<Account> response = connection.get(requestUrl, AccountListResponse.class);
      return response;
   }

   @Override
   public KazooConnection getConnection() {
      return connection;
   }

   @Override
   public void moveAccount(final String accountId, final String newParentAccountId) throws IOException {
      KazooClientImpl.log.debug("Moving account {} to {}...", accountId, newParentAccountId);
      GenericUrl requestUrl = new GenericUrl(serverUrl + "/accounts/" + accountId + "/move");
      AccountMoveTarget target = new AccountMoveTarget();
      target.setTo(newParentAccountId);
      connection.post(requestUrl, target, AccountResponse.class);
   }

   @Override
   public Account updateAccount(final Account account) throws IOException {
      KazooClientImpl.log.debug("Updating account {}...", account);
      GenericUrl requestUrl = new GenericUrl(serverUrl + "/accounts/" + account.getId());
      Account response = connection.post(requestUrl, account, AccountResponse.class);
      return response;
   }

}
