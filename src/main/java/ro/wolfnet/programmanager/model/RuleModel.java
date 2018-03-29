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

	private long ruleId;
	
	/** The rule type. */
	private int ruleType;

	/** The start date. */
	private Date startDate;

	/** The end date. */
	private Date endDate;

	/** The employee. */
	private long employee;

	private String employeeName;

    private long[] replacers;

	private String[] replacersName;

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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public long getRuleId() {
		return ruleId;
	}

	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	public long[] getReplacers() {
		return replacers;
	}

	public void setReplacers(long[] replacers) {
		this.replacers = replacers;
	}

	public String[] getReplacersName() {
		return replacersName;
	}

	public void setReplacersName(String[] replacersName) {
		this.replacersName = replacersName;
	}
}
