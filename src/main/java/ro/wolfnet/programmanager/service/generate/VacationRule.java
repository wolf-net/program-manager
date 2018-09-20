package ro.wolfnet.programmanager.service.generate;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.wolfnet.programmanager.entity.EmployeeEntity;
import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.entity.RuleVacationEntity;
import ro.wolfnet.programmanager.entity.StationEntity;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.repository.StationRepository;
import ro.wolfnet.programmanager.service.RuleService;
import ro.wolfnet.programmanager.utils.Utils;

/**
 * The Class VacationRule.
 *
 * @author isti
 * @since Mar 22, 2018
 */
@Component
public class VacationRule implements GenerateRule {

  /** The station repository. */
  @Autowired
  private StationRepository stationRepository;

  /* (non-Javadoc)
   * @see ro.wolfnet.programmanager.service.generate.GenerateRule#filterEmployees(long, java.util.Date, java.util.List, java.util.List)
   */
  @Override
  public List<EmployeeStatusModel> filterEmployees(long stationId, Date date, List<EmployeeStatusModel> employees, List<RuleBaseEntity> rules) {
    List<RuleVacationEntity> vacationRules = RuleService.filterVacationFromBaseRules(rules);
    if (vacationRules == null || vacationRules.size() == 0) {
      return employees;
    }

    List<EmployeeStatusModel> availableEmployees = new ArrayList<>();
    for (EmployeeStatusModel employee : employees) {
      EmployeeStatusModel availableEmployee = getAvailableEmployee(date, employee, vacationRules);
      if (availableEmployee != null) {
        availableEmployees.add(employee);
      }
    }
    return availableEmployees;
  }

  /**
   * Gets the available employee.
   *
   * @param date the date
   * @param employee the employee
   * @param vacationRules the vacation rules
   * @return the available employee
   */
  private EmployeeStatusModel getAvailableEmployee(Date date, EmployeeStatusModel employee, List<RuleVacationEntity> vacationRules) {
    List<RuleVacationEntity> myVacations = getVacationsOfEmployee(employee.getId(), vacationRules);
    if (myVacations == null || myVacations.size() == 0) {
      return employee;
    }

    for (RuleVacationEntity vacation : myVacations) {
      if (vacation.getStart().after(date) || vacation.getEnd().before(date)) {
        if (employee.isVacationHoursEqualized()) {
          employee.setWorkedHours(employee.getWorkedHours() + getAuxWorkedHours(date, employee, vacation, myVacations, vacationRules));
          employee.setVacationHoursEqualized(true);
        }
        continue;
      }
      //employee is on vacation
      return null;
    }
    return employee;
  }

  /**
   * Gets the aux worked hours.
   *
   * @param date the date
   * @param employee the employee
   * @param myCurrentVacation the my current vacation
   * @param myVacations the my vacations
   * @param vacationRules the vacation rules
   * @return the aux worked hours
   */
  private double getAuxWorkedHours(Date date, EmployeeStatusModel employee, RuleVacationEntity myCurrentVacation, List<RuleVacationEntity> myVacations,
                                   List<RuleVacationEntity> vacationRules) {
    double auxWorkedHours = getAuxWorkedHoursFromDifferences(date, employee, myVacations, vacationRules);
    if (auxWorkedHours == 0) {
      auxWorkedHours = (Utils.getDateDifference(myCurrentVacation.getStart(), myCurrentVacation.getEnd(), TimeUnit.DAYS) * 8) /
        Utils.getDayOfMonthNumbers(date);
      auxWorkedHours *= Utils.getDateDifference(Utils.getDateFromBeginningOfMonth(date), date, TimeUnit.DAYS);
    }
    return auxWorkedHours;
  }

  /**
   * Gets the vacations of employee.
   *
   * @param employeeId the employee id
   * @param vacationRules the vacation rules
   * @return the vacations of employee
   */
  private List<RuleVacationEntity> getVacationsOfEmployee(long employeeId, List<RuleVacationEntity> vacationRules) {
    if (employeeId == 0 || vacationRules == null || vacationRules.size() == 0) {
      return null;
    }

    List<RuleVacationEntity> myVacations = new ArrayList<>();
    for (RuleVacationEntity vacation : vacationRules) {
      if (vacation == null || vacation.getEmployees() == null || vacation.getEmployees().isEmpty()) {
        continue;
      }
      if (vacation.getEmployees().iterator().next().getId() == employeeId) {
        myVacations.add(vacation);
      }
    }
    return myVacations;
  }

  /**
   * Gets the aux worked hours.
   * 
   * Rule: (((how many assigned stations I have * worked employees on each one / how many employees will work in this interval) - my vacation hours) /
   * work days number * work day index * -1) + 
   * worked hours for past vacations
   *
   * @param workDate the work date
   * @param employee the employee
   * @param vacationsOfEmployee the vacations of employee
   * @param allVacations the all vacations
   * @return the aux worked hours
   */
  private double getAuxWorkedHoursFromDifferences(Date workDate, EmployeeStatusModel employee, List<RuleVacationEntity> vacationsOfEmployee,
                                                  List<RuleVacationEntity> allVacations) {
    if (workDate == null || vacationsOfEmployee == null || vacationsOfEmployee.size() == 0) {
      return 0;
    }

    double workedHoursDifferenceBetweenMeAndOthers = 0;
    double myVacationHoursInPast = 0;
    Date start = Utils.getDateFromBeginningOfMonth(workDate);
    Date end = Utils.getDateAtEndOfMonth(workDate);
    List<Date> vacationDays = new ArrayList<>();
    List<StationEntity> employeeStations = stationRepository.getStationsOfEmployee(employee.getId());
    for (RuleVacationEntity vacation : vacationsOfEmployee) {
      Date calcStart = Utils.getMaximum(start, vacation.getStart());
      Date calcEnd = Utils.getMinimum(end, vacation.getEnd());
      if (calcStart.after(calcEnd)) {
        continue;
      }
      double myVacationHours = getVacationDays(calcStart, calcEnd) * 8;
      double othersWorkedHours = getWorkedHoursOfOneEmployeeWithoutMe(employeeStations, calcStart, calcEnd, allVacations);
      workedHoursDifferenceBetweenMeAndOthers += (othersWorkedHours - myVacationHours);
      if (calcEnd.before(workDate)) {
        myVacationHoursInPast += myVacationHours;
      }
      vacationDays.addAll(Utils.getDatesBetweenTwoDates(calcStart, calcEnd));
    }

    int workDayIndex = 1;
    int totalWorkDaysThisMonth = 0;
    Calendar cal = Calendar.getInstance();
    cal.setTime(start);
    while (!cal.getTime().after(end)) {
      cal.add(Calendar.DAY_OF_MONTH, 1);
      if (!Utils.areDatesContainDate(vacationDays, cal.getTime())) {
        totalWorkDaysThisMonth++;
      }
      if (!cal.getTime().after(workDate)) {
        workDayIndex++;
      }
    }
    return (workedHoursDifferenceBetweenMeAndOthers / totalWorkDaysThisMonth * workDayIndex * (-1)) + myVacationHoursInPast;
  }

  /**
   * Gets the vacation days.
   *
   * @param calcStart the calc start
   * @param calcEnd the calc end
   * @return the vacation days
   */
  private long getVacationDays(Date calcStart, Date calcEnd) {
    if (calcStart == null || calcEnd == null || calcStart.after(calcEnd)) {
      return 0;
    }
    long diff = Utils.getDateDifference(calcStart, calcEnd, TimeUnit.DAYS);
    if (calcStart.getTime() != calcEnd.getTime()) {
      diff++;
    }
    return diff;
  }

  /**
   * Gets the worked hours of one employee without me.
   *
   * @param employeeStations the employee stations
   * @param startInterval the start interval
   * @param endInterval the end interval
   * @param allVacations the all vacations
   * @return the worked hours of one employee without me
   */
  private double getWorkedHoursOfOneEmployeeWithoutMe(List<StationEntity> employeeStations, Date startInterval, Date endInterval,
                                                      List<RuleVacationEntity> allVacations) {
    if (employeeStations == null || startInterval == null || endInterval == null || allVacations == null) {
      return 0;
    }

    double result = 0;
    for (StationEntity station : employeeStations) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(startInterval);
      while (!cal.getTime().after(endInterval)) {
        int availableEmployeesNumber = getAvailableEmployeesNumberForStationForOneDay(station, cal.getTime(), allVacations);
        result += (station.getCapacity() * 24 / availableEmployeesNumber);
        cal.add(Calendar.DAY_OF_MONTH, 1);
      }
    }
    return result;
  }

  /**
   * Gets the available employees number for station within interval.
   *
   * @param station the station
   * @param date the date
   * @param allVacations the all vacations
   * @return the available employees number for station within interval
   */
  private int getAvailableEmployeesNumberForStationForOneDay(StationEntity station, Date date, List<RuleVacationEntity> allVacations) {
    if (station == null || date == null) {
      return 0;
    }

    int availableEmployeesNumber = 0;
    Set<EmployeeEntity> employeesWorkingInStation = getEmployeesWorkingInStationForInterval(station, date, allVacations);
    for (EmployeeEntity employee : employeesWorkingInStation) {
      List<RuleVacationEntity> employeeVacations = getVacationsOfEmployee(employee.getId(), allVacations);
      if (!isEmployeeOnVacationOnSpecificDate(employeeVacations, date)) {
        availableEmployeesNumber++;
      }
    }
    return availableEmployeesNumber;
  }

  /**
   * Gets the employees working in station for interval.
   *
   * @param station the station
   * @param date the date
   * @param allVacations the all vacations
   * @return the employees working in station for interval
   */
  private Set<EmployeeEntity> getEmployeesWorkingInStationForInterval(StationEntity station, Date date, List<RuleVacationEntity> allVacations) {
    List<RuleVacationEntity> vacations = getActiveVacations(date, allVacations);
    if (vacations == null || vacations.size() == 0) {
      return station.getEmployees();
    }

    List<EmployeeEntity> replacers = new ArrayList<>();
    for (RuleVacationEntity vacation : vacations) {
      if (vacation.getReplacers() == null || vacation.getReplacers().size() == 0 ||
        !doesEmployeeWorkInStation(vacation.getEmployees().iterator().next(), station)) {
        continue;
      }
      replacers.addAll(vacation.getReplacers());
    }

    replacers.addAll(station.getEmployees());
    Set<EmployeeEntity> unique = replacers.stream()
        .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(EmployeeEntity::getId))), HashSet::new));
    return unique;
  }

  /**
   * Gets the active vacations.
   *
   * @param date the date
   * @param allVacations the all vacations
   * @return the active vacations
   */
  private List<RuleVacationEntity> getActiveVacations(Date date, List<RuleVacationEntity> allVacations) {
    if (date == null || allVacations == null || allVacations.size() == 0) {
      return null;
    }

    List<RuleVacationEntity> activeVacations = new ArrayList<>();
    for (RuleVacationEntity vacation : allVacations) {
      if (vacation.getStart().after(date) || vacation.getEnd().before(date)) {
        continue;
      }
      activeVacations.add(vacation);
    }
    return activeVacations;
  }

  /**
   * Does employee work in station.
   *
   * @param employee the employee
   * @param station the station
   * @return true, if successful
   */
  private boolean doesEmployeeWorkInStation(EmployeeEntity employee, StationEntity station) {
    if (employee == null || station == null || employee.getStations() == null) {
      return false;
    }
    for (StationEntity employeeStation : employee.getStations()) {
      if (station.getId() == employeeStation.getId()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if is employee on vacation between interval.
   *
   * @param employeeVacations the employee vacations
   * @param date the date
   * @return true, if is employee on vacation between interval
   */
  private boolean isEmployeeOnVacationOnSpecificDate(List<RuleVacationEntity> employeeVacations, Date date) {
    if (employeeVacations == null || date == null) {
      return false;
    }
    for (RuleVacationEntity vacation : employeeVacations) {
      Date calcStart = Utils.getMaximum(date, vacation.getStart());
      Date calcEnd = Utils.getMinimum(date, vacation.getEnd());
      if (calcStart.after(calcEnd)) {
        continue;
      }
      return true;
    }
    return false;
  }

}
