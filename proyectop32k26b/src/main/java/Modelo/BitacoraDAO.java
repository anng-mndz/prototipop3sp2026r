//Documentación: Astrid Fernanda Ruíz López 9959 24 2976
//Astrid modificó los métodos y el método insert
package Modelo;

import Controlador.clsBitacora;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp; //manejo de fechas usado por la bd sql
import java.text.SimpleDateFormat;
import java.time.LocalDateTime; //para la fechas y horas en java
import java.util.ArrayList;
import java.util.List;

public class BitacoraDAO {

    private static final String SQL_SELECT = "SELECT Bitcodigo, Usuid, Aplcodigo, Bitfecha, Bitip, Bitequipo, Bitaccion FROM Bitacora";
    private static final String SQL_INSERT = "INSERT INTO Bitacora(Usuid, Aplcodigo, Bitfecha, Bitip, Bitequipo, Bitaccion) VALUES(?, ?, NOW(), ?, ?, ?)";
    //Se agregaron varios querys para poder buscar por diferentes tipos en la bitacora (ya sea codigo, usuario, fecha,etc) 
    private static final String SQL_QUERY_POR_CODIGO = "SELECT Bitcodigo, Usuid, Aplcodigo, Bitfecha, Bitip, Bitequipo, Bitaccion FROM Bitacora WHERE Bitcodigo=?";
    private static final String SQL_QUERY_POR_USUARIO = "SELECT Bitcodigo, Usuid, Aplcodigo, Bitfecha, Bitip, Bitequipo, Bitaccion FROM Bitacora WHERE Usuid=?";
    private static final String SQL_QUERY_POR_APLICACION = "SELECT Bitcodigo, Usuid, Aplcodigo, Bitfecha, Bitip, Bitequipo, Bitaccion FROM Bitacora WHERE Aplcodigo=?";
    private static final String SQL_QUERY_POR_FECHAS = "SELECT Bitcodigo, Usuid, Aplcodigo, Bitfecha, Bitip, Bitequipo, Bitaccion FROM Bitacora WHERE Bitfecha BETWEEN ? AND ?"; //between y and para buscar desde el inicio d fecha que el usuario seleccione hasta el final (el intervalo d fechas)
    private static final String SQL_QUERY_POR_ACCION = "SELECT Bitcodigo, Usuid, Aplcodigo, Bitfecha, Bitip, Bitequipo, Bitaccion FROM Bitacora WHERE Bitaccion=?";


    // SELECT (trae todos los registros) 
        public String fechaActual() {

        java.util.Date fecha = new java.util.Date();
        //SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");

        return formatoFecha.format(fecha);

    }

    public static String horaActual() {

        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("hh:mm:ss");

        return formatoFecha.format(fecha);

    }
    
    private String obtenerNombrePc() throws UnknownHostException {
        // return System.getProperty("user.name");        
        return InetAddress.getLocalHost().getHostName();
    }
            
    private String obtenerIP() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }        
    public List<clsBitacora> select() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clsBitacora bitacora = null;
        List<clsBitacora> bitacoras = new ArrayList<clsBitacora>(); //lista para almacenar todos los registros

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery(); //se ejecuta la consulta
            while (rs.next()) {
                //valores de cada columna del ResultSet
                int Bitcodigo    = rs.getInt("Bitcodigo");
                int Usuid    = rs.getInt("Usuid");
                int Aplcodigo    = rs.getInt("Aplcodigo");
                String Bitfecha  = rs.getString("Bitfecha");
                String Bitip     = rs.getString("Bitip");
                String Bitequipo = rs.getString("Bitequipo");
                String Bitaccion = rs.getString("Bitaccion");

                bitacora = new clsBitacora();
                bitacora.setBitcodigo(Bitcodigo);
                bitacora.setUsucodigo(Usuid);
                bitacora.setAplcodigo(Aplcodigo);
                bitacora.setBitfecha(Bitfecha);
                bitacora.setBitip(Bitip);
                bitacora.setBitequipo(Bitequipo);
                bitacora.setBitaccion(Bitaccion);

                bitacoras.add(bitacora);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return bitacoras;
    }

    // Isert
    public int insert(int Usuid, int Aplcodigo, String Bitaccion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String ipAsignada;
        String nombrepcAsignada;
        ipAsignada = " ";
        nombrepcAsignada = " ";        
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            try {
                   ipAsignada= obtenerIP();
                   nombrepcAsignada= obtenerNombrePc();            
            } catch (UnknownHostException ex)
                {
                }                           
            //asignación d valores a los parametros
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, Usuid);
            stmt.setInt(2, Aplcodigo);
            stmt.setString(3, ipAsignada);
            stmt.setString(4, nombrepcAsignada);
            stmt.setString(5,  Bitaccion);

            System.out.println("Ejecutando query: " + SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados: " + rows);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    // Query por código 
    public clsBitacora queryPorCodigo(clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_QUERY_POR_CODIGO);
            stmt = conn.prepareStatement(SQL_QUERY_POR_CODIGO);
            // se pasa el código que se desea buscar
            stmt.setInt(1, bitacora.getBitcodigo());
            rs = stmt.executeQuery();
            while (rs.next()) {
                int Bitcodigo    = rs.getInt("Bitcodigo");
                int Usuid    = rs.getInt("Usuid");
                int Aplcodigo    = rs.getInt("Aplcodigo");
                String Bitfecha = rs.getString("Bitfecha");
                String Bitip     = rs.getString("Bitip");
                String Bitequipo = rs.getString("Bitequipo");
                String Bitaccion = rs.getString("Bitaccion");

                bitacora = new clsBitacora();
                bitacora.setBitcodigo(Bitcodigo);
                bitacora.setUsucodigo(Usuid);
                bitacora.setAplcodigo(Aplcodigo);
                bitacora.setBitfecha(Bitfecha); 
                bitacora.setBitip(Bitip);
                bitacora.setBitequipo(Bitequipo);
                bitacora.setBitaccion(Bitaccion);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return bitacora;
    }

    //Se hace el mismo proceso para los demás querys: ejecutar la consulta, recorrer el ResultSet, convertir cada registro en un objeto Bitacora y agregarlo a una lista.
    
    // Query por usuario
    public List<clsBitacora> queryPorUsuario(int Usuid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clsBitacora bitacora = null;
        List<clsBitacora> bitacoras = new ArrayList<clsBitacora>();

        try {
            conn = Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_QUERY_POR_USUARIO);
            stmt = conn.prepareStatement(SQL_QUERY_POR_USUARIO);
            stmt.setInt(1, Usuid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int Bitcodigo    = rs.getInt("Bitcodigo");
                int usu          = rs.getInt("Usuid");
                int Aplcodigo    = rs.getInt("Aplcodigo");
                String Bitfecha  = rs.getString("Bitfecha");
                String Bitip     = rs.getString("Bitip");
                String Bitequipo = rs.getString("Bitequipo");
                String Bitaccion = rs.getString("Bitaccion");

                bitacora = new clsBitacora();
                bitacora.setBitcodigo(Bitcodigo);
                bitacora.setUsucodigo(Usuid);
                bitacora.setAplcodigo(Aplcodigo);
                bitacora.setBitfecha(Bitfecha);
                bitacora.setBitip(Bitip);
                bitacora.setBitequipo(Bitequipo);
                bitacora.setBitaccion(Bitaccion);

                bitacoras.add(bitacora);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return bitacoras;
    }

    // Query por aplicación
    public List<clsBitacora> queryPorAplicacion(int Aplcodigo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clsBitacora bitacora = null;
        List<clsBitacora> bitacoras = new ArrayList<clsBitacora>();

        try {
            conn = Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_QUERY_POR_APLICACION);
            stmt = conn.prepareStatement(SQL_QUERY_POR_APLICACION);
            stmt.setInt(1, Aplcodigo);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int Bitcodigo    = rs.getInt("Bitcodigo");
                int Usuid    = rs.getInt("Usuid");
                int apl          = rs.getInt("Aplcodigo");
                String Bitfecha  = rs.getString("Bitfecha");
                String Bitip     = rs.getString("Bitip");
                String Bitequipo = rs.getString("Bitequipo");
                String Bitaccion = rs.getString("Bitaccion");

                bitacora = new clsBitacora();
                bitacora.setBitcodigo(Bitcodigo);
                bitacora.setUsucodigo(Usuid);
                bitacora.setAplcodigo(apl);
                bitacora.setBitfecha(Bitfecha);
                bitacora.setBitip(Bitip);
                bitacora.setBitequipo(Bitequipo);
                bitacora.setBitaccion(Bitaccion);

                bitacoras.add(bitacora);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return bitacoras;
    }

    // Query por rango de fechas
    public List<clsBitacora> queryPorFechas (String fechaInicio, String fechaFin) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clsBitacora bitacora = null;
        List<clsBitacora> bitacoras = new ArrayList<clsBitacora>();

        try {
            conn = Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_QUERY_POR_FECHAS);
            stmt = conn.prepareStatement(SQL_QUERY_POR_FECHAS);
            stmt.setString(1, fechaInicio);
            stmt.setString(2, fechaFin);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int Bitcodigo    = rs.getInt("Bitcodigo");
                int Usuid    = rs.getInt("Usuid");
                int Aplcodigo    = rs.getInt("Aplcodigo");
                String Bitfecha  = rs.getString("Bitfecha");
                String Bitip     = rs.getString("Bitip");
                String Bitequipo = rs.getString("Bitequipo");
                String Bitaccion = rs.getString("Bitaccion");

                bitacora = new clsBitacora();
                bitacora.setBitcodigo(Bitcodigo);
                bitacora.setUsucodigo(Usuid);
                bitacora.setAplcodigo(Aplcodigo);
                bitacora.setBitfecha(Bitfecha);
                bitacora.setBitip(Bitip);
                bitacora.setBitequipo(Bitequipo);
                bitacora.setBitaccion(Bitaccion);

                bitacoras.add(bitacora);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return bitacoras;
    }

    // Query por acción
    public List<clsBitacora> queryPorAccion(String Bitaccion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clsBitacora bitacora = null;
        List<clsBitacora> bitacoras = new ArrayList<clsBitacora>();

        try {
            conn = Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_QUERY_POR_ACCION);
            stmt = conn.prepareStatement(SQL_QUERY_POR_ACCION);
            stmt.setString(1, Bitaccion);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int Bitcodigo    = rs.getInt("Bitcodigo");
                int Usuid    = rs.getInt("Usuid");
                int Aplcodigo    = rs.getInt("Aplcodigo");
                String Bitfecha  = rs.getString("Bitfecha");
                String Bitip     = rs.getString("Bitip");
                String Bitequipo = rs.getString("Bitequipo");
                String Bitaccion2 = rs.getString("Bitaccion");

                bitacora = new clsBitacora();
                bitacora.setBitcodigo(Bitcodigo);
                bitacora.setUsucodigo(Usuid);
                bitacora.setAplcodigo(Aplcodigo);
                bitacora.setBitfecha(Bitfecha);
                bitacora.setBitip(Bitip);
                bitacora.setBitequipo(Bitequipo);
                bitacora.setBitaccion(Bitaccion2);

                bitacoras.add(bitacora);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return bitacoras;
    }
}