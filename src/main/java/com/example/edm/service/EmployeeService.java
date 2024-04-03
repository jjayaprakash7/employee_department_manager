package com.example.edm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.edm.dto.EmployeeDTO;
import com.example.edm.entity.EmployeeVO;

@Service
public interface EmployeeService {

	EmployeeVO createEmployee(EmployeeDTO employeeDTO);

	List<EmployeeVO> getAllEmployees();

	EmployeeVO getEmployeeById(Long id);

	EmployeeVO updateEmployee(Long id, EmployeeDTO employeeDTO);

	void deleteEmployee(Long id);

}
