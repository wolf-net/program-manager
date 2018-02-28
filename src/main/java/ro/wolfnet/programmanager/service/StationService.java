package ro.wolfnet.programmanager.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.wolfnet.programmanager.entity.StationEntity;
import ro.wolfnet.programmanager.model.StationModel;
import ro.wolfnet.programmanager.repository.StationRepository;
import ro.wolfnet.programmanager.utils.Utils;

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
  public Set<StationEntity> getEntitiesFromIds(String[] stations) {
    if (stations == null) {
      return null;
    }

    return Arrays.asList(stations).stream().map(stationId -> getEntityFromParams(Utils.getIntAttribute(stationId))).collect(Collectors.toSet());
  }

  /**
   * Gets the station entity.
   *
   * @param stationId the station id
   * @return the station entity
   */
  private StationEntity getEntityFromParams(int stationId) {
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
  public String[] getIdsFromEntities(Set<StationEntity> stations) {
    if (stations == null) {
      return null;
    }

    return stations.stream().map(station -> String.valueOf(station.getId())).toArray(String[]::new);
  }

}
