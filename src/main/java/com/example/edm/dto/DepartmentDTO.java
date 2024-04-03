package com.example.edm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentDTO {
	private Long depId;
	@NotBlank(message = "Department name cannot be blank")
	private String name;

}
