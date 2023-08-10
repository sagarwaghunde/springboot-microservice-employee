package org.saga.employee.service.service;

import org.saga.employee.service.dto.APIResponseDto;
import org.saga.employee.service.dto.DepartmentDto;
import org.saga.employee.service.dto.EmployeeDto;
import org.saga.employee.service.entity.Employee;
import org.saga.employee.service.exception.EmailAlreadyExistsException;
import org.saga.employee.service.exception.ResourceNotFoundException;
import org.saga.employee.service.mapper.EmployeeMapper;
import org.saga.employee.service.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		employeeRepository.findByEmail(employeeDto.getEmail()).ifPresent(employee -> {
			throw new EmailAlreadyExistsException(String.format("Employee with '%s' email address already exists", employee.getEmail()));
		});
		Employee entity = EmployeeMapper.MAPPER.mapToEmployee(employeeDto);
		Employee savedEntity = employeeRepository.save(entity); 	
		return EmployeeMapper.MAPPER.mapToEmployeeDto(savedEntity);
	}

	@Override
	public APIResponseDto getEmployeeById(Long employeId) {
		Employee employee = employeeRepository.findById(employeId).orElseThrow(
			() -> new ResourceNotFoundException("Employee", "id", employeId.toString())
		);
		ResponseEntity<DepartmentDto> response = restTemplate.getForEntity("http://localhost:8080/api/v1/departments/" + employee.getDepartmentCode(), DepartmentDto.class);
		DepartmentDto departmentDto = response.getBody();
		EmployeeDto employeeDto = EmployeeMapper.MAPPER.mapToEmployeeDto(employee);
		APIResponseDto apiResponseDto = new APIResponseDto(employeeDto, departmentDto);
		return apiResponseDto;
	}

}
