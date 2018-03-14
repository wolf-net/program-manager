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
  private int workedHours;

  /** The last worked. */
  private Date lastWorked;

  /**
   * Instantiates a new employee status model.
   */
  public EmployeeStatusModel() {
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
  public int getWorkedHours() {
    return workedHours;
  }

  /**
   * Sets the worked hours.
   *
   * @param workedHours the new worked hours
   */
  public void setWorkedHours(int workedHours) {
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

}
