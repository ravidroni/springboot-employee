package com.springboot.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void addEmployee() throws Exception {

        // given - precondition or setup
        Employee employee = Employee.builder()
                .name("ravi")
                .email("ravi@gmail.com")
                .role("developer")
                .build();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(employee.getName())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())))
                .andExpect(jsonPath("$.role",
                        is(employee.getRole())));

    }

    // JUnit test for Get All employees REST API
    @Test
    public void getAllEmployees() throws Exception {
        // given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<Employee>();
        listOfEmployees.add(Employee.builder().name("ravi").email("ravi@gmail.com").role("developer").build());
        listOfEmployees.add(Employee.builder().name("arun").email("arun@gmail.com").role("tester").build());
        employeeRepository.saveAll(listOfEmployees);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));


    }

    // positive scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void getEmployeeById() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .name("ravi")
                .email("ravi@gmail.com")
                .role("developer")
                .build();

        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(employee.getName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())))
                .andExpect(jsonPath("$.role", is(employee.getRole())));

    }

    // negative scenario - valid employee id
    // JUnit test for GET employee Not found by id REST API
    @Test
    public void getEmployeeNotFoundById() throws Exception {
        // given - precondition or setup
        int employeeId = 2;
        Employee employee = Employee.builder()
                .name("ravi")
                .email("ravi@gmail.com")
                .role("developer")
                .build();

        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    // JUnit test for update employee REST API - positive scenario
    @Test
    public void updateEmployee() throws Exception {
        // given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .name("ravi")
                .email("ravi@gmail.com")
                .role("developer")
                .build();

        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .name("ramesh")
                .email("ramesh@gmail.com")
                .role("developer")
                .build();


        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedEmployee.getName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())))
                .andExpect(jsonPath("$.role", is(updatedEmployee.getRole())));
    }

    // JUnit test for update employee Not Found REST API - negative scenario
    @Test
    public void updateEmployeeNotFound() throws Exception {
        // given - precondition or setup
        int employeeId = 2;
        Employee savedEmployee = Employee.builder()
                .name("ravi")
                .email("ravi@gmail.com")
                .role("developer")
                .build();

        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .name("ramesh")
                .email("ramesh@gmail.com")
                .role("developer")
                .build();


        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // JUnit test for delete employee REST API
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        // given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .name("ravi")
                .email("ravi@gmail.com")
                .role("developer")
                .build();

        employeeRepository.save(savedEmployee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", savedEmployee.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}