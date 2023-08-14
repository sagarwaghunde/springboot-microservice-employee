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
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
//	private RestTemplate restTemplate;
	private WebClient webClient;
//	private APIClient apiClient;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		employeeRepository.findByEmail(employeeDto.getEmail()).ifPresent(employee -> {
			throw new EmailAlreadyExistsException(
					String.format("Employee with '%s' email address already exists", employee.getEmail()));
		});
		Employee entity = EmployeeMapper.MAPPER.mapToEmployee(employeeDto);
		Employee savedEntity = employeeRepository.save(entity);
		return EmployeeMapper.MAPPER.mapToEmployeeDto(savedEntity);
	}

	@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
	@Override
	public APIResponseDto getEmployeeById(Long employeId) {
		Employee employee = employeeRepository.findById(employeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeId.toString()));

//		ResponseEntity<DepartmentDto> response = restTemplate.getForEntity("http://localhost:8080/api/v1/departments/" + employee.getDepartmentCode(), DepartmentDto.class);
//		DepartmentDto departmentDto = response.getBody();
		DepartmentDto departmentDto = webClient.get()
				.uri("http://localhost:8080/api/v1/departments/" + employee.getDepartmentCode()).retrieve()
				.bodyToMono(DepartmentDto.class).block(); // synchronous call

//		DepartmentDto departmentDto = apiClient.getDepartmentByCode(employee.getDepartmentCode());
		EmployeeDto employeeDto = EmployeeMapper.MAPPER.mapToEmployeeDto(employee);
		APIResponseDto apiResponseDto = new APIResponseDto(employeeDto, departmentDto);
		return apiResponseDto;
	}

	public APIResponseDto getDefaultDepartment(Long employeId, Exception exception) {
		Employee employee = employeeRepository.findById(employeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeId.toString()));

		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDepartmentCode("DF001");
		departmentDto.setDepartmentName("Default Dept");
		departmentDto.setDepartmentDescription("this is default department");

		EmployeeDto employeeDto = EmployeeMapper.MAPPER.mapToEmployeeDto(employee);
		APIResponseDto apiResponseDto = new APIResponseDto(employeeDto, departmentDto);
		return apiResponseDto;

	}

}
