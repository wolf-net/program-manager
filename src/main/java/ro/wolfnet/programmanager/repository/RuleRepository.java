package ro.wolfnet.programmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.wolfnet.programmanager.entity.RuleBaseEntity;

/**
 * The Interface RuleRepository.
 *
 * @author isti
 * @since Mar 21, 2018
 */
public interface RuleRepository extends JpaRepository<RuleBaseEntity, Long> {

}
