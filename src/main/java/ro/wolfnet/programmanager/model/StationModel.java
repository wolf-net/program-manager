package ro.wolfnet.programmanager.model;

/**
 * The Class StationModel.
 *
 * @author isti
 * @since Feb 12, 2018
 */
public class StationModel {

  /** The id. */
  private long id;
  
  /** The name. */
  private String name;
  
  /** The capacity. */
  private int capacity;

  /**
   * Gets the id.
   *
   * @return the id
   */
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

}
