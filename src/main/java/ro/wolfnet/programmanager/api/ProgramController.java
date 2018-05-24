package ro.wolfnet.programmanager.api;

import java.io.InputStream;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ro.wolfnet.programmanager.model.ProgramModel;
import ro.wolfnet.programmanager.service.ProgramService;
import ro.wolfnet.programmanager.utils.IncompatibleRulesException;
import ro.wolfnet.programmanager.utils.Utils;

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
   * @param dayOfProgram the day of program
   * @return the programs
   */
  @RequestMapping(value = "/programDay", method = RequestMethod.GET)
  public ResponseEntity<List<ProgramModel>> getPrograms(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dayOfProgram) {
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
  public ResponseEntity<Void> generateProgramsForOneDay(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dayOfProgram) {
    try {
      programService.generateProgramsForOneDay(dayOfProgram);
      return new ResponseEntity<Void>(HttpStatus.OK);
    } catch (IncompatibleRulesException e) {
      return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
  }

  /**
   * Generate program for one day.
   *
   * @param stationId the station id
   * @param dayOfProgram the day of program
   * @return the response entity
   */
  @RequestMapping(value = "/programDay", method = RequestMethod.DELETE)
  public ResponseEntity<Void> deleteProgramsForOneDay(@RequestParam long stationId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dayOfProgram) {
    programService.deleteForStationAndDate(stationId, dayOfProgram);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  /**
   * Generate programs for one month.
   *
   * @param dayOfProgram the day of program
   * @return the response entity
   */
  @RequestMapping(value = "/programMonth", method = RequestMethod.PUT)
  public ResponseEntity<Void> generateProgramsForOneMonth(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dayOfProgram) {
    try {
      programService.generateProgramsForOneMonth(dayOfProgram);
      return new ResponseEntity<Void>(HttpStatus.OK);
    } catch (IncompatibleRulesException e) {
      return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
  }

  /**
   * Export programs for one month.
   *
   * @param dayOfProgram the day of program
   * @param response the response
   * @param principal the principal
   * @throws Exception the exception
   */
  @RequestMapping(value = "/generateProgramMonth", method = RequestMethod.GET)
  public void exportProgramsForOneMonth(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dayOfProgram, HttpServletResponse response,
                                        Principal principal) throws Exception {
    InputStream is = programService.exportProgramForOneMonth(dayOfProgram, principal.getName());
    IOUtils.copy(is, response.getOutputStream());
    response.flushBuffer();
  }

  /**
   * Generate programs for one month.
   *
   * @param model the model
   * @return the response entity
   */
  @RequestMapping(value = "/programManually", method = RequestMethod.POST)
  public ResponseEntity<String> saveProgramManually(@RequestBody ProgramModel model) {
    String errorMessage = programService.saveManuallyProgram(model);
    if (!Utils.isNullOrEmpty(errorMessage)) {
      return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<String>("", HttpStatus.OK);
  }

}
