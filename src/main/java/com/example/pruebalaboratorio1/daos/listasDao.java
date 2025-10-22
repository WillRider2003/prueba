package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.streaming;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// *** CAMBIO: Ahora hereda de baseDao ***
public class listasDao extends baseDao {

    // *** CAMBIO: Implementación del método abstracto ***
    @Override
    public boolean validarBorrado(Object entity) {
        return false; // No aplica para esta clase
    }

    // *** CAMBIO: Método implementado para listar géneros ***
    public ArrayList<genero> listarGeneros() {
        ArrayList<genero> listaGeneros = new ArrayList<>();

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM genero ORDER BY nombre";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                genero gen = new genero();
                gen.setIdGenero(rs.getInt("idGenero"));
                gen.setNombre(rs.getString("nombre"));
                listaGeneros.add(gen);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaGeneros;
    }

    // *** CAMBIO: Método implementado para listar servicios de streaming ***
    public ArrayList<streaming> listarStreamings() {
        ArrayList<streaming> listaStreamings = new ArrayList<>();

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM streaming ORDER BY nombreServicio";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                streaming stream = new streaming();
                stream.setIdStreaming(rs.getInt("idStreaming"));
                stream.setNombreServicio(rs.getString("nombreServicio"));
                listaStreamings.add(stream);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaStreamings;
    }
}