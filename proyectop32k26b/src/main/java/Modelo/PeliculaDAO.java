package Modelo;

import Controlador.clsPelicula;
import Controlador.clsUsuarioConectado;
import Modelo.BitacoraDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    private static final int APL_CODIGO = 10006; // Código de la app Películas en tabla aplicaciones 

    /**
     * Lista todas las películas de la base de datos
     */
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
     * Inserta una nueva película y registra en bitácora
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

            // Registro en bitácora
            BitacoraDAO bitacoraDAO = new BitacoraDAO();
            bitacoraDAO.insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "INSERT");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar película", e);
        }
    }

    /**
     * Actualiza una película existente y registra en bitácora
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

            // Registro en bitácora
            BitacoraDAO bitacoraDAO = new BitacoraDAO();
            bitacoraDAO.insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "UPDATE");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar película", e);
        }
    }

    /**
     * Elimina una película y registra en bitácora
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

            // Registro en bitácora
            BitacoraDAO bitacoraDAO = new BitacoraDAO();
            bitacoraDAO.insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "DELETE");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar película", e);
        }
    }

    /**
     * Consulta una película por su ID
     */
    public clsPelicula query(int idPelicula) {
        clsPelicula pelicula = null;
        String sql = "SELECT * FROM peliculas WHERE idPelicula=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPelicula);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pelicula = new clsPelicula();
                    pelicula.setIdPelicula(rs.getInt("idPelicula"));
                    pelicula.setNombre(rs.getString("nombre"));
                    pelicula.setClasificacion(rs.getString("clasificacion"));
                    pelicula.setGenero(rs.getString("genero"));
                    pelicula.setSubtitulado(rs.getString("subtitulado"));
                    pelicula.setIdioma(rs.getString("idioma"));
                    pelicula.setPrecio(rs.getDouble("precio"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al consultar película", e);
        }

        return pelicula;
    }
}