package ro.wolfnet.programmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ro.wolfnet.programmanager.entity.UserEntity;
import ro.wolfnet.programmanager.model.SettingsModel;
import ro.wolfnet.programmanager.repository.UserRepository;

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

  /**
   * Change password.
   *
   * @param userName the user name
   * @param newPassword the new password
   * @param exportProgramColumns the export program columns
   */
  public void saveSettings(String userName, String newPassword, int[] exportProgramColumns) {
    for (UserEntity user : userRepository.findAll()) {
      if (user.getUsername().equals(userName)) {
        if (newPassword != null && !newPassword.equals("")) {
          user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        }
        user.setExportProgramColumns(exportProgramColumns);
        userRepository.save(user);
        break;
      }
    }
  }

  /**
   * Gets the user.
   *
   * @param userName the user name
   * @return the user
   */
  public SettingsModel getSettings(String userName) {
    for (UserEntity user : userRepository.findAll()) {
      if (user.getUsername().equals(userName)) {
        return getModelFromEntity(user);
      }
    }
    return null;
  }

  /**
   * Gets the model from entity.
   *
   * @param user the user
   * @return the model from entity
   */
  private SettingsModel getModelFromEntity(UserEntity user) {
    if (user == null) {
      return null;
    }
    
    SettingsModel model = new SettingsModel();
    model.setExportColumns(user.getExportProgramColumns());
    return model;
  }

}