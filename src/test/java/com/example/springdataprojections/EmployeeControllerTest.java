package com.example.springdataprojections;

import com.example.springdataprojections.Projection.EmployeeProjection;
import com.example.springdataprojections.entity.Department;
import com.example.springdataprojections.entity.Employee;
import com.example.springdataprojections.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    private Employee employee;

    @BeforeEach
    void setUp() {

        Department department = new Department();
        department.setId(1L);
        department.setName("Отдел продаж");

        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Иван");
        employee.setLastName("Иванов");
        employee.setPosition("Директор");
        employee.setSalary(160000.00);
        employee.setDepartment(department);
    }

    @Test
    void testCreateEmployee() throws Exception {
        Mockito.when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Иван\",\"lastName\":\"Иванов\",\"position\":\"Директор\",\"salary\":160000.00,\"department\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Иван"))
                .andExpect(jsonPath("$.lastName").value("Иванов"))
                .andExpect(jsonPath("$.position").value("Директор"))
                .andExpect(jsonPath("$.salary").value(160000.00));
    }

    @Test
    void testGetAllEmployee() throws Exception {
        List<Employee> employees = Collections.singletonList(employee);
        Mockito.when(employeeService.getAllEmployee()).thenReturn(employees);

        mockMvc.perform(get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Иван"))
                .andExpect(jsonPath("$[0].lastName").value("Иванов"))
                .andExpect(jsonPath("$[0].position").value("Директор"));
    }

    @Test
    void testGetEmployeeById() throws Exception {


        Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Иван"))
                .andExpect(jsonPath("$.lastName").value("Иванов"))
                .andExpect(jsonPath("$.position").value("Директор"))
                .andExpect(jsonPath("$.salary").value(160000.00))
                .andExpect(jsonPath("$.department.id").value(1))
                .andExpect(jsonPath("$.department.name").value("Отдел продаж"));
    }

    @Test
    void testUpdateEmployee() throws Exception {

        Mockito.doNothing().when(employeeService).updateEmployee(eq(1L), any(Employee.class));

        mockMvc.perform(put("/api/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Иван\",\"lastName\":\"Иванов\",\"position\":\"Директор\",\"salary\":160000.00,\"department\":{\"id\":1,\"name\":\"Отдел продаж\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Иван"))
                .andExpect(jsonPath("$.lastName").value("Иванов"))
                .andExpect(jsonPath("$.position").value("Директор"))
                .andExpect(jsonPath("$.salary").value(160000.00))
                .andExpect(jsonPath("$.department.id").value(1))
                .andExpect(jsonPath("$.department.name").value("Отдел продаж"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        Mockito.doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employee/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetEmployeeProjection() throws Exception {

        EmployeeProjection employeeProjection = new EmployeeProjection() {
            @Override
            public String getFullName() {
                return "Иван Иванов";
            }

            @Override
            public String getPosition() {
                return "Директор";
            }

            @Override
            public String getDepartmentName() {
                return "Отдел продаж";
            }
        };

        Mockito.when(employeeService.getAllEmployeeProjections())
                .thenReturn(List.of(employeeProjection));

        mockMvc.perform(get("/api/employee/projections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Иван Иванов"))
                .andExpect(jsonPath("$[0].position").value("Директор"))
                .andExpect(jsonPath("$[0].departmentName").value("Отдел продаж"));
    }


}
