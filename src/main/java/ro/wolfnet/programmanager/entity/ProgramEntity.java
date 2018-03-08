package ro.wolfnet.programmanager.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The Class ProgramEntity.
 *
 * @author isti
 * @since Feb 15, 2018
 */
@Entity
public class ProgramEntity {

  /** The id. */
  private int id;

  /** The name. */
  private Date date;

  /** The station. */
  private StationEntity station;

  /** The employee. */
  private EmployeeEntity employee;

  /** The worked hours. */
  private int workedHours;

  /**
   * Instantiates a new program entity.
   */
  public ProgramEntity() {
  }

  /**
   * Instantiates a new program entity.
   *
   * @param employee the employee
   */
  public ProgramEntity(EmployeeEntity employee) {
    this.employee = employee;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(int id) {
    this.id = id;
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

  /**
   * Gets the station.
   *
   * @return the station
   */
  @ManyToOne
  @JoinColumn(name = "station_entity_id")
  public StationEntity getStation() {
    return station;
  }

  /**
   * Sets the station.
   *
   * @param station the new station
   */
  public void setStation(StationEntity station) {
    this.station = station;
  }

  /**
   * Gets the employee.
   *
   * @return the employee
   */
  @ManyToOne
  @JoinColumn(name = "employee_entity_id")
  public EmployeeEntity getEmployee() {
    return employee;
  }

  /**
   * Sets the employee.
   *
   * @param employee the new employee
   */
  public void setEmployee(EmployeeEntity employee) {
    this.employee = employee;
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

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result;
    if (employee != null) {
      result += employee.getId();
    }
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
    ProgramEntity entity = (ProgramEntity) obj;
    if (employee != null && entity.getEmployee() != null && employee.getId() != entity.getEmployee().getId()) {
      return false;
    }
    return true;
  }
}