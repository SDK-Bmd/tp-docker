package fr.takima.training.simpleapi.controller;

import fr.takima.training.simpleapi.dto.StudentDTO;
import fr.takima.training.simpleapi.entity.Student;
import fr.takima.training.simpleapi.mapper.StudentMapper;
import fr.takima.training.simpleapi.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(StudentMapper.toDTO(student));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        try {
            Student created = studentService.addStudent(StudentMapper.toEntity(studentDTO));
            URI location = URI.create("/students/" + created.getId());
            return ResponseEntity.created(location).body(StudentMapper.toDTO(created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable long id, @RequestBody StudentDTO studentDTO) {
        try {
            Student updated = studentService.updateStudent(StudentMapper.toEntity(studentDTO), id);
            return ResponseEntity.ok(StudentMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.removeStudentById(id);
        return ResponseEntity.ok().build();
    }
}