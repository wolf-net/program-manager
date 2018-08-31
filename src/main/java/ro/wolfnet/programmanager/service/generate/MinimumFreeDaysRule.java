package ro.wolfnet.programmanager.service.generate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.utils.Utils;

/**
 * The Class MinimumFreeDaysRule.
 *
 * @author isti
 * @since Aug 30, 2018
 */
@Component
public class MinimumFreeDaysRule implements GenerateRule {

  /* (non-Javadoc)
   * @see ro.wolfnet.programmanager.service.generate.GenerateRule#filterEmployees(long, java.util.Date, java.util.List, java.util.List)
   */
  @Override
  public List<EmployeeStatusModel> filterEmployees(long stationId, Date date, List<EmployeeStatusModel> employees, List<RuleBaseEntity> rules) {
    if (employees == null || employees.size() == 0) {
      return null;
    }

    List<EmployeeStatusModel> filteredEmployees = new ArrayList<>();
    for (EmployeeStatusModel employee : employees) {
      if (employee.getLastWorked() == null || Utils.getDateDifference(employee.getLastWorked(), date, TimeUnit.HOURS) > 24) {
        filteredEmployees.add(employee);
      }
    }
    return filteredEmployees;
  }

}
