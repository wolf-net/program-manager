package ro.wolfnet.programmanager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ro.wolfnet.programmanager.entity.ProgramEntity;

/**
 * The Interface ProgramRepository.
 *
 * @author isti
 * @since Feb 15, 2018
 */
public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {

  /**
   * Find all.
   *
   * @param date the date
   * @return the list
   */
  @Query("SELECT p FROM ProgramEntity p where p.date = :date")
  List<ProgramEntity> findAll(@Param("date") Date date);
}
