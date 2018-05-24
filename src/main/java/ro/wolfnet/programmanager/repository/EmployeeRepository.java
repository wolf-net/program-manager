package ro.wolfnet.programmanager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ro.wolfnet.programmanager.entity.EmployeeEntity;

/**
 * The Interface EmployeeRepository.
 *
 * @author isti
 * @since May 23, 2018
 */
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

  /**
   * Filter.
   *
   * @param station the station
   * @param date the date
   * @return the list
   */
  @Query("SELECT DISTINCT e FROM EmployeeEntity e JOIN FETCH e.stations s LEFT JOIN e.programs p WHERE s.id = :station and (p is null or p.date != :date or p.station.id = :station)")
  List<EmployeeEntity> findByStationAndDate(@Param("station") Long station, @Param("date") Date date);

}