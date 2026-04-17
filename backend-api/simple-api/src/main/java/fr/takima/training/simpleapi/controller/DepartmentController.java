package fr.takima.training.simpleapi.controller;

import fr.takima.training.simpleapi.entity.Department;
import fr.takima.training.simpleapi.entity.Student;
import fr.takima.training.simpleapi.repository.DepartmentRepository;
import fr.takima.training.simpleapi.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;

    public DepartmentController(DepartmentRepository departmentRepository, StudentRepository studentRepository) {
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @GetMapping("/{departmentName}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String departmentName) {
        return departmentRepository.findByName(departmentName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{departmentName}/students")
    public List<Student> getStudentsByDepartment(@PathVariable String departmentName) {
        return studentRepository.findByDepartmentName(departmentName);
    }
}