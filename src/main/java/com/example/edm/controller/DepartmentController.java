package com.example.edm.controller;

import java.util.List;
import java.util.Map;

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

import com.example.edm.dto.DepartmentDTO;
import com.example.edm.entity.DepartmentVO;
import com.example.edm.exception.DepartmentNotFoundException;
import com.example.edm.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@PostMapping
	public ResponseEntity<DepartmentVO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
		DepartmentVO createdDepartment = departmentService.createDepartment(departmentDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
	}

	@GetMapping("/{depId}")
	public ResponseEntity<DepartmentVO> getDepartmentById(@PathVariable Long depId) {
		DepartmentVO department = departmentService.getDepartmentById(depId)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + depId));
		return ResponseEntity.ok(department);
	}

	@GetMapping
	public ResponseEntity<List<DepartmentVO>> getAllDepartments() {
		List<DepartmentVO> departments = departmentService.getAllDepartments();
		return ResponseEntity.ok(departments);
	}

	@PutMapping("/{depId}")
	public ResponseEntity<DepartmentVO> updateDepartment(@PathVariable Long depId,
			@Valid @RequestBody DepartmentDTO departmentDTO) {
		DepartmentVO updatedDepartment = departmentService.updateDepartment(depId, departmentDTO);
		return ResponseEntity.ok(updatedDepartment);
	}

	@DeleteMapping("/{depId}")
	public ResponseEntity<Void> deleteDepartment(@PathVariable Long depId) {
		departmentService.deleteDepartment(depId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/employee-count-by-department")
	public ResponseEntity<Map<String,Long>> getEmployeeCountByDepartment() {
		return ResponseEntity.ok(departmentService.getEmployeeCountByDepartment());
	}
}
