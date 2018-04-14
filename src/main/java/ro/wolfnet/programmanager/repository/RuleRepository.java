package ro.wolfnet.programmanager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ro.wolfnet.programmanager.entity.RuleBaseEntity;
import ro.wolfnet.programmanager.entity.RuleVacationEntity;

/**
 * The Interface RuleRepository.
 *
 * @author isti
 * @since Mar 21, 2018
 */
public interface RuleRepository extends JpaRepository<RuleBaseEntity, Long> {
	
	@Query("SELECT r FROM RuleVacationEntity r WHERE r.start <= ?1 AND r.end >= ?2")
	List<RuleVacationEntity> findActiveVacations(Date from, Date to);

}
