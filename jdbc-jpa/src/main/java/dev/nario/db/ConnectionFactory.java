package dev.nario.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    private static String name = "docker";
    private static String password = "docker";
    private static String url = "jdbc:postgresql://localhost5432/postgres";


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, name, password);
        } catch(Exception e) {
            throw new RuntimeException("Erro ao se conectar com o banco de dados", e);
        }
    }
}
