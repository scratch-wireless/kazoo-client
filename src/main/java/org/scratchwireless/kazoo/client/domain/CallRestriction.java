/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
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
public class CallRestriction extends DomainObject {
   /** Hash methods */
   public enum RestrictionActions {
      /** Allow all calls of this type */
      @Value("allow")
      ALLOW,
      /** Reject all calls of this type */
      @Value("deny")
      DENY,
      /** Inherit deny/allow value */
      @Value("inherit")
      INHERIT
   }

   @Key("action")
   private String action;

   public void setAction(final RestrictionActions action) {
      this.action = action.toString();
   }

   @Override
   public String toString() {
      return super.toString();
   }
}
