package ro.wolfnet.programmanager.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

/**
 * The Class RuleVacationEntity.
 *
 * @author isti
 * @since Mar 21, 2018
 */
@Entity
public class RuleVacationEntity extends RuleBaseEntity {

  /** The start. */
  private Date start;

  /** The end. */
  private Date end;

  /** The replacer. */
  private Set<EmployeeEntity> replacers;

  /**
   * Gets the replacers.
   *
   * @return the replacers
   */
  @ManyToMany
  @JoinColumn(name = "employee_entity_id")
  public Set<EmployeeEntity> getReplacers() {
    return replacers;
  }

  /**
   * Sets the replacers.
   *
   * @param replacers the new replacers
   */
  public void setReplacers(Set<EmployeeEntity> replacers) {
    this.replacers = replacers;
  }

  /**
   * Gets the start.
   *
   * @return the start
   */
  public Date getStart() {
    return start;
  }

  /**
   * Sets the start.
   *
   * @param start the new start
   */
  public void setStart(Date start) {
    this.start = start;
  }

  /**
   * Gets the end.
   *
   * @return the end
   */
  public Date getEnd() {
    return end;
  }

  /**
   * Sets the end.
   *
   * @param end the new end
   */
  public void setEnd(Date end) {
    this.end = end;
  }

}
