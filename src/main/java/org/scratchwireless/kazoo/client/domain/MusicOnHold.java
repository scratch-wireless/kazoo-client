/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.api.client.util.Key;

/**
 * The default music on hold parameters.
 *
 * @author bpdavis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MusicOnHold extends DomainObject {
   /** The ID of a media object that should be used as the default music on hold. */
   @Size(max = 128)
   @Key("media_id")
   private String mediaId;

   @Override
   public String toString() {
      return super.toString();
   }
}
