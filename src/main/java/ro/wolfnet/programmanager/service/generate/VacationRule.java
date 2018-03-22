package ro.wolfnet.programmanager.service.generate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.entity.RuleVacationEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.service.RuleService;

/**
 * The Class VacationRule.
 *
 * @author isti
 * @since Mar 22, 2018
 */
@Component
public class VacationRule implements GenerateRule {

  /* (non-Javadoc)
   * @see ro.wolfnet.programmanager.service.generate.GenerateRule#filterEmployees(long, java.util.Date, java.util.List, java.util.List)
   */
  @Override
  public List<EmployeeStatusModel> filterEmployees(long stationId, Date date, List<EmployeeStatusModel> employees, List<RuleBaseEntity> rules) {
    List<RuleVacationEntity> vacationRules = RuleService.filterVacationFromBaseRules(rules);
    if (vacationRules == null || vacationRules.size() == 0) {
      return employees;
    }
    
    List<EmployeeStatusModel> availableEmployees = new ArrayList<>();
    for (EmployeeStatusModel employee : employees) {
      if (!isOnVacation(date, employee, vacationRules)) {
        availableEmployees.add(employee);
      }
    }
    return availableEmployees;
  }

  /**
   * Checks if is on vacation.
   *
   * @param date the date
   * @param employee the employee
   * @param vacationRules the vacation rules
   * @return true, if is on vacation
   */
  private boolean isOnVacation(Date date, EmployeeStatusModel employee, List<RuleVacationEntity> vacationRules) {
    for (RuleVacationEntity vacation : vacationRules) {
      if (vacation.getEmployees().iterator().next().getId() == employee.getId()) {
        if (vacation.getStart().after(date) || vacation.getEnd().before(date)) {
          continue;
        }
        return true;
      }
    }
    return false;
  }

}
