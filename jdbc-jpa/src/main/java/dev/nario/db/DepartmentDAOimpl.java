package dev.nario.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import dev.nario.entities.Department;

public class DepartmentDAOimpl implements DepartmentDAO {
    private Connection conn;
    
    public DepartmentDAOimpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Department findById(Integer id) {
        String sql = "SELECT * FROM department WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return null;
            }

            Department department = new Department(
                rs.getInt("id"),
                rs.getString("name")
            );
            return department;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar departamentos", e);
        }
    }

    @Override
    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM department";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Department(
                    rs.getInt("id"),
                    rs.getString("name")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar departments", e);
        }
    }

    @Override
    public Department insert(Department department) {
        String sql = "INSERT INTO department (name) VALUES (?) RETURNING id";
        try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, department.getName());
            pstm.execute();
            ResultSet rs = pstm.getResultSet();
            int id = rs.getInt("id");
            department.setId(id);
            return department;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao criar departamento", exception);
        }
    }

    @Override
    public Department update(Department department) {
        String sql = "UPDATE department SET name = ? WHERE id = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, department.getName());
            pstm.setInt(2, department.getId());
            pstm.execute();
            return department;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar departamento", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM department WHERE id = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, id);
            pstm.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar departamento", e);
        }
    }


}
