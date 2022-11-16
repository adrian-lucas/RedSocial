package backend.serviciousuarios;

public class Usuario {
    private int idUsuario;
    private String nombre;
    public Usuario(int idUsuario,String nombre){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }
    public String getNombre(){
        return  nombre;
    }

}
