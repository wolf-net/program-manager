package ro.wolfnet.programmanager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
  List<ProgramEntity> findAllByDate(@Param("date") Date date);

  /**
   * Delete by date.
   *
   * @param date the date
   */
  @Modifying
  @Transactional
  @Query("DELETE FROM ProgramEntity p where p.date = :date")
  void deleteByDate(@Param("date") Date date);

  /**
   * Delete by date.
   *
   * @param date the date
   */
  @Modifying
  @Transactional
  @Query("DELETE FROM ProgramEntity p where p.date = :date or p.date < :date")
  void deleteByDateOlderEqual(@Param("date") Date date);
}
