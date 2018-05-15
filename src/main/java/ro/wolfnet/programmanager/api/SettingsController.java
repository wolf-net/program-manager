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
import ro.wolfnet.programmanager.service.ProgramService;
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

  /** The program service. */
  @Autowired
  private ProgramService programService;

  /**
   * Insert employee.
   *
   * @param model the model
   * @param principal the principal
   * @param request the request
   * @return the response entity
   */
  @RequestMapping(value = "/settings", method = RequestMethod.POST)
  public ResponseEntity<Void> saveSettings(@RequestBody SettingsModel model, Principal principal, HttpServletRequest request) {
    userService.saveSettings(principal.getName(), model.getNewPassword(), model.getExportColumns());
    programService.deleteOlderEqualThan(model.getDeleteOlder());

    if (model.getNewPassword() != null && !model.getNewPassword().equals("")) {
      ((SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().setAuthenticated(false);
    }
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  /**
   * Gets the settings.
   *
   * @param principal the principal
   * @return the settings
   */
  @RequestMapping(value = "/settings", method = RequestMethod.GET)
  public ResponseEntity<SettingsModel> getSettings(Principal principal) {
    SettingsModel model = userService.getSettings(principal.getName());
    return new ResponseEntity<SettingsModel>(model, HttpStatus.OK);
  }

}
