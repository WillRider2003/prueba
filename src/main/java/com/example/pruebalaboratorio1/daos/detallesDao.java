package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.beans.genero;
import java.sql.*;

// *** CAMBIO: Ahora hereda de baseDao ***
public class detallesDao extends baseDao {

    // *** CAMBIO: Implementación del método abstracto ***
    @Override
    public boolean validarBorrado(Object entity) {
        return false; // No aplica para detalles
    }

    public pelicula obtenerPelicula(int idPelicula) {
        pelicula movie = new pelicula();

        // *** CAMBIO: Usa getConnection() de baseDao ***
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT p.*, g.nombre as genero_nombre " +
                                "FROM pelicula p " +
                                "INNER JOIN genero g ON p.idGenero = g.idGenero " +
                                "WHERE p.IDPELICULA = ?")) {

            pstmt.setInt(1, idPelicula);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                movie.setIdPelicula(rs.getInt("idPelicula"));
                movie.setTitulo(rs.getString("titulo"));
                movie.setDirector(rs.getString("director"));
                movie.setAnoPublicacion(rs.getInt("anoPublicacion"));
                movie.setRating(rs.getDouble("rating"));
                movie.setBoxOffice(rs.getDouble("boxOffice"));
                movie.setDuracion(rs.getString("duracion"));
                movie.setPremioOscar(rs.getBoolean("premioOscar"));

                // *** CAMBIO: Se crea objeto género ***
                genero gen = new genero();
                gen.setIdGenero(rs.getInt("idGenero"));
                gen.setNombre(rs.getString("genero_nombre"));
                movie.setGeneroObj(gen);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movie;
    }
}