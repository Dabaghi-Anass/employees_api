package com.communication.app.services;

import com.communication.app.entities.Employee;
import com.communication.app.errors.EmployeeNotFoundException;
import com.communication.app.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
@CrossOrigin("*")
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()) throw new EmployeeNotFoundException("Employee not found");
        return employee.get();
    }
    public List<Employee> getEmployeesByName(String name){
        return employeeRepository.findAllByName(name);
    }
    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    private boolean isEmptyField(Object field){
        boolean isNull = Objects.isNull(field);
        boolean isBlank = "".equals(field);
        return isBlank || isNull;
    }
    public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        if(employeeOptional.isEmpty()) throw new EmployeeNotFoundException("Employee not found");
        Employee employee1 = employeeOptional.get();
        if(!isEmptyField(employee.getAge())) employee1.setAge(employee.getAge());
        if(!isEmptyField(employee.getCity())) employee1.setCity(employee.getCity());
        if(!isEmptyField(employee.getName())) employee1.setName(employee.getName());
        if(!isEmptyField(employee.getEmail())) employee1.setEmail(employee.getEmail());
        if(!isEmptyField(employee.getImageUrl())) employee1.setImageUrl(employee.getImageUrl());
        if(!isEmptyField(employee.getPhone())) employee1.setPhone(employee.getPhone());
        if(!isEmptyField(employee.getSalary())) employee1.setSalary(employee.getSalary());
        return employeeRepository.save(employee1);
    }
    public List<Employee> getEmployeesByPage(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset,limit);
        return employeeRepository.findAll(pageable).toList();
    }
    public void addAllEmployees(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }
    public void deleteEmployee(Long id)  {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if(employeeOptional.isEmpty()) return;
        employeeRepository.delete(employeeOptional.get());
    }

}
