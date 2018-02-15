package ro.wolfnet.programmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.wolfnet.programmanager.entity.ProgramEntity;

/**
 * The Interface ProgramRepository.
 *
 * @author isti
 * @since Feb 15, 2018
 */
public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {
}
