package com.springboot.service;


import com.springboot.exception.EmployeeNotFoundException;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            return Optional.of(employee.get());

        }else{
            throw new EmployeeNotFoundException("Employee","Id",id);
        }

    }

    @Override
    public Employee update(Employee employee, Integer id) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee","Id",id));
         existingEmployee.setName(employee.getName());
         existingEmployee.setEmail(employee.getEmail());
         existingEmployee.setRole(employee.getRole());
         employeeRepository.save(existingEmployee);
         return existingEmployee;

    }

    @Override
    public void delete(Integer id) {
        employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee","Id",id));
        employeeRepository.deleteById(id);
    }
}
