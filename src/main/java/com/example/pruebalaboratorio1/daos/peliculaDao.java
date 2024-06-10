package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.pelicula;

import java.sql.*;
import java.util.ArrayList;

public class peliculaDao {

    public ArrayList<pelicula> listarPeliculas() {

        ArrayList<pelicula> listaPeliculas = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";
        String username = "root";
        String password = "root";


        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();

            String sql = "SELECT A.*, B.NOMBRE, C.NOMBRESERVICIO FROM  \n" +
                    "(SELECT * FROM PELICULA ) AS A \n" +
                    "INNER JOIN \n" +
                    "(SELECT * FROM GENERO) AS B\n" +
                    "ON A.IDGENERO = B.IDGENERO\n" +
                    "INNER JOIN \n" +
                    "(SELECT * FROM STREAMING) AS C\n" +
                    "ON A.IDSTREAMING = C.IDSTREAMING\n" +
                    "ORDER BY RATING DESC , BOXOFFICE DESC";


            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                pelicula movie = new pelicula();
                int idPelicula = rs.getInt(1);
                movie.setIdPelicula(idPelicula);
                String titulo = rs.getString("titulo");
                movie.setTitulo(titulo);
                String director = rs.getString("director");
                movie.setDirector(director);
                int anoPublicacion = rs.getInt("anoPublicacion");
                movie.setAnoPublicacion(anoPublicacion);
                double rating = rs.getDouble("rating");
                movie.setRating(rating);
                double boxOffice = rs.getDouble("boxOffice");
                movie.setBoxOffice(boxOffice);
                String nombregenero = rs.getString("nombre");
                movie.setGenero(nombregenero);
                String duracion = rs.getString("duracion");
                movie.setDuracion(duracion);
                String streaming = rs.getString("nombreServicio");
                movie.setStreaming(streaming);
                boolean oscar = rs.getBoolean("premioOscar");
                movie.setPremioOscar(oscar);

                listaPeliculas.add(movie);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaPeliculas;
    }

    public ArrayList<pelicula> listarPeliculasFiltradas(int idGenero) {

        ArrayList<pelicula> listaPeliculasFiltradas= new ArrayList<>();

        return listaPeliculasFiltradas;
    }

    public void crearPelicula( ){

    }


    public void borrarPelicula(int idPelicula) {


    }


}
