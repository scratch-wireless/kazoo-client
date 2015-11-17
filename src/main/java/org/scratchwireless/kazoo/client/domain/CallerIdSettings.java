/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.api.client.util.Key;

/**
 * Defines caller ID settings based on the type of call being made.
 *
 * @author bpdavis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CallerIdSettings extends DomainObject {
   /** The caller ID used when a resource is flagged as 'emergency'. */
   @Key("emergency")
   private CallerId emergency;
   /** The default caller ID used when dialing external numbers. */
   @Key("external")
   private CallerId external;
   /** The default caller ID used when dialing internal extensions. */
   @Key("internal")
   private CallerId internal;

   @Override
   public String toString() {
      return super.toString();
   }
}
