/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.Value;

/**
 * Envelope for responses.
 *
 * @author bpdavis
 * @param <T>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class ResponseEnvelope<T> extends GenericJson {
   /** Status values */
   public enum Statuses {
      /** Error */
      @Value("error")
      ERROR,
      /** Failure */
      @Value("failure")
      FAILURE,
      /** Success */
      @Value("success")
      SUCCESS
   }

   @Key("auth_token")
   private String authToken;
   @Key("data")
   private T data;
   @Key("error")
   private Integer error;
   @Key("message")
   private String message;
   @Key("page_size")
   private Integer pageSize;
   @Key("request_id")
   private String requestId;
   @Key("revision")
   private String revision;
   @Key("status")
   private String status;

   @Override
   public String toString() {
      if (super.getUnknownKeys() != null) {
         for (String key : super.getUnknownKeys().keySet()) {
            ResponseEnvelope.log.warn("{} received unknown key: {}", this.getClass().getSimpleName(), key);
         }
      }

      return super.toString();
   }
}
