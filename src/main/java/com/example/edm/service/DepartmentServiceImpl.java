package com.example.edm.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.edm.constant.CommonConstant;
import com.example.edm.dto.DepartmentDTO;
import com.example.edm.entity.DepartmentVO;
import com.example.edm.exception.DepartmentDeleteException;
import com.example.edm.exception.DepartmentNotFoundException;
import com.example.edm.exception.DuplicateEntityException;
import com.example.edm.repo.DepartmentRepo;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	@Autowired
	DepartmentRepo departmentRepo;

	@Override
	public DepartmentVO createDepartment(DepartmentDTO departmentDTO) {
		DepartmentVO department = new DepartmentVO();
		mapDepartmentVOFromDepartmentDTO(department, departmentDTO);
		try {
			department = departmentRepo.save(department);
			LOGGER.info(CommonConstant.AUDIT_LOG_MSG, CommonConstant.OPERATION_CREATE,
					CommonConstant.ENTITY_TYPE_DEPARTMENT, department.getDepId());
			return department;
		} catch (DataIntegrityViolationException ex) {
			throw new DuplicateEntityException("Duplicate department name: " + departmentDTO.getName());
		}
	}

	@Override
	public Optional<DepartmentVO> getDepartmentById(Long depId) {
		return departmentRepo.findById(depId);
	}

	@Override
	public List<DepartmentVO> getAllDepartments() {
		return departmentRepo.findAll();
	}

	@Override
	public DepartmentVO updateDepartment(Long depId, DepartmentDTO departmentDTO) {
		DepartmentVO department = departmentRepo.findById(depId)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + depId));
		mapDepartmentVOFromDepartmentDTO(department, departmentDTO);
		try {
			LOGGER.info(CommonConstant.AUDIT_LOG_MSG, CommonConstant.OPERATION_UPDATE,
					CommonConstant.ENTITY_TYPE_DEPARTMENT, depId);
			return departmentRepo.save(department);
		} catch (DataIntegrityViolationException ex) {
			throw new DuplicateEntityException("Duplicate department name: " + departmentDTO.getName());
		}
	}

	@Override
	public void deleteDepartment(Long depId) {
		if (!departmentRepo.existsById(depId)) {
			throw new DepartmentNotFoundException("Department not found with ID: " + depId);
		}
		try {
			LOGGER.info(CommonConstant.AUDIT_LOG_MSG, CommonConstant.OPERATION_DELETE,
					CommonConstant.ENTITY_TYPE_DEPARTMENT, depId);
			departmentRepo.deleteById(depId);
		} catch (DataIntegrityViolationException e) {
			throw new DepartmentDeleteException("Cannot delete department with associated employees");
		}
	}

	private void mapDepartmentVOFromDepartmentDTO(DepartmentVO departmentVO, DepartmentDTO departmentDTO) {
		departmentVO.setName(departmentDTO.getName());
	}

	@Override
	public Map<String, Long> getEmployeeCountByDepartment() {
		return departmentRepo.countEmployeesPerDepartment().stream()
				.collect(Collectors.toMap(arr -> (String) arr[0], arr -> (Long) arr[1]));
	}

}
