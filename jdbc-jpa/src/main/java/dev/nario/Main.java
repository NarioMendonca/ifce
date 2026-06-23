package dev.nario;

import java.time.LocalDate;

import dev.nario.db.DAOFactory;
import dev.nario.db.DepartmentDAO;
import dev.nario.db.SellerDAO;
import dev.nario.entities.Department;
import dev.nario.entities.Seller;

public class Main {
    public static void main(String[] args) {
        SellerDAO sellerDAO = DAOFactory.createSellerDAO();
        DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();
        
        Department salesDepartment = new Department(null, "Vendas");
        Department rhDepartment = new Department(null, "Rhh");
        salesDepartment = departmentDAO.insert(salesDepartment);
        rhDepartment = departmentDAO.insert(rhDepartment);
        rhDepartment.setName("RH");
        departmentDAO.update(rhDepartment);

        Seller sellerJoao = new Seller(
            null,
            "João Silva",
            "joao@gmail.com", 
            LocalDate.of(2004, 3, 7), 
            3692
        );
        sellerJoao.setDepartment(salesDepartment);
        sellerDAO.insert(sellerJoao);

        Seller sellerPereira = new Seller(
            null,
            "Pereira Alves",
            "pereiraA@gmail.com", 
            LocalDate.of(1997, 7, 2), 
            6180
        );
        sellerPereira.setDepartment(rhDepartment);
        sellerPereira = sellerDAO.insert(sellerPereira);
        sellerPereira.setEmail("pereira2@gmail.com");
        sellerDAO.update(sellerPereira);



        System.out.println("Cadastrados com sucesso!");

    }
}