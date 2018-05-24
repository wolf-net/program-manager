package ro.wolfnet.programmanager.repository;

import java.util.Date;
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

  /**
   * Gets the stations of employee.
   *
   * @param employeeId the employee id
   * @return the stations of employee
   */
  @Query("SELECT e.stations FROM EmployeeEntity e where e.id in :employeeId")
  List<StationEntity> getStationsOfEmployee(@Param("employeeId") long employeeId);

  /**
   * Filter by date without program.
   *
   * @param filterDateWithoutProgram the filter date without program
   * @return the list
   */
  @Query("SELECT s FROM StationEntity s WHERE s.id not in (SELECT DISTINCT p.station.id FROM ProgramEntity p WHERE p.date = :date) ")
  List<StationEntity> filterByDateWithoutProgram(@Param("date") Date date);
}