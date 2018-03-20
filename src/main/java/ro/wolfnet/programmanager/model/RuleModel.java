package ro.wolfnet.programmanager.model;

import java.util.Date;

public class RuleModel {
	
	public static int RULE_TYPE_VACATION = 1;

	private int ruleType;
	private Date vacationStartDate;
	private Date vacationEndDate;
	private long vacationForEmployee;
	
	public Date getVacationStartDate() {
		return vacationStartDate;
	}
	public void setVacationStartDate(Date vacationStartDate) {
		this.vacationStartDate = vacationStartDate;
	}
	public Date getVacationEndDate() {
		return vacationEndDate;
	}
	public void setVacationEndDate(Date vacationEndDate) {
		this.vacationEndDate = vacationEndDate;
	}
	public long getVacationForEmployee() {
		return vacationForEmployee;
	}
	public void setVacationForEmployee(long vacationForEmployee) {
		this.vacationForEmployee = vacationForEmployee;
	}
	public int getRuleType() {
		return ruleType;
	}
	public void setRuleType(int ruleType) {
		this.ruleType = ruleType;
	}
}
