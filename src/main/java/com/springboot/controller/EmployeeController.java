package com.springboot.controller;

import com.springboot.model.Employee;
import com.springboot.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<Employee> save(@RequestBody Employee employee){
        logger.info("Creating a employee");
        return new ResponseEntity<Employee>(employeeService.save(employee),HttpStatus.CREATED);
    }
    @GetMapping
    public List<Employee>getAll(){
        logger.info("getting All employees");
        return employeeService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Employee>> getById(@PathVariable("id") Integer id){
        logger.info("getting the employee, Id:"+id);
        return ResponseEntity.ok().body(employeeService.findById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Employee>update(@PathVariable("id") Integer id, @RequestBody Employee employee){
        logger.info("updating the employee, Id:"+id);
        return new ResponseEntity<Employee>(employeeService.update(employee,id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>delete(@PathVariable("id") Integer id){
        employeeService.delete(id);
        logger.info("deleting the employee, Id:"+id);
        return new ResponseEntity<String>("Employee deleted Successfully",HttpStatus.OK);
    }
}
