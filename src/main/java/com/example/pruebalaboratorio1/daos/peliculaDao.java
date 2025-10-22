package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.streaming;

import java.sql.*;
import java.util.ArrayList;

// *** CAMBIO: Ahora hereda de baseDao ***
public class peliculaDao extends baseDao {

    // *** CAMBIO: Implementación del método abstracto de validación ***
    @Override
    public boolean validarBorrado(Object entity) {
        if (entity instanceof pelicula) {
            pelicula movie = (pelicula) entity;
            // Convertir duración de "Xmin" a minutos numéricos
            String duracionStr = movie.getDuracion().replace("min", "").trim();
            try {
                int duracion = Integer.parseInt(duracionStr);
                return duracion > 180 && !movie.isPremioOscar();
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public ArrayList<pelicula> listarPeliculas() {
        ArrayList<pelicula> listaPeliculas = new ArrayList<>();

        // *** CAMBIO: Usa getConnection() de baseDao en lugar de crear conexión local
        // ***
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            // *** CAMBIO: Query actualizada para incluir nuevos campos ***
            String sql = "SELECT p.*, g.nombre as genero_nombre, s.nombreServicio as streaming_nombre " +
                    "FROM pelicula p " +
                    "INNER JOIN genero g ON p.idGenero = g.idGenero " +
                    "INNER JOIN streaming s ON p.idStreaming = s.idStreaming " +
                    "ORDER BY p.rating DESC, p.boxOffice DESC";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                pelicula movie = new pelicula();
                movie.setIdPelicula(rs.getInt("idPelicula"));
                movie.setTitulo(rs.getString("titulo"));
                movie.setDirector(rs.getString("director"));
                movie.setAnoPublicacion(rs.getInt("anoPublicacion"));
                movie.setRating(rs.getDouble("rating"));
                movie.setBoxOffice(rs.getDouble("boxOffice"));
                movie.setDuracion(rs.getString("duracion"));
                movie.setPremioOscar(rs.getBoolean("premioOscar"));

                // *** CAMBIO: Se crean objetos para género y streaming ***
                genero gen = new genero();
                gen.setIdGenero(rs.getInt("idGenero"));
                gen.setNombre(rs.getString("genero_nombre"));
                movie.setGeneroObj(gen);

                streaming stream = new streaming();
                stream.setIdStreaming(rs.getInt("idStreaming"));
                stream.setNombreServicio(rs.getString("streaming_nombre"));
                movie.setStreamingObj(stream);

                listaPeliculas.add(movie);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaPeliculas;
    }

    // *** CAMBIO: Método implementado para filtrar por género ***
    public ArrayList<pelicula> listarPeliculasFiltradas(int idGenero) {
        ArrayList<pelicula> listaPeliculasFiltradas = new ArrayList<>();

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT p.*, g.nombre as genero_nombre, s.nombreServicio as streaming_nombre " +
                                "FROM pelicula p " +
                                "INNER JOIN genero g ON p.idGenero = g.idGenero " +
                                "INNER JOIN streaming s ON p.idStreaming = s.idStreaming " +
                                "WHERE p.idGenero = ? " +
                                "ORDER BY p.rating DESC, p.boxOffice DESC")) {

            pstmt.setInt(1, idGenero);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pelicula movie = new pelicula();
                movie.setIdPelicula(rs.getInt("idPelicula"));
                movie.setTitulo(rs.getString("titulo"));
                movie.setDirector(rs.getString("director"));
                movie.setAnoPublicacion(rs.getInt("anoPublicacion"));
                movie.setRating(rs.getDouble("rating"));
                movie.setBoxOffice(rs.getDouble("boxOffice"));
                movie.setDuracion(rs.getString("duracion"));
                movie.setPremioOscar(rs.getBoolean("premioOscar"));

                genero gen = new genero();
                gen.setIdGenero(rs.getInt("idGenero"));
                gen.setNombre(rs.getString("genero_nombre"));
                movie.setGeneroObj(gen);

                streaming stream = new streaming();
                stream.setIdStreaming(rs.getInt("idStreaming"));
                stream.setNombreServicio(rs.getString("streaming_nombre"));
                movie.setStreamingObj(stream);

                listaPeliculasFiltradas.add(movie);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaPeliculasFiltradas;
    }

    // *** CAMBIO: Método implementado para crear película ***
    public void crearPelicula(pelicula movie) {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO pelicula (titulo, director, anoPublicacion, rating, boxOffice, duracion, idGenero, idStreaming, premioOscar) "
                                +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, movie.getTitulo());
            pstmt.setString(2, movie.getDirector());
            pstmt.setInt(3, movie.getAnoPublicacion());
            pstmt.setDouble(4, movie.getRating());
            pstmt.setDouble(5, movie.getBoxOffice());
            pstmt.setString(6, movie.getDuracion());
            pstmt.setInt(7, movie.getGeneroObj().getIdGenero());
            pstmt.setInt(8, movie.getStreamingObj().getIdStreaming());
            pstmt.setBoolean(9, movie.isPremioOscar());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // *** CAMBIO: Método implementado para borrar película con validación ***
    public void borrarPelicula(int idPelicula) {
        // Primero obtener la película para validar
        pelicula movie = obtenerPeliculaPorId(idPelicula);

        if (movie != null && validarBorrado(movie)) {
            try (Connection conn = getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(
                            "DELETE FROM pelicula WHERE idPelicula = ?")) {

                pstmt.setInt(1, idPelicula);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // *** CAMBIO: Método privado para obtener película por ID ***
    private pelicula obtenerPeliculaPorId(int idPelicula) {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT p.*, g.nombre as genero_nombre, s.nombreServicio as streaming_nombre " +
                                "FROM pelicula p " +
                                "INNER JOIN genero g ON p.idGenero = g.idGenero " +
                                "INNER JOIN streaming s ON p.idStreaming = s.idStreaming " +
                                "WHERE p.idPelicula = ?")) {

            pstmt.setInt(1, idPelicula);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                pelicula movie = new pelicula();
                movie.setIdPelicula(rs.getInt("idPelicula"));
                movie.setTitulo(rs.getString("titulo"));
                movie.setDirector(rs.getString("director"));
                movie.setAnoPublicacion(rs.getInt("anoPublicacion"));
                movie.setRating(rs.getDouble("rating"));
                movie.setBoxOffice(rs.getDouble("boxOffice"));
                movie.setDuracion(rs.getString("duracion"));
                movie.setPremioOscar(rs.getBoolean("premioOscar"));

                genero gen = new genero();
                gen.setIdGenero(rs.getInt("idGenero"));
                gen.setNombre(rs.getString("genero_nombre"));
                movie.setGeneroObj(gen);

                streaming stream = new streaming();
                stream.setIdStreaming(rs.getInt("idStreaming"));
                stream.setNombreServicio(rs.getString("streaming_nombre"));
                movie.setStreamingObj(stream);

                return movie;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}