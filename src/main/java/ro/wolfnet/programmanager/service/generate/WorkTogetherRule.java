package ro.wolfnet.programmanager.service.generate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.wolfnet.programmanager.entity.EmployeeEntity;
import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.entity.RuleWorkTogetherEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.service.EmployeeService;

/**
 * The Class WorkTogetherRule.
 *
 * @author isti
 * @since Jul 5, 2018
 */
@Component
public class WorkTogetherRule implements GenerateRule {

  /** The employee service. */
  @Autowired
  private EmployeeService employeeService;

  /* (non-Javadoc)
   * @see ro.wolfnet.programmanager.service.generate.GenerateRule#filterEmployees(long, java.util.Date, java.util.List, java.util.List, int)
   */
  @Override
  public List<EmployeeStatusModel> filterEmployees(long stationId, Date date, List<EmployeeStatusModel> employees, List<RuleBaseEntity> rules) {
    List<RuleWorkTogetherEntity> workTogetherRules = getWorkTogetherRulesSortedFiltered(rules, employees);
    if (workTogetherRules == null || workTogetherRules.size() == 0) {
      return employees;
    }

    RuleWorkTogetherEntity workTogetherToApply = workTogetherRules.get(0);
    List<EmployeeStatusModel> selectedEmployees = new ArrayList<>();
    for (EmployeeStatusModel employee : employees) {
      if (workTogetherToApply.getEmployees().contains(new EmployeeEntity(employee.getId()))) {
        selectedEmployees.add(employee);
      }
    }
    return selectedEmployees;
  }

  /**
   * Gets the available employees.
   *
   * @param models the models
   * @return the available employees
   */
  private List<EmployeeEntity> getAvailableEmployees(List<EmployeeStatusModel> models) {
    if (models == null || models.size() == 0) {
      return null;
    }

    List<EmployeeEntity> entities = new ArrayList<>();
    for (EmployeeStatusModel model : models) {
      entities.add(employeeService.getEntityFromModel(model));
    }
    return entities;
  }

  /**
   * Gets the work together rules.
   *
   * @param rules the rules
   * @param availableEmployees the available employees
   * @return the work together rules
   */
  private List<RuleWorkTogetherEntity> getWorkTogetherRulesSortedFiltered(List<RuleBaseEntity> rules, List<EmployeeStatusModel> availableEmployeeModels) {
    List<EmployeeEntity> availableEmployees = getAvailableEmployees(availableEmployeeModels);
    if (availableEmployees == null || availableEmployees.size() == 0 || rules == null || rules.size() == 0) {
      return null;
    }
    
    List<RuleWorkTogetherEntity> workTogetherRules = new ArrayList<>();
    for (RuleBaseEntity rule : rules) {
      if (rule instanceof RuleWorkTogetherEntity) {
        workTogetherRules.add((RuleWorkTogetherEntity) rule);
      }
    }

    Collections.sort(workTogetherRules, new Comparator<RuleWorkTogetherEntity>() {
      @Override
      public int compare(RuleWorkTogetherEntity o1, RuleWorkTogetherEntity o2) {
        if (o1 == null || o2 == null || o1.getEmployees() == null || o2.getEmployees() == null) {
          return 0;
        }
        return new Integer(o1.getEmployees().size()).compareTo(new Integer(o2.getEmployees().size()));
      }
    });

    List<RuleWorkTogetherEntity> filteredWorkTogetherRules = new ArrayList<>();
    for (RuleWorkTogetherEntity workTogetherRule : workTogetherRules) {
      RuleWorkTogetherEntity filteredRule = adaptWorkingTogetherRuleByAvailableEmployees(availableEmployees, workTogetherRule);
      if (filteredRule != null) {
        filteredWorkTogetherRules.add(filteredRule);
      }
    }
    return filteredWorkTogetherRules;
  }

  /**
   * Adapt working together rule by remained employees.
   *
   * @param availableEmployees the available employees
   * @param rule the rule
   * @return the rule work together entity
   */
  private RuleWorkTogetherEntity adaptWorkingTogetherRuleByAvailableEmployees(List<EmployeeEntity> availableEmployees, RuleWorkTogetherEntity rule) {
    if (rule == null || rule.getEmployees() == null || availableEmployees == null || availableEmployees.size() == 0) {
      return null;
    }

    RuleWorkTogetherEntity resultedRule = new RuleWorkTogetherEntity();
    resultedRule.setEmployees(new HashSet<>());
    
    for (EmployeeEntity employee : rule.getEmployees()) {
      if (availableEmployees.contains(employee)) {
        resultedRule.getEmployees().add(employee);
      }
    }

    if (resultedRule.getEmployees().size() == 0) {
      return null;
    }
    return resultedRule;
  }

}
