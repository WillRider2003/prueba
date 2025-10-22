<%@page import="java.util.ArrayList" %>
    <%@page import="com.example.pruebalaboratorio1.beans.pelicula" %>
        <%@page import="com.example.pruebalaboratorio1.beans.genero" %>
            <%@page import="com.example.pruebalaboratorio1.beans.streaming" %>
                <%@page import="java.text.NumberFormat" %>
                    <%@page contentType="text/html" pageEncoding="UTF-8" %>
                        <% ArrayList<pelicula> lista = (ArrayList) request.getAttribute("listaPeliculas");
                            ArrayList<genero> listaGeneros = (ArrayList) request.getAttribute("listarGeneros");
                                String searchTerm = request.getParameter("searchTerm");
                                NumberFormat formatter = NumberFormat.getInstance();
                                %>
                                <!DOCTYPE html>
                                <html>

                                <head>
                                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                                    <title>Películas</title>
                                </head>

                                <body>

                                    <h1>Lista de Películas</h1>

                                    <!-- *** CAMBIO: ComboBox para filtrar por género *** -->
                                    <form action="listaPeliculas" method="get">
                                        <input type="hidden" name="action" value="filtrar">
                                        <label for="idGenero">Filtrar por género:</label>
                                        <select name="idGenero" id="idGenero" onchange="this.form.submit()">
                                            <option value="">Todos los géneros</option>
                                            <% for (genero gen : listaGeneros) { %>
                                                <option value="<%=gen.getIdGenero()%>">
                                                    <%=gen.getNombre()%>
                                                </option>
                                                <% } %>
                                        </select>
                                    </form>

                                    <table border="1">
                                        <tr>
                                            <th>Titulo</th>
                                            <th>Director</th>
                                            <th>Ano Publicacion</th>
                                            <th>Rating</th>
                                            <th>BoxOffice</th>
                                            <th>Genero</th>
                                            <!-- *** CAMBIO: Nuevas columnas agregadas *** -->
                                            <th>Duración</th>
                                            <th>Streaming</th>
                                            <th>Premios Oscar</th>
                                            <th>Actores</th>
                                            <!-- *** CAMBIO: Columna para botón borrar *** -->
                                            <th>Borrar</th>
                                        </tr>
                                        <% for (pelicula movie : lista) { // *** CAMBIO: Validación para determinar si
                                            se puede borrar *** String duracionStr=movie.getDuracion().replace("min", ""
                                            ).trim(); boolean puedeBorrar=false; try { int
                                            duracion=Integer.parseInt(duracionStr); puedeBorrar=duracion> 180 &&
                                            !movie.isPremioOscar();
                                            } catch (NumberFormatException e) {
                                            puedeBorrar = false;
                                            }
                                            %>
                                            <tr>
                                                <td><a href="viewPelicula?idPelicula=<%= movie.getIdPelicula() %>">
                                                        <%=movie.getTitulo()%>
                                                    </a></td>
                                                <td>
                                                    <%=movie.getDirector()%>
                                                </td>
                                                <td>
                                                    <%=movie.getAnoPublicacion()%>
                                                </td>
                                                <td>
                                                    <%=movie.getRating()%>/10
                                                </td>
                                                <td>$<%=formatter.format(movie.getBoxOffice())%>
                                                </td>
                                                <td>
                                                    <%=movie.getGenero()%>
                                                </td>
                                                <!-- *** CAMBIO: Mostrar nuevos campos *** -->
                                                <td>
                                                    <%=movie.getDuracion()%>
                                                </td>
                                                <td>
                                                    <%=movie.getStreaming()%>
                                                </td>
                                                <td>
                                                    <%=movie.isPremioOscar() ? "Sí" : "No" %>
                                                </td>
                                                <td><a href="listaActores?idPelicula=<%= movie.getIdPelicula() %>">Ver
                                                        Actores</a></td>
                                                <!-- *** CAMBIO: Botón borrar condicional *** -->
                                                <td>
                                                    <% if (puedeBorrar) { %>
                                                        <a href="listaPeliculas?action=borrar&idPelicula=<%= movie.getIdPelicula() %>"
                                                            onclick="return confirm('¿Está seguro de borrar esta película?')">Borrar</a>
                                                        <% } else { %>
                                                            No disponible
                                                            <% } %>
                                                </td>
                                            </tr>
                                            <% } %>
                                    </table>

                                    <a href="listaPeliculas?action=crear"
                                        style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border: none; border-radius: 5px;">Crear
                                        Pelicula</a>

                                </body>

                                </html>