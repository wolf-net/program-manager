package ro.wolfnet.programmanager.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class EmployeeEntity.
 *
 * @author isti
 * @since Feb 15, 2018
 */
@Entity
@Table(name = "employee_entity")
public class EmployeeEntity {

  /**
   * Instantiates a new employee entity.
   */
  public EmployeeEntity() {
  }

  /**
   * Instantiates a new employee entity.
   *
   * @param id the id
   */
  public EmployeeEntity(Long id) {
    this.id = id;
  }

  /** The id. */
  private Long id;

  /** The name. */
  private String name;

  /** The note. */
  private String note;

  /** The type. */
  private int type;

  /** The status. */
  private int status;

  /** The programs. */
  private Set<ProgramEntity> programs;

  /** The stations. */
  private Set<StationEntity> stations;

  /** The vacations. */
  private Set<RuleBaseEntity> rules;

  /** The replaces. */
  private Set<RuleVacationEntity> replaces;

  /**
   * Gets the id.
   *
   * @return the id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(Long id) {
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
   * Gets the note.
   *
   * @return the note
   */
  public String getNote() {
    return note;
  }

  /**
   * Sets the note.
   *
   * @param note the new note
   */
  public void setNote(String note) {
    this.note = note;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public int getType() {
    return type;
  }

  /**
   * Sets the type.
   *
   * @param type the new type
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * Gets the status.
   *
   * @return the status
   */
  public int getStatus() {
    return status;
  }

  /**
   * Sets the status.
   *
   * @param status the new status
   */
  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * Gets the programs.
   *
   * @return the programs
   */
  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
  public Set<ProgramEntity> getPrograms() {
    return programs;
  }

  /**
   * Sets the programs.
   *
   * @param programs the new programs
   */
  public void setPrograms(Set<ProgramEntity> programs) {
    this.programs = programs;
  }

  /**
   * Gets the stations.
   *
   * @return the stations
   */
  @ManyToMany
  @JoinColumn(name = "station_entity_id")
  public Set<StationEntity> getStations() {
    return stations;
  }

  /**
   * Sets the stations.
   *
   * @param stations the new stations
   */
  public void setStations(Set<StationEntity> stations) {
    this.stations = stations;
  }

  /**
   * Gets the vacations.
   *
   * @return the vacations
   */
  @ManyToMany(mappedBy = "employees", cascade = CascadeType.ALL)
  public Set<RuleBaseEntity> getRules() {
    return rules;
  }

  /**
   * Sets the vacations.
   *
   * @param rules the new rules
   */
  public void setRules(Set<RuleBaseEntity> rules) {
    this.rules = rules;
  }

  /**
   * Gets the replaces.
   *
   * @return the replaces
   */
  @ManyToMany(mappedBy = "replacers", cascade = CascadeType.ALL)
  public Set<RuleVacationEntity> getReplaces() {
    return replaces;
  }

  /**
   * Sets the replaces.
   *
   * @param replaces the new replaces
   */
  public void setReplaces(Set<RuleVacationEntity> replaces) {
    this.replaces = replaces;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result;
    if (id != null) {
      result += id.longValue();
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
    EmployeeEntity employee = (EmployeeEntity) obj;
    if (employee != null && employee.getId() != null && !employee.getId().equals(id)) {
      return false;
    }
    return true;
  }

}