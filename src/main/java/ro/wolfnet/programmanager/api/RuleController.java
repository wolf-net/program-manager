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
      if ("saveVacation".equals(model.getOperation())) {
        ruleService.saveVacationRule(model);
      }
      else if ("saveWorkTogether".equals(model.getOperation())) {
        ruleService.saveWorkTogetherRule(model);
      }
      else {
        throw new InvalidParameterException("Untreated operation: " + model.getOperation());
      }
    } catch (InvalidParameterException e) {
      return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
    }
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  /**
   * Gets the rules.
   *
   * @return the rules
   */
  @RequestMapping(value = "/rule", method = RequestMethod.GET)
  public ResponseEntity<List<RuleModel>> getRules() {
    List<RuleModel> rules = ruleService.findRuleModels();
    return new ResponseEntity<List<RuleModel>>(rules, HttpStatus.OK);
  }

  /**
   * Delete employee.
   *
   * @param ruleId the rule id
   * @return the response entity
   */
  @RequestMapping(value = "/rule", method = RequestMethod.DELETE)
  public ResponseEntity<Void> deleteEmployee(long ruleId) {
    ruleService.deleteById(ruleId);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

}
