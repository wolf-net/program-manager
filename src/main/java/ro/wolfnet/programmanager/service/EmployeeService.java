package ro.wolfnet.programmanager.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ro.wolfnet.programmanager.entity.EmployeeEntity;
import ro.wolfnet.programmanager.repository.EmployeeRepository;
import ro.wolfnet.programmanager.model.EmployeeModel;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  public List<EmployeeModel> findAll() {
	List<EmployeeEntity> employeeEntities = this.employeeRepository.findAll();
	List<EmployeeModel> employeeModels = getModelsFromEntityes(employeeEntities);
    return employeeModels;
  }
  
  public void save(EmployeeModel model) {
	EmployeeEntity entity = getEntityFromModel(model);
    this.employeeRepository.save(entity);
  }
  
  public void deleteById(long employeeId) {
	  this.employeeRepository.delete(employeeId);
  }
  
  private EmployeeEntity getEntityFromModel(EmployeeModel model) {
	  if (model == null) {
		  return null;
	  }
	  EmployeeEntity entity = new EmployeeEntity();
	  entity.setId(model.getId());
	  entity.setName(model.getName());
	  entity.setNote(model.getNote());
	  entity.setStatus(model.getStatus());
	  entity.setType(getEntityTypeFromModelType(model.getType()));
	  return entity;
  }
  
  private List<EmployeeModel> getModelsFromEntityes(List<EmployeeEntity> entities) {
	  if (entities == null) {
			return null;
	  }
	  List<EmployeeModel> models = new ArrayList<>();
	  for (EmployeeEntity entity:entities) {
		  EmployeeModel model = new EmployeeModel();
		  model.setId(entity.getId());
		  model.setName(entity.getName());
		  model.setNote(entity.getNote());
		  model.setStatus(entity.getStatus());
		  model.setType(getModelTypeFromEntityType(entity.getType()));
		  models.add(model);
	  }
	  return models;
  }
  
  private int getEntityTypeFromModelType(String modelType) {
	  if ("blue".equals(modelType)) {
		  return 1;
	  }
	  if ("green".equals(modelType)) {
		  return 2;
	  }
	  if ("red".equals(modelType)) {
		  return 3;
	  }
	  return 0;
  }
  
  private String getModelTypeFromEntityType(int entityType) {
	if (entityType == 0) {
		return "black";
	}
	if (entityType == 1) {
		return "blue";
	}
	if (entityType == 2) {
		return "green";
	}
	if (entityType == 3) {
		return "red";
	}
	return null;
  }
  
}
