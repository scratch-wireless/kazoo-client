/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.api.client.util.Key;

/**
 * Ringtone Parameters.
 *
 * @author bpdavis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RingTones extends DomainObject {
   /** The alert info SIP header added when the call is from external sources */
   @Key("external")
   @Size(max = 256)
   private String external;
   /** The alert info SIP header added when the call is from internal sources */
   @Key("internal")
   @Size(max = 256)
   private String internal;

   @Override
   public String toString() {
      return super.toString();
   }
}
