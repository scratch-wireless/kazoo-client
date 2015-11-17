/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.api.client.util.Key;

/**
 * TODO Add class summary here
 *
 * @author bpdavis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountMoveTarget extends DomainObject {
   @Key("to")
   private String to;

   @Override
   public String toString() {
      return super.toString();
   }
}
