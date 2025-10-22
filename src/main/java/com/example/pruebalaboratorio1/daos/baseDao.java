package com.example.pruebalaboratorio1.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class baseDao {
    
    // *** CAMBIO: Se implementó método de conexión centralizado ***
    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";
        String username = "root";
        String password = "root";
        
        return DriverManager.getConnection(url, username, password);
    }
    
    // *** CAMBIO: Se agregó método abstracto para validar borrado ***
    public abstract boolean validarBorrado(Object entity);
}