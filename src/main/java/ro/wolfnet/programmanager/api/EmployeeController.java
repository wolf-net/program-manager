package ro.wolfnet.programmanager.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.wolfnet.programmanager.entity.EmployeeEntity;
import ro.wolfnet.programmanager.service.EmployeeService;

@RestController
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @RequestMapping(value = "/employee", method = RequestMethod.GET)
  public ResponseEntity<String> test() {
    String result = "";
    List<EmployeeEntity> employees = employeeService.findAll();
    if (employees != null) {
      for (EmployeeEntity employee : employees) {
        result += ", " + employee.getName();
      }
    }
    return new ResponseEntity<String>("Employees: " + result, HttpStatus.OK);
  }

}
