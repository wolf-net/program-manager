package ro.wolfnet.helloopenshift.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ro.wolfnet.helloopenshift.entity.UserEntity;
import ro.wolfnet.helloopenshift.repository.UserRepository;

/**
 * The Class UserService.
 *
 * @author isti
 * @since Feb 5, 2018
 */
@Service
public class UserService {

  /** The user repository. */
  @Autowired
  private UserRepository userRepository;

  /** The b crypt password encoder. */
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * Find all.
   *
   * @return the list
   */
  public List<UserEntity> findAll() {
    return this.userRepository.findAll();
  }

  /**
   * Dummy insert.
   */
  public void dummyInsert() {
    UserEntity user = new UserEntity();
    user.setUsername("u_" + System.currentTimeMillis());
    user.setPassword(bCryptPasswordEncoder.encode("u_" + System.currentTimeMillis()));
    this.userRepository.save(user);
  }
  
  public static void main(String[] args) {
    System.out.println(new BCryptPasswordEncoder().encode("isti"));
  }
  
}
