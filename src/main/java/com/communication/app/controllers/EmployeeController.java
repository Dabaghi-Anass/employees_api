package com.communication.app.controllers;

import com.communication.app.entities.Employee;
import com.communication.app.errors.EmployeeNotFoundException;
import com.communication.app.services.EmployeeService;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @GetMapping(params = "name")
    public List<Employee> getEmployeesByNameRoute(@RequestParam String name){
        return employeeService.getEmployeesByName(name);
    }
    @GetMapping(params = {"limit","offset"})
    public List<Employee> getEmployeesByPageRoute(
            @RequestParam("limit") int limit,
            @RequestParam("offset") int offset){
        return employeeService.getEmployeesByPage(limit,offset);
    }

    @GetMapping
    public List<Employee> getAllEmployeesRoute(){
        return employeeService.getAllEmployees();
    }
    @PostMapping("/addAll")
    public String pst(@RequestBody List<Employee> employees){
        employeeService.addAllEmployees(employees);
        return "Done";
    }
    @PostMapping
    public Employee addEmployeeRoute(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }
    @SneakyThrows
    @PutMapping("/{id}")
    public Employee updateEmployeeRoute(@RequestBody Employee employee, @PathVariable Long id){
        employee.setId(id);
        return employeeService.updateEmployee(employee);
    }
    @DeleteMapping("/{id}")
    public void deleteEmployeeRoute(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }
}
