package org.saga.employee.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.saga.employee.service.dto.EmployeeDto;
import org.saga.employee.service.entity.Employee;

@Mapper
public interface EmployeeMapper {
	// entrypoint
	EmployeeMapper MAPPER = Mappers.getMapper(EmployeeMapper.class);
	
	// Source and target objects should have same filed names for default implementation
	// if field names are not same, mapping can be used
	EmployeeDto mapToEmployeeDto(Employee employee);
	
	Employee mapToEmployee(EmployeeDto employee);
}