/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.Value;

/**
 * Envelope for requests.
 *
 * @author bpdavis
 * @param <T>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class RequestEnvelope<T> extends GenericJson {
   /** Verbs */
   public enum Verbs {
      /** Delete */
      @Value("DELETE")
      DELETE,
      /** Get */
      @Value("GET")
      GET,
      /** Post */
      @Value("POST")
      POST,
      /** Put */
      @Value("PUT")
      PUT
   }

   @Key("auth_token")
   private String authToken;
   @NotNull
   @Key("data")
   private T data;
   @Key("verb")
   private Verbs verb;

   @Override
   public String toString() {
      if (super.getUnknownKeys() != null) {
         for (String key : super.getUnknownKeys().keySet()) {
            RequestEnvelope.log.warn("{} received unknown key: {}", this.getClass().getSimpleName(), key);
         }
      }

      return super.toString();
   }
}
