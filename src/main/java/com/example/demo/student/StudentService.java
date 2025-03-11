package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email is already in use.");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException(String.format("Student with Id: %,d does not exist", studentId));
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isEmpty()) {
            throw new IllegalStateException(String.format("Student with Id: %,d does not exist", studentId));
        }

        Student currStudent = student.get();

        if (name != null
                && name.length() > 0
                && !Objects.equals(currStudent.getName(), name)) {
            currStudent.setName(name);
        }

        if (email != null
                && email.length() > 0
                && !Objects.equals(currStudent.getEmail(), email)) {
            // See if student with that email already exists
            Optional<Student> optionalStudent = studentRepository.
                    findStudentByEmail(email);

            if (optionalStudent.isPresent()) {
                throw new IllegalStateException(String.format("Student with Email: %s already exists", email));
            }

            currStudent.setEmail(email);
        }
    }
}
