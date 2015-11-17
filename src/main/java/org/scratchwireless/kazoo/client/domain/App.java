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
public class App extends DomainObject {
   @Key("api_url")
   private String apiUrl;
   @Key("id")
   private String id;
   @Key("label")
   private String label;
   @Key("name")
   private String name;

   @Override
   public String toString() {
      return super.toString();
   }
}
