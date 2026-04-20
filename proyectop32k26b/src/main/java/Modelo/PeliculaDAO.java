package Modelo;

import Controlador.clsPelicula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    /**
     * Lista todas las películas de la base de datos
     * @return Lista de objetos clsPelicula
     */
    //Errores que se daban en DAO
    public List<clsPelicula> listar() {
    List<clsPelicula> lista = new ArrayList<>();
    String sql = "SELECT * FROM peliculas";

    try (Connection conn = Conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            clsPelicula pelicula = new clsPelicula();
            pelicula.setIdPelicula(rs.getInt("idPelicula"));
            pelicula.setNombre(rs.getString("nombre"));
            pelicula.setClasificacion(rs.getString("clasificacion"));
            pelicula.setGenero(rs.getString("genero"));
            pelicula.setSubtitulado(rs.getString("subtitulado"));
            pelicula.setIdioma(rs.getString("idioma"));
            pelicula.setPrecio(rs.getDouble("precio"));
            lista.add(pelicula);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}

    /**
     * Inserta una nueva película en la base de datos.
     * 
     * @param pelicula Objeto clsPelicula con los datos a insertar
     */
    public void insert(clsPelicula pelicula) {

        String sql = "INSERT INTO peliculas (nombre, clasificacion, genero, subtitulado, idioma, precio) " +
             "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pelicula.getNombre());
            ps.setString(2, pelicula.getClasificacion());
            ps.setString(3, pelicula.getGenero());
            ps.setString(4, pelicula.getSubtitulado());
            ps.setString(5, pelicula.getIdioma());
            ps.setDouble(6, pelicula.getPrecio());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar película", e);
        }
    }

    /**
     * Actualiza los datos de una película existente.
     * 
     * @param pelicula Objeto clsPelicula con los datos actualizados
     */
    public void update(clsPelicula pelicula) {

        String sql = "UPDATE peliculas SET nombre=?, clasificacion=?, genero=?, " +
             "subtitulado=?, idioma=?, precio=? WHERE idPelicula=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pelicula.getNombre());
            ps.setString(2, pelicula.getClasificacion());
            ps.setString(3, pelicula.getGenero());
            ps.setString(4, pelicula.getSubtitulado());
            ps.setString(5, pelicula.getIdioma());
            ps.setDouble(6, pelicula.getPrecio());
            ps.setInt(7, pelicula.getIdPelicula());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("No se encontró la película para actualizar");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar película", e);
        }
    }

    /**
     * Elimina una película de la base de datos según su ID.
     * 
     * @param idPelicula ID de la película a eliminar
     */
    public void delete(int idPelicula) {

        String sql = "DELETE FROM peliculas WHERE idPelicula=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPelicula);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("No se encontró la película para eliminar");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar película", e);
        }
    }

    /**
     * Consulta una película específica por su ID.
     * 
     * @param idPelicula ID de la película
     * @return Objeto clsPelicula o null si no existe
     */
    public clsPelicula query(int idPelicula) {

        clsPelicula pelicula = null;
        String sql = "SELECT * FROM peliculas WHERE idPelicula=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPelicula);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pelicula.setIdPelicula(rs.getInt("idPelicula"));
pelicula.setNombre(rs.getString("nombre"));
pelicula.setClasificacion(rs.getString("clasificacion"));
pelicula.setGenero(rs.getString("genero"));
pelicula.setSubtitulado(rs.getString("subtitulado"));
pelicula.setIdioma(rs.getString("idioma"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al consultar película", e);
        }

        return pelicula;
    }
}