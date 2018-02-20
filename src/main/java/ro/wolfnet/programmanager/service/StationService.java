package ro.wolfnet.programmanager.service;

import java.util.ArrayList;
import java.util.List;

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

}
