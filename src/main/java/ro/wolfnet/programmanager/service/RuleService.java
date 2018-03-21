package ro.wolfnet.programmanager.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.wolfnet.programmanager.entity.EmployeeEntity;
import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.entity.RuleVacationEntity;
import ro.wolfnet.programmanager.model.RuleModel;
import ro.wolfnet.programmanager.repository.RuleRepository;

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
    return model;
  }

  public void deleteById(long ruleId) {
	RuleVacationEntity entity = new RuleVacationEntity();
	entity.setId(ruleId);
	ruleRepository.delete(entity);
  }

  public List<RuleBaseEntity> findRuleEntities() {
	return ruleRepository.findAll();
  }

}
