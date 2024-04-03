package com.example.edm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.edm.entity.EmployeeVO;

public interface EmployeeRepo extends JpaRepository<EmployeeVO, Long> {

}
