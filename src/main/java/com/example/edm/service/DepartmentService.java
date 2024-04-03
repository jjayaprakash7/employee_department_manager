package com.example.edm.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.edm.dto.DepartmentDTO;
import com.example.edm.entity.DepartmentVO;

@Service
public interface DepartmentService {

	DepartmentVO createDepartment(DepartmentDTO departmentDTO);

	Optional<DepartmentVO> getDepartmentById(Long depId);

	List<DepartmentVO> getAllDepartments();

	DepartmentVO updateDepartment(Long depId, DepartmentDTO departmentDTO);

	void deleteDepartment(Long depId);

	Map<String,Long> getEmployeeCountByDepartment();

}
