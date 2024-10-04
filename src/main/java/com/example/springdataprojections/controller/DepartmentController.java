package com.example.springdataprojections.controller;

import com.example.springdataprojections.entity.Department;
import com.example.springdataprojections.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        Department newDepartment = departmentService.addDepartment(department);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }

}
