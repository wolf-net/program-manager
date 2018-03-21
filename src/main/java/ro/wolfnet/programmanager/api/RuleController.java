package ro.wolfnet.programmanager.api;

import java.security.InvalidParameterException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.wolfnet.programmanager.model.RuleModel;
import ro.wolfnet.programmanager.service.RuleService;

/**
 * The Class RuleController.
 *
 * @author isti
 * @since Mar 21, 2018
 */
@RestController
public class RuleController {

  /** The rule service. */
  @Autowired
  private RuleService ruleService;

  /**
   * Insert rule.
   *
   * @param model the model
   * @return the response entity
   */
  @RequestMapping(value = "/rule", method = RequestMethod.PUT)
  public ResponseEntity<Void> insertRule(@RequestBody RuleModel model) {
    try {
      ruleService.saveVacationRule(model);
    } catch (InvalidParameterException e) {
      return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
    }
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  /**
   * Gets the rules.
   *
   * @param model the model
   * @return the rules
   */
  @RequestMapping(value = "/rule", method = RequestMethod.GET)
  public ResponseEntity<List<RuleModel>> getRules() {
    List<RuleModel> rules = ruleService.findRules();
    return new ResponseEntity<List<RuleModel>>(rules, HttpStatus.OK);
  }

}
