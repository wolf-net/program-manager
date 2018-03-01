package ro.wolfnet.programmanager.service.generate;

import java.util.List;

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
   *
   * @param employees the employees
   * @return the list
   */
  public List<EmployeeStatusModel> filterEmployees(long stationId, List<EmployeeStatusModel> employees);
}
