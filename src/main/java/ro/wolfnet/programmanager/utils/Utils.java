package ro.wolfnet.programmanager.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The Class Utils.
 *
 * @author isti
 * @since Feb 28, 2018
 */
public class Utils {

  /**
   * Gets the int attribute.
   *
   * @param attribute the attribute
   * @return the int attribute
   */
  public static int getIntAttribute(Object attribute) {
    int returnedValue = 0;
    try {
      returnedValue = Integer.parseInt(attribute.toString());
    } catch (NumberFormatException | NullPointerException e) {
    }
    return returnedValue;
  }

  /**
   * Checks if is null or empty.
   *
   * @param s the s
   * @return the boolean
   */
  public static Boolean isNullOrEmpty(String s) {
    return (s == null || s.trim().length() < 1) ? true : false;
  }

  /**
   * Gets the date difference.
   *
   * @param start the start
   * @param end the end
   * @param timeUnit the time unit
   * @return the date difference
   */
  public static long getDateDifference(Date start, Date end, TimeUnit timeUnit) {
    long diffInMillies = end.getTime() - start.getTime();
    return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
  }

  /**
   * Gets the day of month numbers.
   *
   * @param date the date
   * @return the day of month numbers
   */
  public static int getDayOfMonthNumbers(Date date) {
    Calendar mycal = Calendar.getInstance();
    mycal.setTime(date);
    return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  /**
   * Gets the date from beginning of month.
   *
   * @param date the date
   * @return the date from beginning of month
   */
  public static Date getDateFromBeginningOfMonth(Date date) {
    Calendar mycal = Calendar.getInstance();
    mycal.setTime(date);
    mycal.set(Calendar.DAY_OF_MONTH, 1);
    return mycal.getTime();
  }

  /**
   * Gets the date at end of month.
   *
   * @param date the date
   * @return the date at end of month
   */
  public static Date getDateAtEndOfMonth(Date date) {
    Calendar mycal = Calendar.getInstance();
    mycal.setTime(date);
    mycal.set(Calendar.DAY_OF_MONTH, mycal.getActualMaximum(Calendar.DAY_OF_MONTH));
    return mycal.getTime();
  }

}
