/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.impl;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.logging.LogFactory;

/**
 * Configuration management class, for reading properties, etc.
 *
 * @author bpdavis
 */
public class ConfigManager {
   private static final ConfigManager instance = new ConfigManager();

   /**
    * @return the singleton instance of the ConfigManager.
    */
   public static final ConfigManager getInstance() {
      return ConfigManager.instance;
   }

   private XMLConfiguration configuration;

   /**
    * Constructor.
    */
   ConfigManager() {
      // Load configuration
      configuration = loadConfiguration();
   }

   /**
    * @param key
    * @param defaultValue
    * @return the value of the given key, or the default value if not defined.
    */
   public boolean getBoolean(final String key, final boolean defaultValue) {
      return configuration.getBoolean(key, defaultValue);
   }

   /**
    * @param key
    * @param defaultValue
    * @return the value of the given key, or the default value if not defined.
    */
   public float getFloat(final String key, final float defaultValue) {
      return configuration.getFloat(key, defaultValue);
   }

   /**
    * @param key
    * @param defaultValue
    * @return the value of the given key, or the default value if not defined.
    */
   public int getInteger(final String key, final int defaultValue) {
      return configuration.getInt(key, defaultValue);
   }

   /**
    * @param key
    * @param defaultValue
    * @return the value of the given key, or the default value if not defined.
    */
   public long getLong(final String key, final long defaultValue) {
      return configuration.getLong(key, defaultValue);
   }

   /**
    * @param key
    * @param defaultValue
    * @return the value of the given key, or the default value if not defined.
    */
   public String getString(final String key, final String defaultValue) {
      return configuration.getString(key, defaultValue);
   }

   /**
    * @return a fresh copy of the configuration.
    */
   private XMLConfiguration loadConfiguration() {
      XMLConfiguration config = null;
      try {
         config = new XMLConfiguration("config.xml");
         FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
         strategy.setRefreshDelay(10000L);
         config.setReloadingStrategy(strategy);
         config.setLogger(LogFactory.getLog(ConfigManager.class));
      } catch (Exception e) {
         System.err.println("Unable to read configuration: " + e.getMessage());
         e.printStackTrace();
      }
      return config;
   }
}
