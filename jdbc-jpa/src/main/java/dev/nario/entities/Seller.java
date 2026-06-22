package dev.nario.entities;

import java.time.LocalDate;

public class Seller {
    String id;
    String name;
    String email;
    LocalDate birthDate;
    double baseSalary;
    Department department;

    public Seller(
        String id,
        String name,
        String email,
        LocalDate birthDate,
        double baseSalary,
        Department department
    ) {
        setId(id);
        setName(name);
        setEmail(email);
        setBirthDate(birthDate);
        setBaseSalary(baseSalary);
        setDepartment(department);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public double getBaseSalary() {
        return baseSalary;
    }
    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
}
