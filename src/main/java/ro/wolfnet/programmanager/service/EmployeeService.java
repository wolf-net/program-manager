package ro.wolfnet.programmanager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.wolfnet.programmanager.entity.EmployeeEntity;
import ro.wolfnet.programmanager.entity.ProgramEntity;
import ro.wolfnet.programmanager.entity.RuleVacationEntity;
import ro.wolfnet.programmanager.model.EmployeeModel;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.repository.EmployeeRepository;
import ro.wolfnet.programmanager.repository.ProgramRepository;
import ro.wolfnet.programmanager.utils.Utils;

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

  /** The program repository. */
  @Autowired
  private ProgramRepository programRepository;

  /** The station service. */
  @Autowired
  private StationService stationService;
  
  @Autowired
  private RuleService ruleService;

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
    entity.setStations(stationService.getEntitiesFromIds(model.getStationIds()));
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
    model.setStationNames(stationService.getNamesFromEntities(entity.getStations()));
    model.setStationIds(stationService.getIdsFromEntities(entity.getStations()));
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

  /**
   * Find employee statuses.
   *
   * @param monthDay the month day
   * @return the list
   */
  public List<EmployeeStatusModel> findEmployeeStatuses(Date monthDay) {
    List<EmployeeModel> employees = this.findAll();
    List<ProgramEntity> programs = programRepository.findAll();
    List<RuleVacationEntity> vacations = RuleService.filterVacationFromBaseRules(ruleService.findRuleEntities());
    List<EmployeeStatusModel> employeeStatuses = new ArrayList<>();
    for (EmployeeModel employee : employees) {
      EmployeeStatusModel employeeStatus = new EmployeeStatusModel(employee);
      int[] workedHours = getWorkedHoursOfEmployeeFromPrograms(employee, programs, vacations, monthDay);
      employeeStatus.setWorkedHours(workedHours[0]);
      employeeStatus.setOlderWorkedHours(workedHours[1]);
      employeeStatus.setLastWorked(getLastWorkedFromPrograms(employee, programs));
      employeeStatuses.add(employeeStatus);
    }
    return employeeStatuses;
  }

  /**
   * Gets the last worked from programs.
   *
   * @param employee the employee
   * @param programs the programs
   * @return the last worked from programs
   */
  private Date getLastWorkedFromPrograms(EmployeeModel employee, List<ProgramEntity> programs) {
    Date min = null;
    for (ProgramEntity program : programs) {
      if (program.getEmployee().getId() != employee.getId()) {
        continue;
      }
      if (min == null || min.after(program.getDate())) {
        min = program.getDate();
      }
    }
    return min;
  }

  /**
   * Gets the worked hours of employee from programs.
   *
   * @param employee the employee
   * @param programs the programs
   * @param vacations 
   * @param generateForDay the month day
   * @return the worked hours of employee from programs
   */
  
  //@TODO: include also vacations in worked hours
  //@TODO: include only past/this month programs and vacations
  private int[] getWorkedHoursOfEmployeeFromPrograms(EmployeeModel employee, List<ProgramEntity> programs, List<RuleVacationEntity> vacations, Date generateForDay) {
    int[] workedHours = { 0, 0 };
    Date startOfMonthDay = Utils.getDateFromBeginningOfMonth(generateForDay);
    for (ProgramEntity program : programs) {
      if (program.getEmployee().getId() != employee.getId()) {
        continue;
      }
      
      if (program.getDate().before(startOfMonthDay)) {
        workedHours[1] += program.getWorkedHours();
      }
      
      workedHours[0] += program.getWorkedHours();
    }
    return workedHours;
  }

}
