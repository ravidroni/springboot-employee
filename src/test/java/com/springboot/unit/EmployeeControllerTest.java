package com.springboot.unit;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.SpringbootEmployeeApplication;
import com.springboot.controller.EmployeeController;
import com.springboot.model.Employee;
import com.springboot.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;






@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(SpringbootEmployeeApplication.class);
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    EmployeeServiceImpl employeeServiceImpl;




    @Test
    public void getListOfEmployees() {
        List<Employee> employees = employeeServiceImpl.getAll();
        assertThat(employees.size()).isGreaterThan(-1);
        if(employees.size() >= 0) {
            System.out.println("Getting employees ");
        }
        else {
            System.out.println("Some error retrieving employees");
        }
    }
    @Test
    public void getEmployeeById() {

        employeeServiceImpl.findById(1);
    }
    @Test
    void getEmployeeByIdNotFound() throws Exception {
        doReturn(Optional.empty()).when(employeeServiceImpl).findById(1);

        // Execute the service call
        Optional<Employee> emp = employeeServiceImpl.findById(1);

        // Assert the response
        assertFalse(emp.isPresent(), "Employee should not be found");
    }


    @Test
    void saveEmployee() {
        Employee employee = new Employee(1,"ram","ram@gmail.com","developer");
        Employee employeeReturned = employeeServiceImpl.save(employee);


    }



    @Test
    void updateEmployee() {
        Employee employee = new Employee(1,"ram","ram@gmail.com","developer");
        Employee employeeReturned = employeeServiceImpl.save(employee);
        employee.setEmail("ramkumar@gmail.com");
        assertEquals(employee.getEmail(),"ramkumar@gmail.com");
    }
    @Test
    void updateEmployeeNotFound() throws Exception {
        // Setup our mocked service
        Employee emp1 = new Employee(1, "ram", "ram@gmail.com", "developer");
        Employee emp2 = new Employee(2, "raj", "ram@gmail.com", "developer");
        given(employeeServiceImpl.findById((int) anyLong())).willReturn(Optional.ofNullable(null));
        employeeServiceImpl.update(emp2, emp1.getId());
    }

    @Test
    void deleteEmployee(){
        Employee emp = new Employee(1,"ram","ram@gmail.com","developer");
        employeeServiceImpl.delete(1);
        verify(employeeServiceImpl,times(1)).delete(1);

    }





}


