package ro.wolfnet.programmanager.service.generate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
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

  private EmployeeStatusModel getAvailableEmployee(Date date, EmployeeStatusModel employee, List<RuleVacationEntity> vacationRules) {
	List<RuleVacationEntity> myVacations = getVacationsOfEmployee(employee.getId(), vacationRules);
	if (myVacations == null || myVacations.size() == 0) {
		return employee;
	}
	
    for (RuleVacationEntity vacation : myVacations) {
        if (vacation.getStart().after(date) || vacation.getEnd().before(date)) {
          employee.setWorkedHours(employee.getWorkedHours() + getAuxWorkedHours(date, employee, myVacations, vacationRules));
          continue;
        }
        //employee is on vacation
        return null;
    }
    return employee;
  }

  private List<RuleVacationEntity> getVacationsOfEmployee(long employeeId, List<RuleVacationEntity> vacationRules) {
	if (employeeId == 0 || vacationRules == null || vacationRules.size() == 0) {
		return null;
	}
	
	List<RuleVacationEntity> myVacations = new ArrayList<>();
	for (RuleVacationEntity vacation:vacationRules) {
		if (vacation.getEmployees().iterator().next().getId() == employeeId) {
			myVacations.add(vacation);
		}
	}
	return myVacations;
}

/**
   * Gets the aux worked hours.
   * 
   * Rule: ((how many assigned stations I have * worked employees on each one / how many employees will work in this interval) - my vacation hours) /
   * work days number * work day index * -1
   *
   * @return the aux worked hours
   */
  private double getAuxWorkedHours(Date workDate, EmployeeStatusModel employee, List<RuleVacationEntity> vacationsOfEmployee, List<RuleVacationEntity> allVacations) {
	  if (workDate == null || vacationsOfEmployee == null || vacationsOfEmployee.size() == 0) {
		  return 0;
	  }
	  
	  double workedHoursDifferenceBetweenMeAndOthers = 0;
	  Date start = Utils.getDateFromBeginningOfMonth(workDate);
	  Date end = Utils.getDateAtEndOfMonth(workDate);
	  List<Date> vacationDays = new ArrayList<>();
	  List<StationEntity> employeeStations = stationRepository.getStationsOfEmployee(employee.getId());
	  for (RuleVacationEntity vacation:vacationsOfEmployee) {
		  Date calcStart = Utils.getMaximum(start, vacation.getStart());
		  Date calcEnd = Utils.getMinimum(end, vacation.getEnd());
		  if (calcStart.after(calcEnd)) {
			  continue;
		  }
		  double myVacationHours = getVacationDays(calcStart, calcEnd) * 8;
		  double othersWorkedHours = getWorkedHoursOfOneEmployeeWithoutMe(employeeStations, calcStart, calcEnd, allVacations);
		  workedHoursDifferenceBetweenMeAndOthers += (othersWorkedHours - myVacationHours);
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
	  return workedHoursDifferenceBetweenMeAndOthers / totalWorkDaysThisMonth * workDayIndex * (-1);
  }

  private long getVacationDays(Date calcStart, Date calcEnd) {
	if (calcStart == null || calcEnd == null || calcStart.after(calcEnd)) {
		return 0;
	}
	long diff = Utils.getDateDifference(calcStart, calcEnd, TimeUnit.DAYS) + 1;
	if (calcStart.getTime() != calcEnd.getTime()) {
		diff++;
	}
	return diff;
  }

	private double getWorkedHoursOfOneEmployeeWithoutMe(List<StationEntity> employeeStations, Date startInterval, Date endInterval, List<RuleVacationEntity> allVacations) {
		if (employeeStations == null || startInterval == null || endInterval == null || allVacations == null) {
			return 0;
		}
		
		double result = 0;
		double daysNumber = getVacationDays(startInterval, endInterval);
		for (StationEntity station:employeeStations) {
			int availableEmployeesNumber = getAvailableEmployeesNumberForStationWithinInterval(station, startInterval, endInterval, allVacations);
			result += (station.getCapacity() * daysNumber * 24 / availableEmployeesNumber);
		}
		return result;
	}

	private int getAvailableEmployeesNumberForStationWithinInterval(StationEntity station, Date startInterval, Date endInterval, List<RuleVacationEntity> allVacations) {
		if (station == null || startInterval == null || endInterval == null) {
			return 0;
		}
		
		int availableEmployeesNumber = 0;
		Set<EmployeeEntity> employeesWorkingInStation = getEmployeesWorkingInStationForInterval(station, startInterval, endInterval);
		for (EmployeeEntity employee:employeesWorkingInStation) {
			List<RuleVacationEntity> employeeVacations = getVacationsOfEmployee(employee.getId(), allVacations);
			if (!isEmployeeOnVacationBetweenInterval(employeeVacations, startInterval, endInterval)) {
				availableEmployeesNumber ++;
			}
		}
		return availableEmployeesNumber;
	}

	private Set<EmployeeEntity> getEmployeesWorkingInStationForInterval(StationEntity station, Date startInterval, Date endInterval) {
		// TODO Add also replacers
		return station.getEmployees();
	}

	private boolean isEmployeeOnVacationBetweenInterval(List<RuleVacationEntity> employeeVacations, Date startInterval, Date endInterval) {
		if (employeeVacations == null || startInterval == null || endInterval == null) {
			return false;
		}
		for (RuleVacationEntity vacation:employeeVacations) {
			Date calcStart = Utils.getMaximum(startInterval, vacation.getStart());
			Date calcEnd = Utils.getMinimum(endInterval, vacation.getEnd());
			if (calcStart.after(calcEnd)) {
				continue;
			}
			return true;
		}
		return false;
	}

}
