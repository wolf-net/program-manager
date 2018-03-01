package ro.wolfnet.programmanager.service.generate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
  public List<EmployeeStatusModel> filterEmployees(long stationId, List<EmployeeStatusModel> employees) {
    if (employees == null) {
      return null;
    }

    Collections.sort(employees, new Comparator<EmployeeStatusModel>() {
      @Override
      public int compare(EmployeeStatusModel o1, EmployeeStatusModel o2) {
        return new Integer(o1.getWorkedHours()).compareTo(new Integer(o2.getWorkedHours()));
      }
    });

    int lastIdx = getLatsLessWorkedIndex(employees);
    return employees.subList(0, lastIdx);
  }

  /**
   * Gets the lats less worked index.
   *
   * @param allEmployees the all employees
   * @return the lats less worked index
   */
  private int getLatsLessWorkedIndex(List<EmployeeStatusModel> allEmployees) {
    if (allEmployees == null || allEmployees.size() == 0) {
      return 0;
    }

    int lessWorkingHour = allEmployees.get(0).getWorkedHours();
    int index = 0;
    while (index < allEmployees.size() && allEmployees.get(index).getWorkedHours() == lessWorkingHour) {
      index++;
    }
    return index;
  }

}
