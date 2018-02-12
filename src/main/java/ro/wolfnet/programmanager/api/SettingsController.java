package ro.wolfnet.programmanager.api;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.wolfnet.programmanager.model.SettingsModel;
import ro.wolfnet.programmanager.service.UserService;

/**
 * The Class SettingsController.
 *
 * @author isti
 * @since Feb 12, 2018
 */
@RestController
public class SettingsController {

  /** The user service. */
  @Autowired
  private UserService userService;

  /**
   * Insert employee.
   *
   * @param model the model
   * @param principal the principal
   * @return the response entity
   */
  @RequestMapping(value = "/settings", method = RequestMethod.POST)
  public ResponseEntity<Void> saveSettings(@RequestBody SettingsModel model, Principal principal, HttpServletRequest request) {
    if (model.getNewPassword() != null && !model.getNewPassword().equals("")) {
      userService.changePassword(principal.getName(), model.getNewPassword());
      ((SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().setAuthenticated(false);
    }
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

}
