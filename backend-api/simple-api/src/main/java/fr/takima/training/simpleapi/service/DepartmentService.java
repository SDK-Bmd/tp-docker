package fr.takima.training.simpleapi.service;

import fr.takima.training.simpleapi.dao.DepartmentDAO;
import fr.takima.training.simpleapi.entity.Department;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentDAO departmentDAO;

    public DepartmentService(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    public Department getDepartmentByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Department name must not be null or empty");
        }
        return departmentDAO.findDepartmentByName(name);
    }
}