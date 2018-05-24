package ro.wolfnet.programmanager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.wolfnet.programmanager.entity.StationEntity;
import ro.wolfnet.programmanager.model.StationModel;
import ro.wolfnet.programmanager.repository.StationRepository;

/**
 * The Class StationService.
 *
 * @author isti
 * @since Feb 12, 2018
 */
@Service
public class StationService {

  /** The station repository. */
  @Autowired
  private StationRepository stationRepository;

  /**
   * Find all.
   *
   * @return the list
   */
  public List<StationModel> findAll() {
    List<StationEntity> entities = this.stationRepository.findAll();
    List<StationModel> models = getModelsFromEntityes(entities);
    return models;
  }

  /**
   * Gets the models from entityes.
   *
   * @param entities the entities
   * @return the models from entityes
   */
  private List<StationModel> getModelsFromEntityes(List<StationEntity> entities) {
    if (entities == null) {
      return null;
    }
    List<StationModel> models = new ArrayList<>();
    for (StationEntity entity : entities) {
      StationModel model = new StationModel();
      model.setId(entity.getId());
      model.setName(entity.getName());
      model.setCapacity(entity.getCapacity());
      models.add(model);
    }
    return models;
  }

  /**
   * Save.
   *
   * @param model the model
   */
  public void save(StationModel model) {
    StationEntity entity = getEntityFromModel(model);
    if (entity == null) {
      return;
    }
    this.stationRepository.save(entity);
  }

  /**
   * Gets the entity from model.
   *
   * @param model the model
   * @return the entity from model
   */
  public StationEntity getEntityFromModel(StationModel model) {
    if (model == null) {
      return null;
    }

    StationEntity entity = new StationEntity();
    entity.setId(model.getId());
    entity.setName(model.getName());
    entity.setCapacity(model.getCapacity());
    return entity;
  }

  /**
   * Delete by id.
   *
   * @param stationId the station id
   */
  public void deleteById(long stationId) {
    this.stationRepository.delete(stationId);
  }

  /**
   * Gets the stations from ids.
   *
   * @param stations the stations
   * @return the stations from ids
   */
  public Set<StationEntity> getEntitiesFromIds(long[] stations) {
    if (stations == null) {
      return null;
    }

    Set<StationEntity> result = new HashSet<>();
    for (long stationId : stations) {
      result.add(getEntityFromParams(stationId));
    }
    return result;
  }

  /**
   * Gets the station entity.
   *
   * @param stationId the station id
   * @return the station entity
   */
  private StationEntity getEntityFromParams(long stationId) {
    StationEntity entity = new StationEntity();
    entity.setId(stationId);
    return entity;
  }

  /**
   * Gets the names from entities.
   *
   * @param stations the stations
   * @return the names from entities
   */
  public String[] getNamesFromEntities(Set<StationEntity> stations) {
    if (stations == null) {
      return null;
    }

    return stations.stream().map(station -> station.getName()).toArray(String[]::new);
  }

  /**
   * Gets the ids from entities.
   *
   * @param stations the stations
   * @return the ids from entities
   */
  public long[] getIdsFromEntities(Set<StationEntity> stations) {
    if (stations == null) {
      return null;
    }

    return stations.stream().map(station -> station.getId()).mapToLong(Long::longValue).toArray();
  }

  /**
   * Count.
   *
   * @return the long
   */
  public Long count() {
    return stationRepository.count();
  }

  /**
   * Gets the capacity for station id.
   *
   * @param stationId the station id
   * @return the capacity for station id
   */
  public Integer getCapacityForStationId(long stationId) {
    StationEntity station = stationRepository.findOne(stationId);
    if (station == null) {
      return null;
    }
    return station.getCapacity();
  }

  /**
   * Filter stations.
   *
   * @param filterDateWithoutProgram the filter date without program
   * @return the list
   */
  public List<StationModel> filterStations(Date filterDateWithoutProgram) {
    List<StationEntity> entities = null;
    if (filterDateWithoutProgram == null) {
      entities = this.stationRepository.findAll();
    }
    else {
      entities = this.stationRepository.filterByDateWithoutProgram(filterDateWithoutProgram);
    }

    List<StationModel> models = getModelsFromEntityes(entities);
    return models;
  }

}
