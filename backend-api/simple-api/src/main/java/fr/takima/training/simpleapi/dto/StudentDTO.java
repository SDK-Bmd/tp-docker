package fr.takima.training.simpleapi.dto;


public record StudentDTO(
        Long id,
        String firstname,
        String lastname,
        DepartmentDTO department
) {
    public record DepartmentDTO(Long id, String name) {}
}