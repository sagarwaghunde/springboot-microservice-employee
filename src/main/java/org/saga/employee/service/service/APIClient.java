package org.saga.employee.service.service;

import org.saga.employee.service.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8080", value = "DEPARTMENT-SERVICE")
public interface APIClient {

	// Build get department by code rest API
	@GetMapping("api/v1/departments/{dept-code}")
	DepartmentDto getDepartmentByCode(@PathVariable("dept-code") String deptCode);
	
}
