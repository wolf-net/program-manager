package ro.wolfnet.programmanager.model;

/**
 * The Class EmployeeHoursModel.
 *
 * @author isti
 * @since Feb 22, 2018
 */
public class EmployeeStatusModel extends EmployeeModel {

  /** The worked hours. */
  private int workedHours;

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

}
