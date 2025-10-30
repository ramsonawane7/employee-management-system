package com.empsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empsys.entity.*;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
