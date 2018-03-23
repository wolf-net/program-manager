package ro.wolfnet.programmanager.service.generate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.entity.RuleVacationEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.service.RuleService;
import ro.wolfnet.programmanager.utils.Utils;

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
      EmployeeStatusModel availableEmployee = getAvailableEmployee(date, employee, vacationRules);
      if (availableEmployee != null) {
        availableEmployees.add(employee);
      }
    }
    return availableEmployees;
  }

  private EmployeeStatusModel getAvailableEmployee(Date date, EmployeeStatusModel employee, List<RuleVacationEntity> vacationRules) {
    for (RuleVacationEntity vacation : vacationRules) {
      if (vacation.getEmployees().iterator().next().getId() == employee.getId()) {
        if (vacation.getStart().after(date) || vacation.getEnd().before(date)) {
          double vacationPercentWorkedHours = Utils.getDateDifference(vacation.getStart(), vacation.getEnd(), TimeUnit.HOURS) / Utils.getDayOfMonthNumbers(date);
          vacationPercentWorkedHours *= Utils.getDateDifference(Utils.getDateFromBeginningOfMonth(date), date, TimeUnit.DAYS);
          employee.setWorkedHours(employee.getWorkedHours() + vacationPercentWorkedHours);
          continue;
        }
        
        //employee is on vacation
        return null;
      }
    }
    return employee;
  }

}
