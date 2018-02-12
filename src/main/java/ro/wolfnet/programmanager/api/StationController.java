package ro.wolfnet.programmanager.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.wolfnet.programmanager.model.StationModel;
import ro.wolfnet.programmanager.service.StationService;

/**
 * The Class StationController.
 *
 * @author isti
 * @since Feb 12, 2018
 */
@RestController
public class StationController {

  /** The station service. */
  @Autowired
  private StationService stationService;

  /**
   * Insert employee.
   *
   * @return the response entity
   */
  @RequestMapping(value = "/station", method = RequestMethod.GET)
  public ResponseEntity<List<StationModel>> getStations() {
    List<StationModel> stations = stationService.findAll();
    return new ResponseEntity<List<StationModel>>(stations, HttpStatus.OK);
  }

  /**
   * Save station.
   *
   * @param model the model
   * @return the response entity
   */
  @RequestMapping(value = "/station", method = RequestMethod.POST)
  public ResponseEntity<Void> saveStation(@RequestBody StationModel model) {
    stationService.save(model);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  /**
   * Save station.
   *
   * @param stationId the station id
   * @return the response entity
   */
  @RequestMapping(value = "/station", method = RequestMethod.DELETE)
  public ResponseEntity<Void> saveStation(long stationId) {
    stationService.deleteById(stationId);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

}
