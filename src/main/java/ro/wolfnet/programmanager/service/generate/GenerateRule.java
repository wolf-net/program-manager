package ro.wolfnet.programmanager.service.generate;

import java.util.Date;
import java.util.List;

import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;

/**
 * The Interface GenerateRule.
 *
 * @author isti
 * @since Mar 1, 2018
 */
public interface GenerateRule {

  /**
   * Filter employees.
 * @param date 
   *
   * @param employees the employees
 * @param rules 
   * @return the list
   */
  public List<EmployeeStatusModel> filterEmployees(long stationId, Date date, List<EmployeeStatusModel> employees, List<RuleBaseEntity> rules);
}
