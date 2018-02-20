package ro.wolfnet.programmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.wolfnet.programmanager.entity.EmployeeEntity;
import ro.wolfnet.programmanager.model.EmployeeModel;
import ro.wolfnet.programmanager.repository.EmployeeRepository;

/**
 * The Class EmployeeService.
 *
 * @author isti
 * @since Feb 20, 2018
 */
@Service
public class EmployeeService {

  /** The employee repository. */
  @Autowired
  private EmployeeRepository employeeRepository;

  /**
   * Find all.
   *
   * @return the list
   */
  public List<EmployeeModel> findAll() {
    List<EmployeeEntity> employeeEntities = this.employeeRepository.findAll();
    List<EmployeeModel> employeeModels = getModelsFromEntityes(employeeEntities);
    return employeeModels;
  }

  /**
   * Save.
   *
   * @param model the model
   */
  public void save(EmployeeModel model) {
    EmployeeEntity entity = getEntityFromModel(model);
    this.employeeRepository.save(entity);
  }

  /**
   * Delete by id.
   *
   * @param employeeId the employee id
   */
  public void deleteById(long employeeId) {
    this.employeeRepository.delete(employeeId);
  }

  /**
   * Gets the entity from model.
   *
   * @param model the model
   * @return the entity from model
   */
  public EmployeeEntity getEntityFromModel(EmployeeModel model) {
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

  /**
   * Gets the models from entityes.
   *
   * @param entities the entities
   * @return the models from entityes
   */
  private List<EmployeeModel> getModelsFromEntityes(List<EmployeeEntity> entities) {
    if (entities == null) {
      return null;
    }
    List<EmployeeModel> models = new ArrayList<>();
    for (EmployeeEntity entity : entities) {
      models.add(getModelFromEntity(entity));
    }
    return models;
  }

  /**
   * Gets the model from entity.
   *
   * @param entity the entity
   * @return the model from entity
   */
  public EmployeeModel getModelFromEntity(EmployeeEntity entity) {
    if (entity == null) {
      return null;
    }
    EmployeeModel model = new EmployeeModel();
    model.setId(entity.getId());
    model.setName(entity.getName());
    model.setNote(entity.getNote());
    model.setStatus(entity.getStatus());
    model.setType(getModelTypeFromEntityType(entity.getType()));
    return model;
  }

  /**
   * Gets the entity type from model type.
   *
   * @param modelType the model type
   * @return the entity type from model type
   */
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

  /**
   * Gets the model type from entity type.
   *
   * @param entityType the entity type
   * @return the model type from entity type
   */
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
