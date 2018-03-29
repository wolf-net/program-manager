package ro.wolfnet.programmanager.service.generate;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.wolfnet.programmanager.entity.EmployeeEntity;
import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.entity.RuleVacationEntity;
import ro.wolfnet.programmanager.entity.StationEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.service.RuleService;

/**
 * The Class AssignedStationsRule.
 *
 * @author isti
 * @since Mar 1, 2018
 */
@Component
public class AssignedStationsRule implements GenerateRule {

  /** The rule service. */
  @Autowired
  private RuleService ruleService;

  /* (non-Javadoc)
   * @see ro.wolfnet.programmanager.service.generate.GenerateRule#filterEmployees(java.util.List)
   */
  @Override
  public List<EmployeeStatusModel> filterEmployees(long stationId, Date date, List<EmployeeStatusModel> employees, List<RuleBaseEntity> rules) {
    if (employees == null) {
      return null;
    }

    return employees.stream().filter(employee -> canEmployeeWorkInStation(stationId, date, employee, rules)).collect(Collectors.toList());
  }

  /**
   * Can employee work in station.
   *
   * @param stationId the station id
 * @param date 
   * @param employee the employee
   * @param rules the rules
   * @return true, if successful
   */
  private boolean canEmployeeWorkInStation(long stationId, Date date, EmployeeStatusModel employee, List<RuleBaseEntity> rules) {
    if (isEmployeePrimaryAssignedToStation(stationId, employee)) {
      return true;
    }

    if (isEmployeeReplacement(stationId, employee, date, rules)) {
      return true;
    }

    return false;
  }

  /**
   * Checks if is employee replacement.
   *
   * @param stationId the station id
   * @param employee the employee
 * @param date 
   * @param rules the rules
   * @return true, if is employee replacement
   */
  private boolean isEmployeeReplacement(long stationId, EmployeeStatusModel employee, Date date, List<RuleBaseEntity> rules) {
    List<RuleVacationEntity> activeVacations = ruleService.filterActiveVacations(date, rules);
    if (employee == null || activeVacations == null || activeVacations.size() == 0) {
      return false;
    }

    for (RuleVacationEntity vacation : activeVacations) {
      if (vacation.getReplacers() == null) {
        continue;
      }

      Iterator<EmployeeEntity> replacersIterator = vacation.getReplacers().iterator();
      while (replacersIterator.hasNext()) {
        EmployeeEntity replacer = replacersIterator.next();
        if (replacer.getId() != employee.getId()) {
          continue;
        }

        EmployeeEntity employeeOnVacation = vacation.getEmployees().iterator().next();
        if (employeeOnVacation.getStations() == null) {
          continue;
        }

        Iterator<StationEntity> stationsIterator = employeeOnVacation.getStations().iterator();
        while (stationsIterator.hasNext()) {
          if (stationsIterator.next().getId() == stationId) {
            return true;
          }
        }
      }
    }

    return false;
  }

  /**
   * Checks if is employee primary assigned to station.
   *
   * @param stationId the station id
   * @param employee the employee
   * @return true, if is employee primary assigned to station
   */
  private boolean isEmployeePrimaryAssignedToStation(long stationId, EmployeeStatusModel employee) {
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
