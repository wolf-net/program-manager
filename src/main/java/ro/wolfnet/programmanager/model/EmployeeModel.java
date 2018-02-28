package ro.wolfnet.programmanager.model;

/**
 * The Class EmployeeModel.
 *
 * @author isti
 * @since Feb 28, 2018
 */
public class EmployeeModel {

  /** The id. */
  private long id;

  /** The name. */
  private String name;

  /** The note. */
  private String note;

  /** The type. */
  private String type;

  /** The status. */
  private int status;

  /** The stations. */
  private String[] stationIds;

  /** The station names. */
  private String[] stationNames;

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
  public String getType() {
    return type;
  }

  /**
   * Sets the type.
   *
   * @param type the new type
   */
  public void setType(String type) {
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
   * Gets the station ids.
   *
   * @return the station ids
   */
  public String[] getStationIds() {
    return stationIds;
  }

  /**
   * Sets the station ids.
   *
   * @param stationIds the new station ids
   */
  public void setStationIds(String[] stationIds) {
    this.stationIds = stationIds;
  }

  /**
   * Gets the station names.
   *
   * @return the station names
   */
  public String[] getStationNames() {
    return stationNames;
  }

  /**
   * Sets the station names.
   *
   * @param stationNames the new station names
   */
  public void setStationNames(String[] stationNames) {
    this.stationNames = stationNames;
  }

}
