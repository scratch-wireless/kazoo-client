/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client;

import java.util.Date;

/**
 * Helper class that provides utility methods for dealing with gregorian dates
 * used in Kazoo.
 *
 * @author aheiner
 */
public class DateHelper {
   private static final long EPOCH_JANUARY_ONE_NINETEEN_SEVENTY_SECONDS = 62167219200l;
   private static final long MILLIS_PER_SECOND = 1000;

   /**
    * Method that converts a standard java date object to gregorian seconds as
    * stored in Kazoo.
    *
    * @param date
    * @return the given Date in Kazoo time.
    */
   public static long dateToGregorianSeconds(final Date date) {
      return date.getTime() / DateHelper.MILLIS_PER_SECOND + DateHelper.EPOCH_JANUARY_ONE_NINETEEN_SEVENTY_SECONDS;
   }

   /**
    * Method that converts a date returned from Kazoo in gregorian seconds to a
    * standard java date object.
    *
    * @param gregorianSeconds
    * @return the given Kazoo date/time as a Date.
    */
   public static Date gregorianSecondsToDate(final long gregorianSeconds) {
      long dateMillis = (gregorianSeconds - DateHelper.EPOCH_JANUARY_ONE_NINETEEN_SEVENTY_SECONDS) * DateHelper.MILLIS_PER_SECOND;
      return new Date(dateMillis);
   }
}
