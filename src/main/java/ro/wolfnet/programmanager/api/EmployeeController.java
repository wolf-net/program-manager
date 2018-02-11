package ro.wolfnet.programmanager.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import ro.wolfnet.programmanager.service.EmployeeService;
import ro.wolfnet.programmanager.model.EmployeeModel;

@RestController
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @RequestMapping(value = "/employee", method = RequestMethod.GET)
  public ResponseEntity<List<EmployeeModel>> getEmployees() {
    List<EmployeeModel> employees = employeeService.findAll();
    return new ResponseEntity<List<EmployeeModel>>(employees, HttpStatus.OK);
  }

  @RequestMapping(value = "/employee", method = RequestMethod.DELETE)
  public ResponseEntity<Void> deleteEmployee(long employeeId) {
    employeeService.deleteById(employeeId);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @RequestMapping(value = "/employee", method = RequestMethod.POST)
  public ResponseEntity<Void> insertEmployee(@RequestBody EmployeeModel model) {
	employeeService.save(model);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

}
