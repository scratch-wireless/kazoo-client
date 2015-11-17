/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.api.client.util.Key;

/**
 * Caller ID Name/Number.
 *
 * @author bpdavis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CallerId extends DomainObject {
   /** The caller id name */
   @Key("name")
   @Size(max = 35)
   private String name;
   /** The caller id number */
   @Key("number")
   @Size(max = 35)
   private String number;

   @Override
   public String toString() {
      return super.toString();
   }
}
