package ro.wolfnet.programmanager.model;

import java.util.Date;

/**
 * The Class RuleModel.
 *
 * @author isti
 * @since Mar 21, 2018
 */
public class RuleModel {

  /** The rule type vacation. */
  public static int RULE_TYPE_VACATION = 1;
  
  /** The rule type work together. */
  public static int RULE_TYPE_WORK_TOGETHER = 2;

  /** The operation. */
  private String operation;

  /** The rule id. */
  private long ruleId;

  /** The rule type. */
  private int ruleType;

  /** The start date. */
  private Date startDate;

  /** The end date. */
  private Date endDate;

  /** The employee. */
  private long employee;

  /** The employee name. */
  private String employeeName;

  /** The replacers. */
  private long[] replacers;

  /** The replacers name. */
  private String[] replacersName;

  /** The employees. */
  private long[] employees;

  /** The employees name. */
  private String[] employeesName;

  /**
   * Gets the start date.
   *
   * @return the start date
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * Sets the start date.
   *
   * @param startDate
   *            the new start date
   */
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  /**
   * Gets the end date.
   *
   * @return the end date
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * Sets the end date.
   *
   * @param endDate
   *            the new end date
   */
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  /**
   * Gets the employee.
   *
   * @return the employee
   */
  public long getEmployee() {
    return employee;
  }

  /**
   * Sets the employee.
   *
   * @param employee
   *            the new employee
   */
  public void setEmployee(long employee) {
    this.employee = employee;
  }

  /**
   * Gets the rule type.
   *
   * @return the rule type
   */
  public int getRuleType() {
    return ruleType;
  }

  /**
   * Sets the rule type.
   *
   * @param ruleType
   *            the new rule type
   */
  public void setRuleType(int ruleType) {
    this.ruleType = ruleType;
  }

  /**
   * Gets the employee name.
   *
   * @return the employee name
   */
  public String getEmployeeName() {
    return employeeName;
  }

  /**
   * Sets the employee name.
   *
   * @param employeeName the new employee name
   */
  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  /**
   * Gets the rule id.
   *
   * @return the rule id
   */
  public long getRuleId() {
    return ruleId;
  }

  /**
   * Sets the rule id.
   *
   * @param ruleId the new rule id
   */
  public void setRuleId(long ruleId) {
    this.ruleId = ruleId;
  }

  /**
   * Gets the replacers.
   *
   * @return the replacers
   */
  public long[] getReplacers() {
    return replacers;
  }

  /**
   * Sets the replacers.
   *
   * @param replacers the new replacers
   */
  public void setReplacers(long[] replacers) {
    this.replacers = replacers;
  }

  /**
   * Gets the replacers name.
   *
   * @return the replacers name
   */
  public String[] getReplacersName() {
    return replacersName;
  }

  /**
   * Sets the replacers name.
   *
   * @param replacersName the new replacers name
   */
  public void setReplacersName(String[] replacersName) {
    this.replacersName = replacersName;
  }

  /**
   * Gets the employees.
   *
   * @return the employees
   */
  public long[] getEmployees() {
    return employees;
  }

  /**
   * Sets the employees.
   *
   * @param employees the new employees
   */
  public void setEmployees(long[] employees) {
    this.employees = employees;
  }

  /**
   * Gets the operation.
   *
   * @return the operation
   */
  public String getOperation() {
    return operation;
  }

  /**
   * Sets the operation.
   *
   * @param operation the new operation
   */
  public void setOperation(String operation) {
    this.operation = operation;
  }

  /**
   * Gets the employees name.
   *
   * @return the employees name
   */
  public String[] getEmployeesName() {
    return employeesName;
  }

  /**
   * Sets the employees name.
   *
   * @param employeesName the new employees name
   */
  public void setEmployeesName(String[] employeesName) {
    this.employeesName = employeesName;
  }
}
