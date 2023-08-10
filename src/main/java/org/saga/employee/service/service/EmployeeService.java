package org.saga.employee.service.service;

import org.saga.employee.service.dto.APIResponseDto;
import org.saga.employee.service.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto saveEmployee(EmployeeDto employeeDto);
	
	APIResponseDto getEmployeeById(Long employeId);
}
