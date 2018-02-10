package ro.wolfnet.programmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.wolfnet.programmanager.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}