package ro.wolfnet.programmanager.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Class ProgramModel.
 *
 * @author isti
 * @since Feb 20, 2018
 */
public class ProgramModel {

  /** The station id. */
  private long stationId;

  /** The station name. */
  private String stationName;

  /** The date. */
  private Date date;

  /** The employees. */
  private List<EmployeeModel> employees;

  /**
   * Instantiates a new program model.
   */
  public ProgramModel() {
  }

  /**
   * Instantiates a new program model.
   *
   * @param stationName the station name
   */
  public ProgramModel(String stationName) {
    this.stationName = stationName;
  }

  /**
   * Gets the station name.
   *
   * @return the station name
   */
  public String getStationName() {
    return stationName;
  }

  /**
   * Sets the station name.
   *
   * @param stationName the new station name
   */
  public void setStationName(String stationName) {
    this.stationName = stationName;
  }

  /**
   * Gets the employees.
   *
   * @return the employees
   */
  public List<EmployeeModel> getEmployees() {
    return employees;
  }

  /**
   * Sets the employees.
   *
   * @param employees the new employees
   */
  public void setEmployees(List<EmployeeModel> employees) {
    this.employees = employees;
  }

  /**
   * Append employees.
   *
   * @param employee the employee
   */
  public void appendEmployee(EmployeeModel employee) {
    if (this.employees == null) {
      this.employees = new ArrayList<>();
    }
    this.employees.add(employee);
  }

  /**
   * Gets the station id.
   *
   * @return the station id
   */
  public long getStationId() {
    return stationId;
  }

  /**
   * Sets the station id.
   *
   * @param stationId the new station id
   */
  public void setStationId(long stationId) {
    this.stationId = stationId;
  }

  /**
   * Gets the date.
   *
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * Sets the date.
   *
   * @param date the new date
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + stationName.hashCode();
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ProgramModel model = (ProgramModel) obj;
    if (stationName != null && !stationName.equals(model.getStationName())) {
      return false;
    }
    return true;
  }

}
