package ro.wolfnet.programmanager.service.generate;

import java.util.List;
import java.util.stream.Collectors;

import ro.wolfnet.programmanager.model.EmployeeStatusModel;

/**
 * The Class AssignedStationsRule.
 *
 * @author isti
 * @since Mar 1, 2018
 */
public class AssignedStationsRule implements GenerateRule {

  /* (non-Javadoc)
   * @see ro.wolfnet.programmanager.service.generate.GenerateRule#filterEmployees(java.util.List)
   */
  @Override
  public List<EmployeeStatusModel> filterEmployees(long stationId, List<EmployeeStatusModel> employees) {
    if (employees == null) {
      return null;
    }

    return employees.stream().filter(employee -> canEmployeeWorkInStation(stationId, employee)).collect(Collectors.toList());
  }

  /**
   * Can employee work in station.
   *
   * @param stationId the station id
   * @param employee the employee
   * @return true, if successful
   */
  private boolean canEmployeeWorkInStation(long stationId, EmployeeStatusModel employee) {
    if (employee == null || employee.getStationIds() == null) {
      return false;
    }

    for (long employeeStationId : employee.getStationIds()) {
      if (stationId == employeeStationId) {
        return true;

      }
    }
    return false;
  }

}
