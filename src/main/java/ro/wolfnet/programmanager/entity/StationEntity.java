package ro.wolfnet.programmanager.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class StationEntity.
 *
 * @author isti
 * @since Feb 15, 2018
 */
@Entity
@Table(name = "station_entity")
public class StationEntity {
  
  /** The id. */
  private long id;
  
  /** The name. */
  private String name;
  
  /** The capacity. */
  private int capacity;
  
  /** The programs. */
  private Set<ProgramEntity> programs;

  /**
   * Gets the id.
   *
   * @return the id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(long id) {
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
   * Gets the capacity.
   *
   * @return the capacity
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   * Sets the capacity.
   *
   * @param capacity the new capacity
   */
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  /**
   * Gets the programs.
   *
   * @return the programs
   */
  @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
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
}