package ro.wolfnet.helloopenshift.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.wolfnet.helloopenshift.entity.UserEntity;

/**
 * The Interface UserRepository.
 *
 * @author isti
 * @since Feb 5, 2018
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}