package com.communication.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee implements Serializable {
    @Id
    @SequenceGenerator(name = "employee_generator", sequenceName = "employee_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_generator")
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Double salary;
    private Short age;
    private String city;
    private String imageUrl;
    private String bio;
}
