package com.example.edm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.edm.constant.CommonConstant;
import com.example.edm.dto.EmployeeDTO;
import com.example.edm.entity.DepartmentVO;
import com.example.edm.entity.EmployeeVO;
import com.example.edm.exception.DepartmentNotFoundException;
import com.example.edm.exception.DuplicateEntityException;
import com.example.edm.exception.EmployeeNotFoundException;
import com.example.edm.repo.DepartmentRepo;
import com.example.edm.repo.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Override
	public EmployeeVO createEmployee(EmployeeDTO employeeDTO) {
		EmployeeVO employee = new EmployeeVO();
		mapEmployeeVOFromEmployeeDTO(employee, employeeDTO);
		List<DepartmentVO> departments = departmentRepo.findAllById(employeeDTO.getDepIds());
		if (departments.isEmpty()) {
			throw new DepartmentNotFoundException("Departments not found with provided IDs");
		}
		employee.setDepartments(departments);
		try {
			employee = employeeRepo.save(employee);
			LOGGER.info(CommonConstant.AUDIT_LOG_MSG, CommonConstant.OPERATION_CREATE,
					CommonConstant.ENTITY_TYPE_EMPLOYEE, employee.getEmpId());
			return employee;
		} catch (DataIntegrityViolationException ex) {
			throw new DuplicateEntityException("Duplicate employee name: " + employeeDTO.getName());
		}
	}

	@Override
	public List<EmployeeVO> getAllEmployees() {
		return employeeRepo.findAll();
	}

	@Override
	public EmployeeVO getEmployeeById(Long id) {
		return employeeRepo.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
	}

	@Override
	public EmployeeVO updateEmployee(Long id, EmployeeDTO employeeDTO) {
		EmployeeVO existingEmployee = getEmployeeById(id);
		mapEmployeeVOFromEmployeeDTO(existingEmployee, employeeDTO);
		List<DepartmentVO> departments = departmentRepo.findAllById(employeeDTO.getDepIds());
		if (departments.isEmpty()) {
			throw new DepartmentNotFoundException("Departments not found with provided IDs");
		}
		existingEmployee.setDepartments(departments);
		try {
			LOGGER.info(CommonConstant.AUDIT_LOG_MSG, CommonConstant.OPERATION_UPDATE,
					CommonConstant.ENTITY_TYPE_EMPLOYEE, id);
			return employeeRepo.save(existingEmployee);
		} catch (DataIntegrityViolationException ex) {
			throw new DuplicateEntityException("Duplicate employee name: " + employeeDTO.getName());
		}
	}

	@Override
	public void deleteEmployee(Long id) {
		EmployeeVO employee = getEmployeeById(id);
		LOGGER.info(CommonConstant.AUDIT_LOG_MSG, CommonConstant.OPERATION_DELETE, CommonConstant.ENTITY_TYPE_EMPLOYEE,
				id);
		employeeRepo.delete(employee);
	}

	private void mapEmployeeVOFromEmployeeDTO(EmployeeVO employeeVO, EmployeeDTO employeeDTO) {
		employeeVO.setName(employeeDTO.getName());
	}

}
