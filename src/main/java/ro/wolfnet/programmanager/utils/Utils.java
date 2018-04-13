package ro.wolfnet.programmanager.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
  
  public static Date getMaximum(Date d1, Date d2) {
      if (d1 == null && d2 == null) return null;
      if (d1 == null) return d2;
      if (d2 == null) return d1;
      return (d1.after(d2)) ? d1 : d2;
  }
  
  /** 
   * Returns the minimum of two dates. A null date is treated as being greater
   * than any non-null date. 
   */
  public static Date getMinimum(Date d1, Date d2) {
      if (d1 == null && d2 == null) return null;
      if (d1 == null) return d2;
      if (d2 == null) return d1;
      return (d1.before(d2)) ? d1 : d2;
  }

  public static List<Date> getDatesBetweenTwoDates(Date calcStart, Date calcEnd) {
	List<Date> dates = new ArrayList<>();
	if (calcStart == null || calcEnd == null || calcStart.after(calcEnd)) {
		return dates;
	}
	Calendar cal = Calendar.getInstance();
	cal.setTime(calcStart);
	while (!cal.getTime().after(calcEnd)) {
		dates.add(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 1);
	}
	dates.add(cal.getTime());
	return dates;
  }

  public static boolean areDatesContainDate(List<Date> dates, Date date) {
	if (dates == null || date == null) {
		return false;
	}
	
	Calendar dateCal = Calendar.getInstance();
	dateCal.setTime(date);
	for (Date currentDate:dates) {
		Calendar currentDateCal = Calendar.getInstance();
		currentDateCal.setTime(currentDate);
		if (dateCal.get(Calendar.YEAR) == currentDateCal.get(Calendar.YEAR) &&
				dateCal.get(Calendar.DAY_OF_YEAR) == currentDateCal.get(Calendar.DAY_OF_YEAR)) {
			return true;
		}
	}
	return false;
  }

  public static Date getDateFromBeginningOfDay(Date date) {
	if (date == null) {
		return null;
	}
	
	Calendar res = Calendar.getInstance();
	res.setTime(date);
	res.set(Calendar.HOUR_OF_DAY, 0);
	res.set(Calendar.MINUTE, 0);
	res.set(Calendar.SECOND, 0);
	res.set(Calendar.MILLISECOND, 0);
	return res.getTime();
  }

}
