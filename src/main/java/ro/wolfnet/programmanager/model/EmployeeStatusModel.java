package ro.wolfnet.programmanager.model;

import java.util.Date;

/**
 * The Class EmployeeHoursModel.
 *
 * @author isti
 * @since Feb 22, 2018
 */
public class EmployeeStatusModel extends EmployeeModel {

  /** The worked hours. */
  private double workedHours;

  /** The older worked hours. */
  private double olderWorkedHours;

  /** The last worked. */
  private Date lastWorked;

  /** The worked hours equalized. */
  private boolean workedHoursEqualized;

  /** The vacation hours equalized. */
  private boolean vacationHoursEqualized;

  /**
   * Instantiates a new employee status model.
   */
  public EmployeeStatusModel() {
  }

  /**
   * Instantiates a new employee status model.
   *
   * @param employeeStatusModel the employee status model
   */
  public EmployeeStatusModel(EmployeeStatusModel employeeStatusModel) {
    this((EmployeeModel) employeeStatusModel);
    this.setLastWorked(employeeStatusModel.getLastWorked());
    this.setWorkedHours(employeeStatusModel.getWorkedHours());
    this.setOlderWorkedHours(employeeStatusModel.getOlderWorkedHours());
  }

  /**
   * Instantiates a new employee status model.
   *
   * @param employeeModel the employee model
   */
  public EmployeeStatusModel(EmployeeModel employeeModel) {
    this.setId(employeeModel.getId());
    this.setStatus(employeeModel.getStatus());
    this.setName(employeeModel.getName());
    this.setNote(employeeModel.getNote());
    this.setType(employeeModel.getType());
    this.setStationIds(employeeModel.getStationIds());
    this.setStationNames(employeeModel.getStationNames());
  }

  /**
   * Gets the worked hours.
   *
   * @return the worked hours
   */
  public double getWorkedHours() {
    return workedHours;
  }

  /**
   * Sets the worked hours.
   *
   * @param workedHours the new worked hours
   */
  public void setWorkedHours(double workedHours) {
    this.workedHours = workedHours;
  }

  /**
   * Gets the last worked.
   *
   * @return the last worked
   */
  public Date getLastWorked() {
    return lastWorked;
  }

  /**
   * Sets the last worked.
   *
   * @param lastWorked the new last worked
   */
  public void setLastWorked(Date lastWorked) {
    this.lastWorked = lastWorked;
  }

  /**
   * Gets the older worked hours.
   *
   * @return the older worked hours
   */
  public double getOlderWorkedHours() {
    return olderWorkedHours;
  }

  /**
   * Sets the older worked hours.
   *
   * @param olderWorkedHours the new older worked hours
   */
  public void setOlderWorkedHours(double olderWorkedHours) {
    this.olderWorkedHours = olderWorkedHours;
  }

  /**
   * Checks if is worked hours equalized.
   *
   * @return true, if is worked hours equalized
   */
  public boolean isWorkedHoursEqualized() {
    return workedHoursEqualized;
  }

  /**
   * Sets the worked hours equalized.
   *
   * @param workedHoursEqualized the new worked hours equalized
   */
  public void setWorkedHoursEqualized(boolean workedHoursEqualized) {
    this.workedHoursEqualized = workedHoursEqualized;
  }

  /**
   * Checks if is vacation hours equalized.
   *
   * @return true, if is vacation hours equalized
   */
  public boolean isVacationHoursEqualized() {
    return vacationHoursEqualized;
  }

  /**
   * Sets the vacation hours equalized.
   *
   * @param vacationHoursEqualized the new vacation hours equalized
   */
  public void setVacationHoursEqualized(boolean vacationHoursEqualized) {
    this.vacationHoursEqualized = vacationHoursEqualized;
  }

}
