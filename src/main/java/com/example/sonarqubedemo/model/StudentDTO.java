package com.example.sonarqubedemo.model;

import lombok.Data;

@Data
public class StudentDTO {
    private String name;
    private String email;
    private int age;
    private String course;
}