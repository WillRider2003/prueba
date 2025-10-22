package com.example.pruebalaboratorio1.servlets;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.beans.streaming;
import com.example.pruebalaboratorio1.daos.listasDao;
import com.example.pruebalaboratorio1.daos.peliculaDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "pelicula-servlet", value = "/listaPeliculas")
public class PeliculaServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String action = request.getParameter("action");
        peliculaDao peliculaDao = new peliculaDao();
        listasDao listaDao = new listasDao();

        // *** CAMBIO: Se obtienen listas de géneros y streamings ***
        ArrayList<genero> listarGeneros = listaDao.listarGeneros();
        ArrayList<streaming> listarStreaming = listaDao.listarStreamings();

        request.setAttribute("listarGeneros", listarGeneros);
        request.setAttribute("listarStreaming", listarStreaming);

        switch (action) {
            case "listar":
                ArrayList<pelicula> listaPeliculas = peliculaDao.listarPeliculas();
                request.setAttribute("listaPeliculas", listaPeliculas);
                RequestDispatcher view = request.getRequestDispatcher("listaPeliculas.jsp");
                view.forward(request, response);
                break;

            // *** CAMBIO: Caso para filtrar por género ***
            case "filtrar":
                int idGenero = Integer.parseInt(request.getParameter("idGenero"));
                ArrayList<pelicula> listaPeliculasFiltradas = peliculaDao.listarPeliculasFiltradas(idGenero);
                request.setAttribute("listaPeliculas", listaPeliculasFiltradas);
                RequestDispatcher viewFiltro = request.getRequestDispatcher("listaPeliculas.jsp");
                viewFiltro.forward(request, response);
                break;

            case "crear":
                RequestDispatcher view2 = request.getRequestDispatcher("crearPelicula.jsp");
                view2.forward(request, response);
                break;

            // *** CAMBIO: Caso para borrar película ***
            case "borrar":
                int idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
                peliculaDao.borrarPelicula(idPelicula);
                response.sendRedirect(request.getContextPath() + "/listaPeliculas?action=listar");
                break;
        }
    }

    // *** CAMBIO: Se agregó método doPost para crear películas ***
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        if ("crear".equals(action)) {
            peliculaDao peliculaDao = new peliculaDao();
            pelicula movie = new pelicula();

            movie.setTitulo(request.getParameter("titulo"));
            movie.setDirector(request.getParameter("director"));
            movie.setAnoPublicacion(Integer.parseInt(request.getParameter("anoPublicacion")));
            movie.setRating(Double.parseDouble(request.getParameter("rating")));
            movie.setBoxOffice(Double.parseDouble(request.getParameter("boxOffice")));
            movie.setDuracion(request.getParameter("duracion"));

            genero gen = new genero();
            gen.setIdGenero(Integer.parseInt(request.getParameter("idGenero")));
            movie.setGeneroObj(gen);

            streaming stream = new streaming();
            stream.setIdStreaming(Integer.parseInt(request.getParameter("idStreaming")));
            movie.setStreamingObj(stream);

            String premioOscar = request.getParameter("premioOscar");
            movie.setPremioOscar("si".equalsIgnoreCase(premioOscar));

            peliculaDao.crearPelicula(movie);
            response.sendRedirect(request.getContextPath() + "/listaPeliculas?action=listar");
        }
    }
}