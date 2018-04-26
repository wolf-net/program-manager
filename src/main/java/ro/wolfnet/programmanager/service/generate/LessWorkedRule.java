package ro.wolfnet.programmanager.service.generate;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.utils.Utils;

/**
 * The Class LessWorkedRule.
 *
 * @author isti
 * @since Mar 1, 2018
 */
@Component
public class LessWorkedRule implements GenerateRule {

  /* (non-Javadoc)
   * @see ro.wolfnet.programmanager.service.generate.GenerateRule#filterEmployees(java.util.List)
   */
  @Override
  public List<EmployeeStatusModel> filterEmployees(long stationId, Date date, List<EmployeeStatusModel> employees, List<RuleBaseEntity> rules) {
    if (employees == null) {
      return null;
    }

    employees = equalizeWorkedHourMinusesForWholeMonth(date, employees);

    Collections.sort(employees, new Comparator<EmployeeStatusModel>() {
      @Override
      public int compare(EmployeeStatusModel o1, EmployeeStatusModel o2) {
        int res = new Double(o1.getWorkedHours()).compareTo(new Double(o2.getWorkedHours()));
        if (res == 0) {
          res = getDate(o1.getLastWorked()).compareTo(getDate(o2.getLastWorked()));
        }
        return res;
      }
    });

    int lastIdx = getLastWorkedIndex(employees);
    return employees.subList(0, lastIdx);
  }

  /**
   * Equalize worked hour minuses for whole month.
   *
   * @param date the date
   * @param employees the employees
   * @return the list
   */
  private List<EmployeeStatusModel> equalizeWorkedHourMinusesForWholeMonth(Date date, List<EmployeeStatusModel> employees) {
    if (date == null || employees == null || employees.size() == 0) {
      return employees;
    }

    Date start = Utils.getDateFromBeginningOfMonth(date);
    Date end = Utils.getDateAtEndOfMonth(date);
    long numberOfTotalWorkDays = getVacationDays(start, end);
    long numberOfLeftWorkDays = getVacationDays(date, end);
    double maxWorkedHours = getMaximumOlderWorkedHours(employees);
    for (int i = 0; i < employees.size(); i++) {
      EmployeeStatusModel employee = employees.get(i);
      double differenceBetweenMeAndMax = maxWorkedHours - employee.getOlderWorkedHours();
      double auxWorkedHours = differenceBetweenMeAndMax / numberOfTotalWorkDays * numberOfLeftWorkDays;
      employee.setWorkedHours(employee.getWorkedHours() + auxWorkedHours);
    }

    return employees;
  }

  /**
   * Gets the maximum older worked hours.
   *
   * @param employees the employees
   * @return the maximum older worked hours
   */
  private double getMaximumOlderWorkedHours(List<EmployeeStatusModel> employees) {
    if (employees == null || employees.size() == 0) {
      return 0;
    }

    EmployeeStatusModel maxOldWorkedEmployee = Collections.max(employees, Comparator.comparing(e -> e.getOlderWorkedHours()));
    return maxOldWorkedEmployee.getOlderWorkedHours();
  }

  /**
   * Gets the vacation days.
   *
   * @param calcStart the calc start
   * @param calcEnd the calc end
   * @return the vacation days
   */
  private long getVacationDays(Date calcStart, Date calcEnd) {
    if (calcStart == null || calcEnd == null || calcStart.after(calcEnd)) {
      return 0;
    }
    long diff = Utils.getDateDifference(calcStart, calcEnd, TimeUnit.DAYS);
    if (calcStart.getTime() != calcEnd.getTime()) {
      diff++;
    }
    return diff;
  }

  /**
   * Gets the lats less worked index.
   *
   * @param allEmployees the all employees
   * @return the lats less worked index
   */
  private int getLastWorkedIndex(List<EmployeeStatusModel> allEmployees) {
    if (allEmployees == null || allEmployees.size() == 0) {
      return 0;
    }

    double lessWorkingHour = allEmployees.get(0).getWorkedHours();
    Date lastWorked = getDate(allEmployees.get(0).getLastWorked());
    int index = 0;
    while (index < allEmployees.size() && allEmployees.get(index).getWorkedHours() == lessWorkingHour &&
      getDate(allEmployees.get(index).getLastWorked()).equals(lastWorked)) {
      index++;
    }
    return index;
  }

  /**
   * Gets the date.
   *
   * @param date the date
   * @return the date
   */
  private Date getDate(Date date) {
    if (date == null) {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      return calendar.getTime();
    }
    return date;
  }

}
