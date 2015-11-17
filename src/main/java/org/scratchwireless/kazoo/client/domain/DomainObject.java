/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Types;

/**
 * Base class for object exchanged via REST/JSON.
 *
 * @author bpdavis
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class DomainObject extends GenericJson {

   @Override
   public String toString() {
      if (getUnknownKeys() != null) {
         for (String key : getUnknownKeys().keySet()) {
            DomainObject.log.info("{} received unknown key: {}", this.getClass().getSimpleName(), key);
         }
      }

      StringBuilder sb = new StringBuilder();
      sb.append("{");
      boolean wroteObject = false;
      for (Entry<String, Object> entry : entrySet()) {
         sb.append("\"").append(entry.getKey()).append("\"").append(":");
         if (entry.getValue() == null) {
            sb.append("null");
         } else if (entry.getValue() instanceof String) {
            sb.append("\"").append(entry.getValue()).append("\"");
         } else if (entry.getValue() instanceof Iterable<?> || entry.getValue().getClass().isArray()) {
            sb.append("[");
            boolean wroteValue = false;
            for (Object o : Types.iterableOf(entry.getValue())) {
               sb.append(String.valueOf(o));
               sb.append(",");
               wroteValue = true;
            }
            if (wroteValue) {
               sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
         } else {
            sb.append(String.valueOf(entry.getValue()));
         }
         sb.append(",");
         wroteObject = true;
      }
      if (wroteObject) {
         sb.deleteCharAt(sb.length() - 1);
      }
      sb.append("}");
      return sb.toString();
   }
}
