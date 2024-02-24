package com.communication.app.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.communication.app.entities.CityEntity;
import com.communication.app.entities.Employee;
import com.communication.app.entities.EmployeePage;
import com.communication.app.errors.EmployeeNotFoundException;
import com.communication.app.repositories.EmployeeRepository;

@Service
public class EmployeeService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisTemplate<String , Employee> redisEmployeeTemplate;
    @Autowired
    EmployeeRepository employeeRepository;
    private static final String REDIS_KEY = "EMPLOYEES";
    public List<Employee> getAllEmployees() {
        String key = REDIS_KEY;
        List<Employee> employeesFromCache = (List<Employee>) redisTemplate.opsForValue().get(key);
        if(employeesFromCache != null)
            return employeesFromCache;

        List<Employee> employees = employeeRepository.findAll();
        redisTemplate.opsForValue().set(key , employees);
        return employees;
    }
    public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
        String key = REDIS_KEY + ":" + id;
        Employee cashedEmployee =(Employee) redisEmployeeTemplate.opsForValue().get(key);
        if(cashedEmployee != null) return cashedEmployee;
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()) throw new EmployeeNotFoundException("Employee not found");
        redisTemplate.opsForValue().set(key, employee.get());
        return employee.get();
    }
    public List<Employee> getEmployeesByCity(String city) {
        String key = REDIS_KEY + ":city:" + city;
        List<Employee> cashedEmployees = (List<Employee>) redisTemplate.opsForValue().get(key);
        if(cashedEmployees != null) return cashedEmployees;
        List<Employee> employees = employeeRepository.findAllByCity(city);
        redisTemplate.opsForValue().set(key, employees);
        return employees;
    }
    public List<Employee> getEmployeesByName(String name){
        String key = REDIS_KEY + ":name:" + name;
        List<Employee> employeesFromCache =(List<Employee>) redisTemplate.opsForValue().get(key);
        if(employeesFromCache != null) {
            System.out.println("cache hit");
            return employeesFromCache;
        }
        List<Employee> employees =  employeeRepository.findAllByNameContainingIgnoreCase(name);
        redisTemplate.opsForValue().set(key , employees);
        return employees;
    }
    public Employee addEmployee(Employee employee){
        Employee savedEmployee = employeeRepository.save(employee);
        String key = REDIS_KEY + ":" + savedEmployee.getId();
        redisEmployeeTemplate.opsForValue().set(key , savedEmployee);
        redisTemplate.delete(REDIS_KEY);
        return savedEmployee;
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
        if(!isEmptyField(employee.getBio())) employee1.setBio(employee.getBio());
        Employee savedEmployee = employeeRepository.save(employee1);
        String key = REDIS_KEY + ":" + savedEmployee.getId();
        redisEmployeeTemplate.opsForValue().set(key , savedEmployee);
        redisTemplate.delete(REDIS_KEY);
        return savedEmployee;
    }
    public EmployeePage getEmployeesByPage(int limit, int offset) {
        String key = String.format("%s:page:%d:%d" , REDIS_KEY,limit,offset);
        List<Employee> employeesFromCache =(List<Employee>) redisTemplate.opsForValue().get(key);
        if(employeesFromCache != null){
            System.out.println("page cache hit");
            return EmployeePage.builder()
                    .employees(employeesFromCache)
                    .hasNext(employeesFromCache.size() == limit)
                    .build();
        }
        Pageable pageable = PageRequest.of(offset,limit);
        List<Employee> employees = employeeRepository.findAll(pageable).toList();
        redisTemplate.opsForValue().set(key,employees);
        EmployeePage employeePage = EmployeePage.builder()
                .employees(employees)
                .hasNext(employees.size() == limit)
                .build();
        return employeePage;
    }
    public void addAllEmployees(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }
    public void deleteEmployee(Long id)  {
        String key = REDIS_KEY + ":"+ id;
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if(employeeOptional.isEmpty()) return;
        redisEmployeeTemplate.delete(key);
        redisTemplate.delete(REDIS_KEY);
        employeeRepository.delete(employeeOptional.get());
    }
    public  List<CityEntity> getAllCities()  {
        String key = REDIS_KEY + ":" + "cities";
        List<CityEntity> citiesFromCache = (List<CityEntity>) redisTemplate.opsForValue().get(key);
        if(citiesFromCache != null) return  citiesFromCache;
        List<String> citiesString =  employeeRepository.findAllCities();
        long i = 0;
        List<CityEntity> citiesList = new ArrayList<>();
        for(String city: citiesString){
            citiesList.add(CityEntity.builder().name(city).id(i).build());
            i++;
        }
        List<CityEntity> cities = citiesList.stream().sorted(Comparator.comparingInt(a -> a.getName().charAt(0))).toList();
        redisTemplate.opsForValue().set(key , cities);
        return cities;
    }

}
