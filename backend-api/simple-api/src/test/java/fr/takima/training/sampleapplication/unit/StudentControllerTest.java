package fr.takima.training.sampleapplication.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.takima.training.simpleapi.SimpleApiApplication;
import fr.takima.training.simpleapi.controller.StudentController;
import fr.takima.training.simpleapi.dto.StudentDTO;
import fr.takima.training.simpleapi.entity.Department;
import fr.takima.training.simpleapi.entity.Student;
import fr.takima.training.simpleapi.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Lightweight controller-only test. Uses @WebMvcTest so the application
 * context only loads the web layer — no database, no real services.
 * This lets us force every branch of the controller (including error paths)
 * without needing Testcontainers.
 */
@WebMvcTest(StudentController.class)
@ContextConfiguration(classes = SimpleApiApplication.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student sampleStudent() {
        Department department = Department.builder().id(1L).name("ASI").build();
        return Student.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .department(department)
                .build();
    }

    @Test
    void testGetStudentByIdReturnsOk() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(sampleStudent());

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", equalTo(1)))
                .andExpect(jsonPath("firstname", equalTo("John")))
                .andExpect(jsonPath("lastname", equalTo("Doe")));
    }

    @Test
    void testGetStudentByIdReturnsNotFoundWhenNull() throws Exception {
        when(studentService.getStudentById(999L)).thenReturn(null);

        mockMvc.perform(get("/students/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateStudentReturnsCreated() throws Exception {
        when(studentService.addStudent(any(Student.class))).thenReturn(sampleStudent());

        StudentDTO body = new StudentDTO(
                null, "John", "Doe",
                new StudentDTO.DepartmentDTO(1L, "ASI")
        );

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
    }

    @Test
    void testCreateStudentReturnsBadRequestOnIllegalArgument() throws Exception {
        when(studentService.addStudent(any(Student.class)))
                .thenThrow(new IllegalArgumentException("invalid"));

        StudentDTO body = new StudentDTO(null, "John", "Doe", null);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateStudentReturnsOk() throws Exception {
        when(studentService.updateStudent(any(Student.class), anyLong()))
                .thenReturn(sampleStudent());

        StudentDTO body = new StudentDTO(
                null, "John", "Doe",
                new StudentDTO.DepartmentDTO(1L, "ASI")
        );

        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname", equalTo("John")));
    }

    @Test
    void testUpdateStudentReturnsBadRequestOnIllegalArgument() throws Exception {
        // This is the branch most likely to be uncovered — force the catch block
        when(studentService.updateStudent(any(Student.class), anyLong()))
                .thenThrow(new IllegalArgumentException("invalid"));

        StudentDTO body = new StudentDTO(
                null, "John", "Doe",
                new StudentDTO.DepartmentDTO(1L, "ASI")
        );

        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteStudentReturnsOk() throws Exception {
        doNothing().when(studentService).removeStudentById(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());

        verify(studentService).removeStudentById(1L);
    }
}