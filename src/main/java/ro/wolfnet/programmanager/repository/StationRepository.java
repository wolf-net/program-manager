package ro.wolfnet.programmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.wolfnet.programmanager.entity.StationEntity;

/**
 * The Interface StationRepository.
 *
 * @author isti
 * @since Feb 12, 2018
 */
public interface StationRepository extends JpaRepository<StationEntity, Long> {
}