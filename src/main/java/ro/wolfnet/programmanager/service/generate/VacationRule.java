package ro.wolfnet.programmanager.service.generate;

import java.util.Date;
import java.util.List;

import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;

public class VacationRule implements GenerateRule {

	@Override
	public List<EmployeeStatusModel> filterEmployees(long stationId, Date date, List<EmployeeStatusModel> employees,
			List<RuleBaseEntity> rules) {
		return employees;
	}

}
