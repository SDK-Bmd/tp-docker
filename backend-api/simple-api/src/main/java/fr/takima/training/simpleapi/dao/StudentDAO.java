package fr.takima.training.simpleapi.dao;

import fr.takima.training.simpleapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDAO extends JpaRepository<Student, Long> {
    // Overrides Optional<Student> findById(Long) from JpaRepository — returns Student directly
    Student findById(long id);
    List<Student> findStudentsByDepartment_Name(String name);
    int countAllByDepartment_Name(String name);
}