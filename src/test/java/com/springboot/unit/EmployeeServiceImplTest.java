package com.springboot.unit;


import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import com.springboot.service.EmployeeService;
import com.springboot.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@WebMvcTest(EmployeeServiceImpl.class)
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;


    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void findById() {
        // Setup our mock repository
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee(1, "ram", "ram@gmail.com", "developer"));
        doReturn(Optional.of(employees)).when(employeeRepository).findById(1);

        // Execute the service call
        Optional<Employee> emp = employeeService.findById(1);

        // Assert the response
        assertTrue(emp.isPresent(), "Employee was not found");
        assertSame(emp.get(), employees, "The employee returned was not the same as the mock");
    }

    @Test
    void findByIdNotFound() throws Exception {
        // Setup our mock repository
        doReturn(Optional.empty()).when(employeeRepository).findById(1);

        // Execute the service call
        Optional<Employee> returnedWidget = employeeRepository.findById(1);

        // Assert the response
        assertFalse(returnedWidget.isPresent(), "Widget should not be found");
    }

    @Test
    void getAll() {
        // Setup our mock repository
        List<Employee> employees = new ArrayList<Employee>();
        Employee employee1 = new Employee(1, "ram", "ram@gmail.com", "developer");
        Employee employee2 = new Employee(2, "raj", "raj@gmail.com", "tester");
        doReturn(Arrays.asList(employee1, employee2)).when(employeeRepository).findAll();

        // Execute the service call
        List<Employee> emp = employeeService.getAll();

        // Assert the response
        assertEquals(2, emp.size(), "findAll should return 2 employeess");
    }

    @Test
    void save() {
        // Setup our mock repository
        Employee empl = new Employee(1, "ram", "ram@gmail.com", "developer");
        doReturn(empl).when(employeeRepository).save(any());

        // Execute the service call
        Employee emply = employeeService.save(empl);

        // Assert the response
        assertNotNull(emply, "The saved employee should not be null");
        assertEquals(1, emply.getId(), "The version should be incremented");
    }
    @Test
    void update(){
        Employee employee = new Employee(1,"ram","ram@gmail.com","developer");
        Employee employeeReturned = employeeRepository.save(employee);
        employee.setEmail("ramkumar@gmail.com");
        assertEquals(employee.getEmail(),"ramkumar@gmail.com");
    }

    @Test
    void updateEmployeeNotFound() throws Exception {
        // Setup our mocked service
        Employee emp1 = new Employee(1, "ram", "ram@gmail.com", "developer");
        Employee emp2 = new Employee(2, "raj", "ram@gmail.com", "developer");
        given(employeeRepository.findById((int) anyLong())).willReturn(Optional.ofNullable(null));
        employeeRepository.findById(1);
    }


    @Test
    void deleteEmployee(){
        Employee emp = new Employee(1,"ram","ram@gmail.com","developer");
        employeeRepository.delete(emp);
        verify(employeeRepository,times(1)).delete(emp);

    }


}