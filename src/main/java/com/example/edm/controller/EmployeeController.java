package com.example.edm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.edm.dto.EmployeeDTO;
import com.example.edm.entity.EmployeeVO;
import com.example.edm.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<EmployeeVO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		EmployeeVO createdEmployee = employeeService.createEmployee(employeeDTO);
		return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<EmployeeVO>> getAllEmployees() {
		List<EmployeeVO> employees = employeeService.getAllEmployees();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@GetMapping("/{empId}")
	public ResponseEntity<EmployeeVO> getEmployeeById(@PathVariable Long empId) {
		EmployeeVO employee = employeeService.getEmployeeById(empId);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@PutMapping("/{empId}")
	public ResponseEntity<EmployeeVO> updateEmployee(@PathVariable Long empId, @Valid @RequestBody EmployeeDTO employeeDTO) {
		EmployeeVO updatedEmployee = employeeService.updateEmployee(empId, employeeDTO);
		return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
	}

	@DeleteMapping("/{empId}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long empId) {
		employeeService.deleteEmployee(empId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
