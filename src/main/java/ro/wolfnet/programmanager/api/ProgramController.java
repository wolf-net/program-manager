package ro.wolfnet.programmanager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.wolfnet.programmanager.service.ProgramService;

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
  @RequestMapping(value = "/program", method = RequestMethod.GET)
  public ResponseEntity<Void> getPrograms() {
    programService.findAll();
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

}
