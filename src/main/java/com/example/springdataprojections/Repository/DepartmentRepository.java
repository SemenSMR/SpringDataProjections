package com.example.springdataprojections.Repository;

import com.example.springdataprojections.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
