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

import ro.wolfnet.programmanager.model.ProgramModel;
import ro.wolfnet.programmanager.service.ProgramService;
import ro.wolfnet.programmanager.utils.IncompatibleRulesException;

/**
 * The Class ProgramController.
 *
 * @author isti
 * @since Feb 15, 2018
 */
@RestController
public class ProgramController {

  /** The program service. */
  @Autowired
  private ProgramService programService;

  /**
   * Gets the programs.
   *
   * @return the programs
   */
  @RequestMapping(value = "/programDay", method = RequestMethod.GET)
  public ResponseEntity<List<ProgramModel>> getPrograms(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dayOfProgram) {
    List<ProgramModel> programs = programService.findAllForOneDay(dayOfProgram);
    return new ResponseEntity<List<ProgramModel>>(programs, HttpStatus.OK);
  }
  
  /**
   * Generate program for one day.
   *
   * @param dayOfProgram the day of program
   * @return the response entity
   */
  @RequestMapping(value = "/programDay", method = RequestMethod.PUT)
  public ResponseEntity<Void> generateProgramsForOneDay(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dayOfProgram) {
    try {
      programService.generateProgramsForOneDay(dayOfProgram);
      return new ResponseEntity<Void>(HttpStatus.OK);
    }catch (IncompatibleRulesException e) {
      return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
  }
  
  /**
   * Generate programs for one month.
   *
   * @param dayOfProgram the day of program
   * @return the response entity
   */
  @RequestMapping(value = "/programMonth", method = RequestMethod.PUT)
  public ResponseEntity<Void> generateProgramsForOneMonth(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dayOfProgram) {
    try {
      programService.generateProgramsForOneMonth(dayOfProgram);
      return new ResponseEntity<Void>(HttpStatus.OK);
    }catch (IncompatibleRulesException e) {
      return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
  }

}
