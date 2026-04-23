package fr.takima.training.simpleapi.service;

import fr.takima.training.simpleapi.dao.StudentDAO;
import fr.takima.training.simpleapi.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public Student getStudentById(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Student id must not be negative");
        }
        return studentDAO.findById(id);
    }

    public List<Student> getStudentsByDepartmentName(String departmentName) {
        if (departmentName == null || departmentName.isEmpty()) {
            throw new IllegalArgumentException("Department name must not be null or empty");
        }
        return studentDAO.findStudentsByDepartment_Name(departmentName);
    }

    public int getStudentsNumberByDepartmentName(String departmentName) {
        if (departmentName == null || departmentName.isEmpty()) {
            throw new IllegalArgumentException("Department name must not be null or empty");
        }
        return studentDAO.countAllByDepartment_Name(departmentName);
    }

    public Student addStudent(Student student) {
        if (student.getDepartment() == null) {
            throw new IllegalArgumentException("Student must have a department");
        }
        if (student.getLastname() == null || student.getLastname().isEmpty()) {
            throw new IllegalArgumentException("Student lastname must not be null or empty");
        }
        return studentDAO.save(student);
    }

    public Student updateStudent(Student student, long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Student id must not be negative");
        }
        student.setId(id);
        return studentDAO.save(student);
    }

    public void removeStudentById(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Student id must not be negative");
        }
        studentDAO.deleteById(id);
    }
}