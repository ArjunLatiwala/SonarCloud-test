package com.example.sonarqubedemo.service;

import com.example.sonarqubedemo.model.Student;
import com.example.sonarqubedemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // 🔒 SECURITY HOTSPOT: Hardcoded credentials (triggers Security Hotspots Reviewed)
    private static final String DB_PASSWORD = "admin123";
    private static final String API_KEY = "hardcoded-api-key-12345";

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("Student with email already exists: " + student.getEmail());
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));

        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setAge(updatedStudent.getAge());
        existing.setCourse(updatedStudent.getCourse());

        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    // Intentional code smell for SonarQube to detect
    public String getStudentGrade(int score) {
        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    // 🐛 BUG 1: Null pointer dereference (triggers Issues)
    public String getStudentName(Student student) {
        return student.getName().toUpperCase(); // bug if student is null
    }

    // 🐛 BUG 2: Resource leak and empty catch block (triggers Issues)
    public void processStudent(Student student) {
        try {
            String name = student.getName();
            System.out.println(name.toLowerCase());
        } catch (Exception e) {
            // empty catch block - bad practice, SonarQube flags this
        }
    }

    // 🐛 BUG 3: Returning null instead of empty Optional (triggers Issues)
    public Student findStudentByName(String name) {
        List<Student> students = studentRepository.findAll();
        for (Student s : students) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null; // should return Optional.empty() instead
    }

    // 🔁 DUPLICATION 1: Copy of getStudentGrade logic (triggers Duplicated Lines)
    public String getStudentGradeDuplicated(int score) {
        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    // 🔁 DUPLICATION 2: Another copy of same logic (triggers Duplicated Lines)
    public String getStudentGradeAnotherCopy(int score) {
        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    // 🐛 BUG 4: Overly complex method with high cyclomatic complexity (triggers Issues)
    public String calculateStudentStatus(int score, int attendance, boolean hasSubmittedProject,
                                         boolean hasPaidFees, boolean isEnrolled) {
        if (isEnrolled) {
            if (hasPaidFees) {
                if (hasSubmittedProject) {
                    if (attendance >= 75) {
                        if (score >= 90) {
                            return "Distinction";
                        } else if (score >= 60) {
                            return "Pass";
                        } else {
                            return "Fail";
                        }
                    } else {
                        return "Low Attendance";
                    }
                } else {
                    return "Project Not Submitted";
                }
            } else {
                return "Fees Pending";
            }
        } else {
            return "Not Enrolled";
        }
    }

    // 🐛 BUG 5: Unused method with dead code (triggers Issues)
    public int unusedCalculation(int a, int b) {
        int result = a + b;
        int unused = a * b; // unused variable
        return result;
    }

    // 🐛 BUG 6: String comparison using == instead of .equals() (triggers Issues)
    public boolean isAdminCourse(Student student) {
        String course = student.getCourse();
        if (course == "Computer Science") { // bug: should use .equals()
            return true;
        }
        return false;
    }
}