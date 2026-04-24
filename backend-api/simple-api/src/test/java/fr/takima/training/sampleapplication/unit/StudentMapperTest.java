package fr.takima.training.sampleapplication.unit;

import fr.takima.training.simpleapi.dto.StudentDTO;
import fr.takima.training.simpleapi.entity.Department;
import fr.takima.training.simpleapi.entity.Student;
import fr.takima.training.simpleapi.mapper.StudentMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    @Test
    void testToDTOWithFullStudent() {
        Department department = Department.builder().id(1L).name("ASI").build();
        Student student = Student.builder()
                .id(42L)
                .firstname("John")
                .lastname("Doe")
                .department(department)
                .build();

        StudentDTO dto = StudentMapper.toDTO(student);

        assertNotNull(dto);
        assertEquals(42L, dto.id());
        assertEquals("John", dto.firstname());
        assertEquals("Doe", dto.lastname());
        assertNotNull(dto.department());
        assertEquals(1L, dto.department().id());
        assertEquals("ASI", dto.department().name());
    }

    @Test
    void testToDTOWithNullStudent() {
        assertNull(StudentMapper.toDTO(null));
    }

    @Test
    void testToDTOWithNullDepartment() {
        Student student = Student.builder()
                .id(1L)
                .firstname("Jane")
                .lastname("Doe")
                .department(null)
                .build();

        StudentDTO dto = StudentMapper.toDTO(student);

        assertNotNull(dto);
        assertNull(dto.department());
        assertEquals("Jane", dto.firstname());
    }

    @Test
    void testToEntityWithFullDTO() {
        StudentDTO.DepartmentDTO depDTO = new StudentDTO.DepartmentDTO(1L, "ASI");
        StudentDTO dto = new StudentDTO(99L, "John", "Doe", depDTO);

        Student student = StudentMapper.toEntity(dto);

        assertNotNull(student);
        // id must NOT be copied from DTO — this is the security guarantee
        assertNull(student.getId());
        assertEquals("John", student.getFirstname());
        assertEquals("Doe", student.getLastname());
        assertNotNull(student.getDepartment());
        assertEquals(1L, student.getDepartment().getId());
        assertEquals("ASI", student.getDepartment().getName());
    }

    @Test
    void testToEntityWithNullDTO() {
        assertNull(StudentMapper.toEntity(null));
    }

    @Test
    void testToEntityWithNullDepartment() {
        StudentDTO dto = new StudentDTO(1L, "Jane", "Doe", null);

        Student student = StudentMapper.toEntity(dto);

        assertNotNull(student);
        assertNull(student.getDepartment());
        assertEquals("Jane", student.getFirstname());
        assertEquals("Doe", student.getLastname());
    }

    @Test
    void testToEntityDoesNotCopyId() {
        // Extra explicit test for the mass-assignment protection
        StudentDTO dto = new StudentDTO(999L, "Evil", "User",
                new StudentDTO.DepartmentDTO(1L, "ASI"));
        Student student = StudentMapper.toEntity(dto);
        assertNull(student.getId(), "Mapper must not copy id from DTO to entity");
    }
}