package com.example.edm.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmployeeDTO {

	private Long empId;
    @NotBlank(message = "Employee name cannot be blank")
	private String name;
    
    @NotEmpty(message = "At least one department must be selected")
	private List<Long> depIds=new ArrayList<>();
}
