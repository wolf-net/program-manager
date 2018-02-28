package ro.wolfnet.programmanager.service.generate;

import java.util.List;

import ro.wolfnet.programmanager.model.EmployeeStatusModel;

public interface GenerateRule {

	public List<EmployeeStatusModel> filterEmployees(List<EmployeeStatusModel> employees);
}
