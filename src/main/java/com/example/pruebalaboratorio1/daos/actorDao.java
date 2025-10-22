package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.actor;
import java.sql.*;
import java.util.ArrayList;

// *** CAMBIO: Ahora hereda de baseDao ***
public class actorDao extends baseDao {

    // *** CAMBIO: Implementación del método abstracto ***
    @Override
    public boolean validarBorrado(Object entity) {
        return false; // No aplica para actores
    }

    public ArrayList<actor> listarActores(int idPelicula) {
        ArrayList<actor> listaActores = new ArrayList<>();

        // *** CAMBIO: Usa getConnection() de baseDao ***
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT A.* " +
                                "FROM ACTOR A " +
                                "INNER JOIN PROTAGONISTAS B ON A.idactor = B.idactor " +
                                "WHERE B.IDPELICULA = ?")) {

            pstmt.setInt(1, idPelicula);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                actor actuador = new actor();
                actuador.setIdActor(rs.getInt("idActor"));
                actuador.setNombre(rs.getString("nombre"));
                actuador.setApellido(rs.getString("apellido"));
                actuador.setAnoNacimiento(rs.getInt("anoNacimiento"));
                actuador.setPremioOscar(rs.getBoolean("premioOscar"));
                listaActores.add(actuador);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaActores;
    }
}