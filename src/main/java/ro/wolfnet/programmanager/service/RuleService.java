package ro.wolfnet.programmanager.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.wolfnet.programmanager.entity.EmployeeEntity;
import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.entity.RuleVacationEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.model.RuleModel;
import ro.wolfnet.programmanager.repository.RuleRepository;
import ro.wolfnet.programmanager.service.generate.AssignedStationsRule;
import ro.wolfnet.programmanager.service.generate.GenerateRule;
import ro.wolfnet.programmanager.service.generate.LessWorkedRule;
import ro.wolfnet.programmanager.service.generate.VacationRule;

/**
 * The Class RuleService.
 *
 * @author isti
 * @since Mar 21, 2018
 */
@Service
public class RuleService {

  /** The rule repository. */
  @Autowired
  private RuleRepository ruleRepository;

  /** The assigned stations rule. */
  @Autowired
  private AssignedStationsRule assignedStationsRule;

  /** The vacation rule. */
  @Autowired
  private VacationRule vacationRule;

  /** The less worked rule. */
  @Autowired
  private LessWorkedRule lessWorkedRule;

  /**
   * Save vacation rule.
   *
   * @param ruleModel the rule model
   * @throws InvalidParameterException the invalid parameter exception
   */
  public void saveVacationRule(RuleModel ruleModel) throws InvalidParameterException {
    if (ruleModel == null || ruleModel.getEmployee() == 0 || ruleModel.getEndDate() == null || ruleModel.getStartDate() == null) {
      throw new InvalidParameterException();
    }

    RuleVacationEntity entity = new RuleVacationEntity();
    entity.setStart(ruleModel.getStartDate());
    entity.setEnd(ruleModel.getEndDate());

    Set<EmployeeEntity> employees = new HashSet<>();
    EmployeeEntity employeeEntity = new EmployeeEntity();
    employeeEntity.setId(ruleModel.getEmployee());
    employees.add(employeeEntity);
    entity.setEmployees(employees);
    
    if (ruleModel.getReplacers() != null) {
    	entity.setReplacers(new HashSet<>());
    	for (long replacer:ruleModel.getReplacers()) {
    		EmployeeEntity replacerEntity = new EmployeeEntity();
    		replacerEntity.setId(replacer);
    		entity.getReplacers().add(replacerEntity);
    		
    	}
    }

    ruleRepository.save(entity);
  }

  /**
   * Find rules.
   *
   * @return the list
   */
  public List<RuleModel> findRuleModels() {
    List<RuleModel> rules = new ArrayList<>();
    for (RuleBaseEntity rule : ruleRepository.findAll()) {
      rules.add(getRuleModelFromEntity(rule));
    }
    return rules;
  }

  /**
   * Gets the rule model from entity.
   *
   * @param rule the rule
   * @return the rule model from entity
   */
  private RuleModel getRuleModelFromEntity(RuleBaseEntity rule) {
    if (rule == null) {
      return null;
    }
    if (rule instanceof RuleVacationEntity) {
      return getRuleModelFromVacationEntity((RuleVacationEntity) rule);
    }
    return null;
  }

  /**
   * Gets the rule model from vacation entity.
   *
   * @param rule the rule
   * @return the rule model from vacation entity
   */
  private RuleModel getRuleModelFromVacationEntity(RuleVacationEntity rule) {
    RuleModel model = new RuleModel();
    EmployeeEntity employee = rule.getEmployees().iterator().next();
    model.setRuleId(rule.getId());
    model.setEmployee(employee.getId());
    model.setEmployeeName(employee.getName());
    model.setEndDate(rule.getEnd());
    model.setStartDate(rule.getStart());
    model.setRuleType(RuleModel.RULE_TYPE_VACATION);
    if (rule.getReplacers() != null) {
    	String[] replacersName = new String[rule.getReplacers().size()];
    	Iterator<EmployeeEntity> replacersIterator = rule.getReplacers().iterator();
    	for (int i = 0; replacersIterator.hasNext(); i++) {
    		replacersName[i] = replacersIterator.next().getName();
    	}
    	model.setReplacersName(replacersName);
    }
    return model;
  }

  /**
   * Delete by id.
   *
   * @param ruleId the rule id
   */
  public void deleteById(long ruleId) {
    RuleVacationEntity entity = new RuleVacationEntity();
    entity.setId(ruleId);
    ruleRepository.delete(entity);
  }

  /**
   * Find rule entities.
   *
   * @return the list
   */
  public List<RuleBaseEntity> findRuleEntities() {
    return ruleRepository.findAll();
  }

  /**
   * Filter vacation from base rules.
   *
   * @param rules the rules
   * @return the list
   */
  public static List<RuleVacationEntity> filterVacationFromBaseRules(List<RuleBaseEntity> rules) {
    if (rules == null || rules.size() == 0) {
      return null;
    }
    List<RuleVacationEntity> vacationRules = new ArrayList<>();
    for (RuleBaseEntity rule : rules) {
      if (rule instanceof RuleVacationEntity) {
        vacationRules.add((RuleVacationEntity) rule);
      }
    }
    return vacationRules;
  }

  /**
   * Filtere employees by rules.
   *
   * @param stationId the station id
   * @param date the date
   * @param allEmployees the all employees
   * @param rules the rules
   * @return the list
   */
  public List<EmployeeStatusModel> filterEmployeesByRules(long stationId, Date date, List<EmployeeStatusModel> allEmployees, List<RuleBaseEntity> rules) {
    if (allEmployees == null || allEmployees.size() == 0) {
      return null;
    }

    List<GenerateRule> generateRules = Arrays.asList(assignedStationsRule, vacationRule, lessWorkedRule);
    for (GenerateRule rule : generateRules) {
      allEmployees = rule.filterEmployees(stationId, date, allEmployees, rules);
    }

    return allEmployees;
  }

  /**
   * Filter active vacations.
 * @param date 
   *
   * @param rules the rules
   * @return the list
   */
  public List<RuleVacationEntity> filterActiveVacations(Date date, List<RuleBaseEntity> rules) {
    if (rules == null) {
      return null;
    }

    List<RuleVacationEntity> activeVacations = new ArrayList<>();
    for (RuleBaseEntity rule : rules) {
      if (!(rule instanceof RuleVacationEntity)) {
        continue;
      }

      RuleVacationEntity vacation = (RuleVacationEntity) rule;
      if (vacation.getStart().after(date) || vacation.getEnd().before(date)) {
        continue;
      }

      activeVacations.add(vacation);
    }
    return activeVacations;
  }

}
