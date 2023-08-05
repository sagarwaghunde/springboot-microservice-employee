package org.saga.employee.service.service;

import org.saga.employee.service.dto.EmployeeDto;
import org.saga.employee.service.entity.Employee;
import org.saga.employee.service.mapper.EmployeeMapper;
import org.saga.employee.service.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		Employee entity = EmployeeMapper.MAPPER.mapToEmployee(employeeDto);
		Employee savedEntity = employeeRepository.save(entity); 	
		return EmployeeMapper.MAPPER.mapToEmployeeDto(savedEntity);
	}

	@Override
	public EmployeeDto getEmployeeById(Long employeId) {
		Employee employee = employeeRepository.findById(employeId).get();
		return EmployeeMapper.MAPPER.mapToEmployeeDto(employee);
	}

}
