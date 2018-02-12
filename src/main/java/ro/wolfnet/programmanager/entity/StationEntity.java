package ro.wolfnet.programmanager.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The Class StationEntity.
 *
 * @author isti
 * @since Feb 12, 2018
 */
@Entity
public class StationEntity implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -4637018459358319222L;

  /** The id. */
  @Id
  @GeneratedValue
  private Long id;
  
  /** The name. */
  private String name;
  
  /** The capacity. */
  private int capacity;

  /**
   * Gets the id.
   *
   * @return the id
   */
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

}