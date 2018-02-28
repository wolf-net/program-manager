package ro.wolfnet.programmanager.utils;

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

}
