package ro.wolfnet.programmanager.entity;

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
  private String name;

  /** The station. */
  private StationEntity station;

  /** The employee. */
  private EmployeeEntity employee;

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
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
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
}