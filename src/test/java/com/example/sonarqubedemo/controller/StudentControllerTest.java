package com.example.sonarqubedemo.controller;

import com.example.sonarqubedemo.model.Student;
import com.example.sonarqubedemo.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllStudents_ShouldReturn200() throws Exception {
        Student s = new Student(1L, "Jane", "jane@example.com", 21, "Math");
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(s));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jane"));
    }

    @Test
    void getStudentById_WhenExists_ShouldReturn200() throws Exception {
        Student s = new Student(1L, "Jane", "jane@example.com", 21, "Math");
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(s));

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jane@example.com"));
    }

    @Test
    void getStudentById_WhenNotExists_ShouldReturn404() throws Exception {
        when(studentService.getStudentById(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/students/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createStudent_ShouldReturn201() throws Exception {
        Student s = new Student(null, "Jane", "jane@example.com", 21, "Math");
        Student saved = new Student(1L, "Jane", "jane@example.com", 21, "Math");
        when(studentService.createStudent(any())).thenReturn(saved);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteStudent_WhenExists_ShouldReturn204() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);
        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());
    }
}
