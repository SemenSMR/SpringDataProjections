package com.example.springdataprojections.service;

import com.example.springdataprojections.Repository.DepartmentRepository;
import com.example.springdataprojections.entity.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {


    private final DepartmentRepository departmentRepository;

    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }
}
