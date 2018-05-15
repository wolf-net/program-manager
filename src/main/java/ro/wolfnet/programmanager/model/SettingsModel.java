package ro.wolfnet.programmanager.model;

import java.util.Date;

/**
 * The Class SettingsModel.
 *
 * @author isti
 * @since Feb 12, 2018
 */
public class SettingsModel {

  /** The new password. */
  private String newPassword;

  /** The delete older. */
  private Date deleteOlder;

  /** The export columns. */
  private int[] exportColumns;

  /**
   * Gets the new password.
   *
   * @return the new password
   */
  public String getNewPassword() {
    return newPassword;
  }

  /**
   * Sets the new password.
   *
   * @param newPassword the new new password
   */
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  /**
   * Gets the delete older.
   *
   * @return the delete older
   */
  public Date getDeleteOlder() {
    return deleteOlder;
  }

  /**
   * Sets the delete older.
   *
   * @param deleteOlder the new delete older
   */
  public void setDeleteOlder(Date deleteOlder) {
    this.deleteOlder = deleteOlder;
  }

  public int[] getExportColumns() {
    return exportColumns;
  }

  public void setExportColumns(int[] exportColumns) {
    this.exportColumns = exportColumns;
  }

}
