/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.api.client.util.Key;

/**
 * Provides an auth-token via an Account API key.
 *
 * @author bpdavis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiAuth extends DomainObject {
   /** The Accounts API key. */
   @Key("api_key")
   @NotNull
   @Size(min = 64, max = 64)
   private String apiKey;

   @Override
   public String toString() {
      return super.toString();
   }
}
