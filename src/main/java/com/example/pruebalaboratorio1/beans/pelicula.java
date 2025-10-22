package com.example.pruebalaboratorio1.beans;

public class pelicula {
    private int idPelicula;
    private String titulo;
    private String director;
    private int anoPublicacion;
    private Double rating;
    private double boxOffice;
    private String genero;
    private String streaming;
    private String duracion;
    private boolean premioOscar;
    
    // *** CAMBIO: Se agregaron objetos para género y streaming ***
    private genero generoObj;
    private streaming streamingObj;

    // Getters y setters existentes (sin cambios)
    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAnoPublicacion() {
        return anoPublicacion;
    }

    public void setAnoPublicacion(int anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getStreaming() {
        return streaming;
    }

    public void setStreaming(String streaming) {
        this.streaming = streaming;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public boolean isPremioOscar() {
        return premioOscar;
    }

    public void setPremioOscar(boolean premioOscar) {
        this.premioOscar = premioOscar;
    }

    // *** CAMBIO: Nuevos getters y setters para objetos ***
    public genero getGeneroObj() {
        return generoObj;
    }

    public void setGeneroObj(genero generoObj) {
        this.generoObj = generoObj;
        // Actualizar también el String para compatibilidad
        if (generoObj != null) {
            this.genero = generoObj.getNombre();
        }
    }

    public streaming getStreamingObj() {
        return streamingObj;
    }

    public void setStreamingObj(streaming streamingObj) {
        this.streamingObj = streamingObj;
        // Actualizar también el String para compatibilidad
        if (streamingObj != null) {
            this.streaming = streamingObj.getNombreServicio();
        }
    }
}