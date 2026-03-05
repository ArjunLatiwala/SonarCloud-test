package com.example.sonarqubedemo.service;

import com.example.sonarqubedemo.model.Student;
import com.example.sonarqubedemo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "John Doe", "john@example.com", 20, "Computer Science");
    }

    @Test
    void getAllStudents_ShouldReturnAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
        List<Student> result = studentService.getAllStudents();
        assertEquals(1, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentById_WhenExists_ShouldReturnStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Optional<Student> result = studentService.getStudentById(1L);
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    void getStudentById_WhenNotExists_ShouldReturnEmpty() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Student> result = studentService.getStudentById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void createStudent_WhenEmailNotExists_ShouldCreateStudent() {
        when(studentRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(studentRepository.save(student)).thenReturn(student);
        Student result = studentService.createStudent(student);
        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void createStudent_WhenEmailExists_ShouldThrowException() {
        when(studentRepository.existsByEmail("john@example.com")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> studentService.createStudent(student));
    }

    @Test
    void deleteStudent_WhenNotExists_ShouldThrowException() {
        when(studentRepository.existsById(99L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudent(99L));
    }

    @Test
    void getStudentGrade_ShouldReturnCorrectGrade() {
        assertEquals("A", studentService.getStudentGrade(95));
        assertEquals("B", studentService.getStudentGrade(85));
        assertEquals("C", studentService.getStudentGrade(75));
        assertEquals("D", studentService.getStudentGrade(65));
        assertEquals("F", studentService.getStudentGrade(50));
    }
}
