package ro.wolfnet.programmanager.utils;

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

  public static long getDateDifference(Date start, Date end, TimeUnit timeUnit) {
	long diffInMillies = end.getTime() - start.getTime();
	return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
  }

}
