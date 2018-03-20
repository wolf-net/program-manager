package ro.wolfnet.programmanager.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.wolfnet.programmanager.model.RuleModel;

@RestController
public class RuleController {

	  @RequestMapping(value = "/rule", method = RequestMethod.PUT)
	  public ResponseEntity<Void> insertRule(@RequestBody RuleModel model) {
		    return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
	  }
	  
}
