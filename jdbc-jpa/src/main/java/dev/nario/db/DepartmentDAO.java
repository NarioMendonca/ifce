package dev.nario.db;

import java.util.List;

import dev.nario.entities.Department;

public interface DepartmentDAO {
    public Department findById(Integer id);
    public List<Department> findAll();
    public Department insert(Department department);
    public Department update(Department department);
    public void delete(Integer id);
}
