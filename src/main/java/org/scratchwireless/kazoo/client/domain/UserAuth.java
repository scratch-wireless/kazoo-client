/*
 * Copyright (c) 2013-2014 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.api.client.util.Key;
import com.google.api.client.util.Value;

/**
 * TODO Add class summary here
 *
 * @author bpdavis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserAuth extends DomainObject {
   /** Hash methods */
   public enum Methods {
      /** MD5 hash method */
      @Value("md5")
      MD5,
      /** SHA hash method */
      @Value("sha")
      SHA
   }

   @Key("account_id")
   private String accountId;
   @Key("account_name")
   private String accountName;
   @Key("account_realm")
   private String accountRealm;
   @Key("credentials")
   private String credentials;
   @Key("method")
   private String method;
   @Key("phone_number")
   private String phoneNumber;

   public void setMethod(final Methods method) {
      this.method = method.toString();
   }

   @Override
   public String toString() {
      return super.toString();
   }
}
