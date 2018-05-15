package ro.wolfnet.programmanager.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The Class UserEntity.
 *
 * @author isti
 * @since Feb 5, 2018
 */
@Entity
public class UserEntity {

  /** The id. */
  @Id
  @GeneratedValue
  private Long id;

  /** The name. */
  private String username;

  /** The email. */
  private String password;

  /** The export program columns. */
  private int[] exportProgramColumns;

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
   * Gets the username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username.
   *
   * @param username the new username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password.
   *
   * @param password the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets the export program columns.
   *
   * @return the export program columns
   */
  public int[] getExportProgramColumns() {
    return exportProgramColumns;
  }

  /**
   * Sets the export program columns.
   *
   * @param exportProgramColumns the new export program columns
   */
  public void setExportProgramColumns(int[] exportProgramColumns) {
    this.exportProgramColumns = exportProgramColumns;
  }

}