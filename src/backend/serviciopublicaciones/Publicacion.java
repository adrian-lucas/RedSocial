package backend.serviciopublicaciones;

public class Publicacion {
    private int idPublicacion;
    private int idUsuario;
    private String contenido;
    private String fecha;
    public Publicacion(int idPublicacion, int idUsuario, String contenido, String fecha){
        this.idPublicacion = idPublicacion;
        this.idUsuario = idUsuario;
        this.contenido = contenido;
        this.fecha     = fecha;

    }
    public int getIdUsuario(){
        return  idUsuario;
    }
    public String getContenido(){
        return  contenido;
    }
    public String getFecha(){
        return  fecha;
    }


}
