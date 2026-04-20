package Controlador;

public class clsPelicula {
    
    private int idPelicula;
    private String nombre;
    private String clasificacion;
    private String genero;
    private String subtitulado;
    private String idioma;
    private double precio;
    
    // Constructor vacío
    public clsPelicula() {
    }
    
    // Constructor con parámetros
    public clsPelicula(int idPelicula, String nombre, String clasificacion, 
                       String genero, String subtitulado, String idioma, double precio) {
        this.idPelicula = idPelicula;
        this.nombre = nombre;
        this.clasificacion = clasificacion;
        this.genero = genero;
        this.subtitulado = subtitulado;
        this.idioma = idioma;
        this.precio = precio;
    }
    
    // ========== GETTERS Y SETTERS ==========
    
    public int getIdPelicula() {
        return idPelicula;
    }
    
    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getClasificacion() {
        return clasificacion;
    }
    
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getSubtitulado() {
        return subtitulado;
    }
    
    public void setSubtitulado(String subtitulado) {
        this.subtitulado = subtitulado;
    }
    
    public String getIdioma() {
        return idioma;
    }
    
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    // ========== MÉTODO toString ==========
    
    @Override
    public String toString() {
        return "clsPelicula{" + 
                "idPelicula=" + idPelicula + 
                ", nombre='" + nombre + '\'' + 
                ", clasificacion='" + clasificacion + '\'' + 
                ", genero='" + genero + '\'' + 
                ", subtitulado='" + subtitulado + '\'' + 
                ", idioma='" + idioma + '\'' + 
                ", precio=" + precio + 
                '}';
    }
}
