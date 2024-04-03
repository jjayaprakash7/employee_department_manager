package com.example.edm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.edm.entity.DepartmentVO;

public interface DepartmentRepo extends JpaRepository<DepartmentVO, Long> {

	@Query(value = "SELECT d.name, COUNT(ed.department_id) FROM department d LEFT JOIN employee_department ed ON d.dep_id = ed.department_id GROUP BY d.name", nativeQuery = true)
	List<Object[]> countEmployeesPerDepartment();

}
