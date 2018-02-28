package ro.wolfnet.programmanager.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.wolfnet.programmanager.entity.ProgramEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.model.ProgramModel;
import ro.wolfnet.programmanager.model.StationModel;
import ro.wolfnet.programmanager.repository.ProgramRepository;
import ro.wolfnet.programmanager.service.generate.GenerateRule;

/**
 * The Class ProgramService.
 *
 * @author isti
 * @since Feb 20, 2018
 */
@Service
public class ProgramService {

  /** The program repository. */
  @Autowired
  private ProgramRepository programRepository;

  /** The station service. */
  @Autowired
  private StationService stationService;

  /** The employee service. */
  @Autowired
  private EmployeeService employeeService;

  /**
   * Insert dummy.
   */
  public void insertDummy() {
    ProgramEntity program = new ProgramEntity();
    programRepository.save(program);
  }

  /**
   * Find all.
   *
   * @param dayOfProgram the day of program
   * @return the list
   */
  public List<ProgramModel> findAllForOneDay(Date dayOfProgram) {
    List<ProgramEntity> entities = programRepository.findAllByDate(dayOfProgram);
    List<ProgramModel> models = new ArrayList<>();
    for (ProgramEntity entity : entities) {
      int stationIdx = models.indexOf(new ProgramModel(entity.getStation().getName()));
      if (stationIdx > -1) {
        models.get(stationIdx).appendEmployee(employeeService.getModelFromEntity(entity.getEmployee()));
      }
      else {
        models.add(getModelFromEntity(entity));
      }
    }
    return models;
  }

  /**
   * Gets the model from entity.
   *
   * @param entity the entity
   * @return the model from entity
   */
  private ProgramModel getModelFromEntity(ProgramEntity entity) {
    if (entity == null) {
      return null;
    }

    ProgramModel model = new ProgramModel();
    model.setStationName(entity.getStation().getName());
    model.appendEmployee(employeeService.getModelFromEntity(entity.getEmployee()));
    return model;
  }

  /**
   * Generate program for one day.
   *
   * @param dayOfProgram the day of program
   */
  public void generateProgramsForOneDay(Date dayOfProgram) {
    try {
      programRepository.deleteByDate(dayOfProgram);
      
      List<StationModel> allStations = stationService.findAll();
      List<EmployeeStatusModel> allEmployees = employeeService.findEmployeeStatuses();
      List<ProgramEntity> programsForDay = new ArrayList<>();
      for (StationModel station : allStations) {
        programsForDay.addAll(getProgramsForStation(station, dayOfProgram, allEmployees));
      }
      programRepository.save(programsForDay);
    } catch (Exception e) {
      System.out.println("Error generating programs for day: " + dayOfProgram + "! " + e.getMessage());
    }
  }

  /**
   * Gets the programs for station.
   *
   * @param station the station
   * @param date the date
   * @param allEmployees the all employees
   * @return the programs for station
   * @throws Exception the exception
   */
  private List<ProgramEntity> getProgramsForStation(StationModel station, Date date, List<EmployeeStatusModel> allEmployees) throws Exception {
    if (station == null || station.getCapacity() == 0) {
      throw new Exception("Missing station!");
    }

    List<ProgramEntity> newPrograms = new ArrayList<>();
    for (int cnt = 0; cnt < station.getCapacity(); cnt++) {
      EmployeeStatusModel chosenEmployee = getRandomEmployeeModelFromList(allEmployees);
      allEmployees.remove(chosenEmployee);
      
      ProgramEntity program = new ProgramEntity();
      program.setEmployee(employeeService.getEntityFromModel(chosenEmployee));
      program.setStation(stationService.getEntityFromModel(station));
      program.setWorkedHours(24);
      program.setDate(date);
      newPrograms.add(program);
    }
    return newPrograms;
  }

  /**
   * Gets the random employee model from list.
   *
   * @param allEmployees the all employees
   * @return the random employee model from list
   * @throws Exception the exception
   */
  private EmployeeStatusModel getRandomEmployeeModelFromList(List<EmployeeStatusModel> allEmployees) throws Exception {
    if (allEmployees == null || allEmployees.size() == 0) {
      throw new Exception("Missing employees!");
    }
    
    List<GenerateRule> rules = new ArrayList<>();
    for (GenerateRule rule:rules) {
    	allEmployees = rule.filterEmployees(allEmployees);
    }
    
    Collections.sort(allEmployees, new Comparator<EmployeeStatusModel>() {
	    @Override
	    public int compare(EmployeeStatusModel o1, EmployeeStatusModel o2) {
	      return new Integer(o1.getWorkedHours()).compareTo(new Integer(o2.getWorkedHours()));
	    }
	});

    int lastLessWorkingHourIdx = getLatsLessWorkedIndex(allEmployees);
    int idx = (int) (Math.random() * (lastLessWorkingHourIdx - 0)) + 0;
    return allEmployees.get(idx);
  }

  /**
   * Gets the lats less worked index.
   *
   * @param allEmployees the all employees
   * @return the lats less worked index
   * @throws Exception the exception
   */
  private int getLatsLessWorkedIndex(List<EmployeeStatusModel> allEmployees) throws Exception {
    if (allEmployees == null || allEmployees.size() == 0) {
      throw new Exception("Missing employees!");
    }

    int lessWorkingHour = allEmployees.get(0).getWorkedHours();
    int index = 0;
    while (index < allEmployees.size() && allEmployees.get(index).getWorkedHours() == lessWorkingHour) {
      index++;
    }
    return index-1;
  }

  /**
   * Generate programs for one month.
   *
   * @param monthDay the month day
   */
  public void generateProgramsForOneMonth(Date monthDay) {
    List<Date> datesOfMonth = getAllDaysFromMonth(monthDay);
    if (datesOfMonth == null || datesOfMonth.size() == 0) {
      System.out.println("Missing month dates!");
      return;
    }

    for (Date date : datesOfMonth) {
      programRepository.deleteByDate(date);
    }
    for (Date date : datesOfMonth) {
      generateProgramsForOneDay(date);
    }
  }

  /**
   * Gets the all days from month.
   *
   * @param monthDay the month day
   * @return the all days from month
   */
  private List<Date> getAllDaysFromMonth(Date monthDay) {
    if (monthDay == null) {
      return null;
    }

    List<Date> dates = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(monthDay);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    int month = calendar.get(Calendar.MONTH);
    while (month == calendar.get(Calendar.MONTH)) {
      dates.add(calendar.getTime());
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    }
    return dates;
  }

}
