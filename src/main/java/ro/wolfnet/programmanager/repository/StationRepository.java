package ro.wolfnet.programmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ro.wolfnet.programmanager.entity.StationEntity;

/**
 * The Interface StationRepository.
 *
 * @author isti
 * @since Feb 12, 2018
 */
public interface StationRepository extends JpaRepository<StationEntity, Long> {

	@Query("SELECT e.stations FROM EmployeeEntity e where e.id in :employeeId")
	List<StationEntity> getStationsOfEmployee(@Param("employeeId") long employeeId);
}