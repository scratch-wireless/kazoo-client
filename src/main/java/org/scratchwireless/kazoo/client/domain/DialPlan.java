/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.api.client.util.Key;

/**
 * A list of default rules used to modify dialed numbers.
 *
 * @author bpdavis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DialPlan extends DomainObject {
   /** Friendly name for this regex dialplan. */
   @Key("description")
   private String description;
   /** Prefix the prepend to the capture group after applying the regex. */
   @Key("prefix")
   private String prefix;
   /** Suffix the append to the capture group after applying the regex. */
   @Key("suffix")
   private String suffix;

   @Override
   public String toString() {
      return super.toString();
   }
}
