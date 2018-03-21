package ro.wolfnet.programmanager.service.generate;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.entity.RuleVacationEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.service.RuleService;

public class VacationRule implements GenerateRule {

	@Autowired
	private RuleService ruleService;
	
	@Override
	public List<EmployeeStatusModel> filterEmployees(long stationId, Date date, List<EmployeeStatusModel> employees,
			List<RuleBaseEntity> rules) {
		List<RuleVacationEntity> vacationRules = ruleService.filterVacationFromBaseRules(rules);
		return employees;
	}

}
