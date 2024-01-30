package com.communication.app.repositories;

import com.communication.app.entities.CityEntity;
import com.communication.app.entities.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public List<Employee> findAllByNameContainingIgnoreCase(String name);
    @Query("SELECT DISTINCT e.city FROM Employee e")
    public List<String> findAllCities();
}
