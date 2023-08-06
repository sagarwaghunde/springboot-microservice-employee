package org.saga.employee.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmployeeDto {
	private Long id;
	
	@NotBlank(message = "firstname should not be blank")
	private String firstname;

	@NotBlank(message = "lastname should not be blank")
	private String lastname;
	
	@NotBlank(message = "Employee email should not be empty")
	@Email(message = "Email address should be valid")
	private String email;

	public EmployeeDto() {
	}

	public EmployeeDto(Long id, String firstname, String lastname, String email) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
