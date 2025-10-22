<%@page import="java.util.ArrayList" %>
    <%@page import="com.example.pruebalaboratorio1.beans.genero" %>
        <%@page import="com.example.pruebalaboratorio1.beans.streaming" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <% // *** CAMBIO: Se obtienen listas de géneros y streamings *** ArrayList<genero> listarGeneros =
                    (ArrayList) request.getAttribute("listarGeneros");
                    ArrayList<streaming> listarStreaming = (ArrayList) request.getAttribute("listarStreaming");
                        %>
                        <!DOCTYPE html>
                        <html lang="es">

                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Creacion Pelicula</title>
                        </head>

                        <body>
                            <h2>Formulario de Creacion de Pelicula</h2>
                            <!-- *** CAMBIO: Formulario actualizado para enviar a servlet *** -->
                            <form action="listaPeliculas" method="post">
                                <input type="hidden" name="action" value="crear">

                                <label for="titulo">Titulo:</label><br>
                                <input type="text" id="titulo" name="titulo" required><br>

                                <label for="director">Director:</label><br>
                                <input type="text" id="director" name="director" required><br>

                                <!-- *** CAMBIO: Campo corregido y tipo number *** -->
                                <label for="anoPublicacion">Año Publicacion:</label><br>
                                <input type="number" id="anoPublicacion" name="anoPublicacion" required><br>

                                <!-- *** CAMBIO: Campo con step para decimales *** -->
                                <label for="rating">Rating:</label><br>
                                <input type="number" step="0.1" id="rating" name="rating" required><br>

                                <!-- *** CAMBIO: Campo con step para decimales *** -->
                                <label for="boxOffice">BoxOffice:</label><br>
                                <input type="number" step="0.01" id="boxOffice" name="boxOffice" required><br>

                                <!-- *** CAMBIO: Campo agregado para duración *** -->
                                <label for="duracion">Duración (ej: 120min):</label><br>
                                <input type="text" id="duracion" name="duracion" required><br>

                                <!-- *** CAMBIO: ComboBox para género *** -->
                                <label for="idGenero">Género:</label><br>
                                <select id="idGenero" name="idGenero" required>
                                    <option value="">Seleccione un género</option>
                                    <% for (genero gen : listarGeneros) { %>
                                        <option value="<%=gen.getIdGenero()%>">
                                            <%=gen.getNombre()%>
                                        </option>
                                        <% } %>
                                </select><br>

                                <!-- *** CAMBIO: ComboBox para streaming *** -->
                                <label for="idStreaming">Streaming:</label><br>
                                <select id="idStreaming" name="idStreaming" required>
                                    <option value="">Seleccione un streaming</option>
                                    <% for (streaming stream : listarStreaming) { %>
                                        <option value="<%=stream.getIdStreaming()%>">
                                            <%=stream.getNombreServicio()%>
                                        </option>
                                        <% } %>
                                </select><br>

                                <!-- *** CAMBIO: ComboBox para premio Oscar *** -->
                                <label for="premioOscar">Premio Oscar:</label><br>
                                <select id="premioOscar" name="premioOscar" required>
                                    <option value="no">No</option>
                                    <option value="si">Sí</option>
                                </select><br>

                                <input type="submit" value="Crear Película">
                            </form>
                        </body>

                        </html>