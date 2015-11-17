/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.impl;

import java.util.logging.Handler;
import java.util.logging.Logger;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Programmatic configuration to bridge Java Logging to SLF4J. Should be referenced by system property, like:
 * <code>-Djava.util.logging.config.class=org.scratchwireless.kazoo.client.impl.LoggingConfig</code>
 *
 * @author bpdavis
 */
public class LoggingConfig {

   private static java.util.logging.Logger getRootLogger() {
      return Logger.getLogger("");
   }

   /**
    * Constructor.
    */
   public LoggingConfig() {
      boolean bridgeJULtoSLF4J = ConfigManager.getInstance().getBoolean("logging.bridgeJULtoSLF4J", true);

      if (bridgeJULtoSLF4J) {
         try {
            // Remove existing handlers
            Logger rootLogger = LoggingConfig.getRootLogger();
            Handler[] handlers = rootLogger.getHandlers();
            for (Handler handler : handlers) {
               rootLogger.removeHandler(handler);
            }

            // Install bridge handler as only handler
            SLF4JBridgeHandler bridgeToSlf4j = new SLF4JBridgeHandler();
            rootLogger.addHandler(bridgeToSlf4j);
         } catch (Exception e) {
            System.err.println("Unable to configure java logging due to exception: " + e.getMessage());
            e.printStackTrace();
         }
      }
   }
}
