package org.saga.employee.service.repository;

import java.util.Optional;

import org.saga.employee.service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	Optional<Employee> findByEmail(String email);
}
