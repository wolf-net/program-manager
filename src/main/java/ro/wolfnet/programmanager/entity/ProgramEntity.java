package ro.wolfnet.programmanager.entity;

import java.util.Date;

import javax.persistence.*;

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
}