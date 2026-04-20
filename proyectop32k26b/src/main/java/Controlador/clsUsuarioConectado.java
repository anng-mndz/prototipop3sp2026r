/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author visitante
 */
public class clsUsuarioConectado {
    private static int UsuId;
    private static String UsuNombre;     

    public clsUsuarioConectado() {
    }

    public static int getUsuId() {
        return UsuId;
    }

    public static void setUsuId(int UsuCodigo) {
        clsUsuarioConectado.UsuId = UsuCodigo;
    }

    public static String getUsuNombre() {
        return UsuNombre;
    }

    public static void setUsuNombre(String UsuNombre) {
        clsUsuarioConectado.UsuNombre = UsuNombre;
    }
    

}
