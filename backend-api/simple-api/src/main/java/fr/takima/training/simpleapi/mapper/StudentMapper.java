package fr.takima.training.simpleapi.mapper;

import fr.takima.training.simpleapi.dto.StudentDTO;
import fr.takima.training.simpleapi.entity.Department;
import fr.takima.training.simpleapi.entity.Student;

/**
 * Maps between the persistent Student entity and the StudentDTO exposed by the API.
 * Kept as a simple static utility to keep the change minimal — for larger projects,
 * consider MapStruct or a Spring @Component.
 */
public final class StudentMapper {

    private StudentMapper() {
        // utility class
    }

    public static StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }
        StudentDTO.DepartmentDTO departmentDTO = null;
        if (student.getDepartment() != null) {
            departmentDTO = new StudentDTO.DepartmentDTO(
                    student.getDepartment().getId(),
                    student.getDepartment().getName()
            );
        }
        return new StudentDTO(
                student.getId(),
                student.getFirstname(),
                student.getLastname(),
                departmentDTO
        );
    }

    public static Student toEntity(StudentDTO dto) {
        if (dto == null) {
            return null;
        }
        Department department = null;
        if (dto.department() != null) {
            department = Department.builder()
                    .id(dto.department().id())
                    .name(dto.department().name())
                    .build();
        }
        return Student.builder()
                // id is deliberately NOT copied from the DTO — prevents mass-assignment
                .firstname(dto.firstname())
                .lastname(dto.lastname())
                .department(department)
                .build();
    }
}