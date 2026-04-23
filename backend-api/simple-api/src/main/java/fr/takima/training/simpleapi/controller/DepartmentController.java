package fr.takima.training.simpleapi.controller;

import fr.takima.training.simpleapi.entity.Department;
import fr.takima.training.simpleapi.entity.Student;
import fr.takima.training.simpleapi.service.DepartmentService;
import fr.takima.training.simpleapi.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final StudentService studentService;

    public DepartmentController(DepartmentService departmentService, StudentService studentService) {
        this.departmentService = departmentService;
        this.studentService = studentService;
    }

    @GetMapping("/{departmentName}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String departmentName) {
        Department department = departmentService.getDepartmentByName(departmentName);
        if (department == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(department);
    }

    @GetMapping("/{departmentName}/students")
    public ResponseEntity<List<Student>> getStudentsByDepartment(@PathVariable String departmentName) {
        Department department = departmentService.getDepartmentByName(departmentName);
        if (department == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getStudentsByDepartmentName(departmentName));
    }

    @GetMapping("/{departmentName}/count")
    public ResponseEntity<Integer> getStudentsCountByDepartment(@PathVariable String departmentName) {
        Department department = departmentService.getDepartmentByName(departmentName);
        if (department == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getStudentsNumberByDepartmentName(departmentName));
    }
}