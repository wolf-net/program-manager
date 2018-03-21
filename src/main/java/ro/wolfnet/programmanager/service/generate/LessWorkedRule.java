package ro.wolfnet.programmanager.service.generate;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;

/**
 * The Class LessWorkedRule.
 *
 * @author isti
 * @since Mar 1, 2018
 */
public class LessWorkedRule implements GenerateRule {

  /* (non-Javadoc)
   * @see ro.wolfnet.programmanager.service.generate.GenerateRule#filterEmployees(java.util.List)
   */
  @Override
  public List<EmployeeStatusModel> filterEmployees(long stationId, Date date, List<EmployeeStatusModel> employees, List<RuleBaseEntity> rules) {
    if (employees == null) {
      return null;
    }

    Collections.sort(employees, new Comparator<EmployeeStatusModel>() {
      @Override
      public int compare(EmployeeStatusModel o1, EmployeeStatusModel o2) {
        int res = new Integer(o1.getWorkedHours()).compareTo(new Integer(o2.getWorkedHours()));
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
   * Gets the lats less worked index.
   *
   * @param allEmployees the all employees
   * @return the lats less worked index
   */
  private int getLastWorkedIndex(List<EmployeeStatusModel> allEmployees) {
    if (allEmployees == null || allEmployees.size() == 0) {
      return 0;
    }

    int lessWorkingHour = allEmployees.get(0).getWorkedHours();
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
