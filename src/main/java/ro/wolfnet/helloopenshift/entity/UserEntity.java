package ro.wolfnet.helloopenshift.entity;

import java.io.Serializable;

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
public class UserEntity implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 5476745898491044394L;

  /** The id. */
  @Id
  @GeneratedValue
  private Long id;

  /** The name. */
  private String username;

  /** The email. */
  private String password;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}