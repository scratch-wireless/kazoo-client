/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Filter for selecting records.
 *
 * @author bpdavis
 */
public class Filter {
   /**
    * Does the key exist on the document.
    */
   public static final String VALUE_HAS_KEY = "has_key";
   /**
    * Is the value not an empty value.
    */
   public static final String VALUE_HAS_VALUE = "has_value";
   /**
    * Is the key not on the document.
    */
   public static final String VALUE_KEY_MISSING = "key_missing";

   private Map<String, String> queryParams = new HashMap<String, String>();

   /**
    * Return results where the key does not have the specified value.
    *
    * @param key
    * @param value
    */
   public void addInverseKeyFilter(final String key, final String value) {
      queryParams.put("filter_not_" + key, value);
   }

   /**
    * Return results where the subkey does not have the specified value.
    *
    * @param key
    * @param subkey
    * @param value
    */
   public void addInverseSubKeyFilter(final String key, final String subkey, final String value) {
      queryParams.put("filter_not_" + key + "." + subkey, value);
   }

   /**
    * Return results where the key has the specified value.
    *
    * @param key
    * @param value
    */
   public void addKeyFilter(final String key, final String value) {
      queryParams.put("filter_" + key, value);
   }

   /**
    * Return results where the subkey has the specified value.
    *
    * @param key
    * @param subkey
    * @param value
    */
   public void addSubKeyFilter(final String key, final String subkey, final String value) {
      queryParams.put("filter_" + key + "." + subkey, value);
   }

   /**
    * @return the map of query params.
    */
   public Map<String, String> getQueryParams() {
      return queryParams;
   }

   /**
    * Return results created before the given date.
    *
    * @param createdDateEnd
    */
   public void setCreatedDateEnd(final Date createdDateEnd) {
      queryParams.put("created_to", String.valueOf(DateHelper.dateToGregorianSeconds(createdDateEnd)));
   }

   /**
    * Return results created after the given date.
    *
    * @param createdDateStart
    */
   public void setCreatedDateStart(final Date createdDateStart) {
      queryParams.put("created_from", String.valueOf(DateHelper.dateToGregorianSeconds(createdDateStart)));
   }

   /**
    * Return results modified before the given date.
    *
    * @param modifiedDateEnd
    */
   public void setModifiedDateEnd(final Date modifiedDateEnd) {
      queryParams.put("modified_to", String.valueOf(DateHelper.dateToGregorianSeconds(modifiedDateEnd)));
   }

   /**
    * Return results modified after the given date.
    *
    * @param modifiedDateStart
    */
   public void setModifiedDateStart(final Date modifiedDateStart) {
      queryParams.put("modified_from", String.valueOf(DateHelper.dateToGregorianSeconds(modifiedDateStart)));
   }
}
