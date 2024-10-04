package com.example.springdataprojections.Repository;

import com.example.springdataprojections.Projection.EmployeeProjection;
import com.example.springdataprojections.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT CONCAT(e.firstName, ' ', e.lastName) AS fullName, e.position AS position, d.name AS departmentName " +
            "FROM Employee e " +
            "LEFT JOIN e.department d")
    List<EmployeeProjection> findAllProjectedBy();
}
