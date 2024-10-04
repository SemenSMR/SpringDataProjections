package com.example.springdataprojections.service;

import com.example.springdataprojections.Projection.EmployeeProjection;
import com.example.springdataprojections.Repository.EmployeeRepository;
import com.example.springdataprojections.entity.Employee;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    public List <Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + id + " not found"));
    }

    public void updateEmployee(Long id, Employee employee) {
        employee.setId(id);
        employeeRepository.save(employee);
    }
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    public List<EmployeeProjection> getAllEmployeeProjections() {
        return employeeRepository.findAllProjectedBy();
    }
}
