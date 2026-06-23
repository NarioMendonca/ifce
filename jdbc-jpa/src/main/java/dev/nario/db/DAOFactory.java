package dev.nario.db;

public class DAOFactory {
    public static DepartmentDAO createDepartmentDAO() {
        return new DepartmentDAOimpl(ConnectionFactory.getConnection());
    }

    public static SellerDAO createSellerDAO() {
        return new SellerDAOimpl(ConnectionFactory.getConnection());
    }
}