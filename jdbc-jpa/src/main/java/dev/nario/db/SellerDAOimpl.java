package dev.nario.db;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import dev.nario.entities.Seller;
import dev.nario.entities.Department;

public class SellerDAOimpl implements SellerDAO {
    private Connection conn;

    public SellerDAOimpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Seller> findAll() {
        List<Seller> sellers = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT seller.*, department.name AS department_name FROM seller INNER JOIN department ON seller.department_id = department.id")) {

            while (rs.next()) {
                Seller seller = new Seller(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getDate("birth_date").toLocalDate(),
                    rs.getDouble("base_salary")
                );

                Department department = new Department(
                    rs.getInt("department_id"),
                    rs.getString("department_name")
                );

                seller.setDepartment(department);

                sellers.add(seller);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendedores", e);
        }

        return sellers;
    }

    @Override
    public Seller findById(Integer id) {
        String sql = """
            SELECT seller.*, department.name AS department_name, department.id AS department_id FROM seller 
            INNER JOIN department ON seller.id = department.id 
            WHERE seller.id = ?
        """;;
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return null;
            }

            Seller seller = new Seller(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getDate("birth_date").toLocalDate(),
                rs.getDouble("base_salary")
            );

            seller.setDepartment(
                new Department(
                    rs.getInt("department_id"), 
                    rs.getString("department_name")
                )
            );
            return seller;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendedor", e);
        }
    }

    @Override
    public Seller insert(Seller seller) {
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO seller (name, email, birth_date, base_salary, department_id) VALUES (?, ?, ?, ?, ?) RETURNING id")) {
            pstmt.setString(1, seller.getName());
            pstmt.setString(2, seller.getEmail());
            pstmt.setDate(3, Date.valueOf(seller.getBirthDate())); // Classe Date do java.sql para converter LocalDate
            pstmt.setDouble(4, seller.getBaseSalary());
            pstmt.setInt(5, seller.getDepartment().getId());

            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            rs.next();
            seller.setId(rs.getInt("id"));
            return seller;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar vendedor", e);
        }

    }

    @Override
    public void update(Seller seller) {
        String sql = """
            UPDATE seller
            SET name = ?, email = ?, birth_date = ?, base_salary = ?, department_id = ?
            WHERE id = ?
            """;

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, seller.getName());
            pstm.setString(2, seller.getEmail());
            pstm.setDate(3, Date.valueOf(seller.getBirthDate()));
            pstm.setDouble(4, seller.getBaseSalary());
            pstm.setInt(5, seller.getDepartment().getId());
            pstm.setInt(6, seller.getId());

            pstm.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar vendedor", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM seller WHERE id = ?";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, id);
            pstm.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir vendedor", e);
        }
    }
}