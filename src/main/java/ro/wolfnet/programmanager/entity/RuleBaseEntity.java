package ro.wolfnet.programmanager.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * The Class RuleBaseEntity.
 *
 * @author isti
 * @since Mar 21, 2018
 */
@Entity
@Table(name = "rule_base_entity")
public class RuleBaseEntity {

  /** The id. */
  private Long id;

  /** The employee. */
  private Set<EmployeeEntity> employees;

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
   * Gets the employees.
   *
   * @return the employees
   */
  @ManyToMany
  @JoinColumn(name = "employee_entity_id")
  public Set<EmployeeEntity> getEmployees() {
    return employees;
  }

  /**
   * Sets the employees.
   *
   * @param employees the new employees
   */
  public void setEmployees(Set<EmployeeEntity> employees) {
    this.employees = employees;
  }

}
