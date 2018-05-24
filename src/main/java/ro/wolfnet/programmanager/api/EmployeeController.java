package ro.wolfnet.programmanager.api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import ro.wolfnet.programmanager.service.EmployeeService;
import ro.wolfnet.programmanager.model.EmployeeModel;

/**
 * The Class EmployeeController.
 *
 * @author isti
 * @since May 23, 2018
 */
@RestController
public class EmployeeController {

  /** The employee service. */
  @Autowired
  private EmployeeService employeeService;

  /**
   * Gets the employees.
   *
   * @return the employees
   */
  @RequestMapping(value = "/employee", method = RequestMethod.GET)
  public ResponseEntity<List<EmployeeModel>> getEmployees(@RequestParam(required = false) Long filterStationId,
                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date filterDate) {
    List<EmployeeModel> employees = employeeService.filterEmployees(filterStationId, filterDate);
    return new ResponseEntity<List<EmployeeModel>>(employees, HttpStatus.OK);
  }

  /**
   * Delete employee.
   *
   * @param employeeId the employee id
   * @return the response entity
   */
  @RequestMapping(value = "/employee", method = RequestMethod.DELETE)
  public ResponseEntity<Void> deleteEmployee(long employeeId) {
    employeeService.deleteById(employeeId);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  /**
   * Insert employee.
   *
   * @param model the model
   * @return the response entity
   */
  @RequestMapping(value = "/employee", method = RequestMethod.POST)
  public ResponseEntity<Void> insertEmployee(@RequestBody EmployeeModel model) {
    employeeService.save(model);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

}
