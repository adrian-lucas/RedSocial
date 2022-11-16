package frontend;

import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.serviciopublicaciones.Publicacion;
import backend.servicioreacciones.ServicioReacciones;
import backend.servicioreacciones.Emocion;
import backend.serviciousuarios.ServicioUsuarios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IU {
    private ServicioPublicaciones servicioPublicaciones;
    private ServicioReacciones servicioReacciones;
    private ServicioUsuarios servicioUsuarios;
    private Scanner sc;
    private int idUsuario;
    private boolean sesionIniciada;
    private String nombreUsuario;
    private Emocion[] emocionesDisponibles;
    public IU(ServicioPublicaciones servicioPublicaciones,
              ServicioReacciones servicioReacciones,
              ServicioUsuarios servicioUsuarios){
        this.sesionIniciada = false;
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioReacciones = servicioReacciones;
        this.servicioUsuarios = servicioUsuarios;
        this.emocionesDisponibles = new Emocion[9];
        llenarEmocionesDisponibles();
        this.sc = new Scanner(System.in);

    }
    private void llenarEmocionesDisponibles(){
        emocionesDisponibles[0] = Emocion.Like;
        emocionesDisponibles[1] = Emocion.Love;
        emocionesDisponibles[2] = Emocion.Sad;
        emocionesDisponibles[3] = Emocion.Happy;
        emocionesDisponibles[4] = Emocion.Mad;
        emocionesDisponibles[5] = Emocion.Surprise;
        emocionesDisponibles[6] = Emocion.Care;
        emocionesDisponibles[7] = Emocion.Indifferent;
        emocionesDisponibles[8] = Emocion.Explain;
    }
    public void iniciar(){
        String comando="";
        inicioSesion();

            while (!comando.equals("salir")) {
                if (comando.equals("cerrar sesion")) {
                    cerrarSesion();
                } else if (comando.equals("crear publicacion")) {
                    publicar();
                } else if (comando.equals("reaccionar")) {
                    reaccionar();
                }
                comando = sc.nextLine();
            }

    }
    private void cerrarSesion(){
        sesionIniciada = false;
        inicioSesion();
        
    }
    private void inicioSesion(){
        letrerosInicio();
        this.nombreUsuario = verificarNombre();
        idUsuario = servicioUsuarios.agregarUsuario(nombreUsuario);
        System.out.println("Bienvenido " + nombreUsuario+ " con Id"+ idUsuario);
        sesionIniciada = true;
        imprimirPublicaciones();
    }
    private  String verificarNombre(){
        String nombre = sc.nextLine();
        if(nombre.equals("salir")||
                nombre.equals("cerrar sesion") ||
                nombre.equals("crear publicacion")||
                nombre.equals("reaccionar")){

            System.out.println("Primero debe iniciar sesion");
            System.out.println("Ingrese nombre de usuario");
            verificarNombre();
        }
        return nombre;
    }
    private void letrerosInicio(){
        System.out.println("Para crear una publicacion ingrese 'crear publicacion'");
        System.out.println("Para reaccionar a una publicación ingrese 'reaccionar'");
        System.out.println("Para cerrar sesion ingrese 'cerrar sesion'");
        System.out.println("Para salir de la aplicacion ingrese 'salir'");
        System.out.println("Ingrese su nombre de usuario:");

    }
    private void imprimirPublicaciones(){
        List<Integer> listarPublicaciones = servicioPublicaciones.listarPublicaciones();
        int numPublicacion = 1;
        for (Integer i: listarPublicaciones) {
           Publicacion p  =  servicioPublicaciones.buscarPublicacion(i);
           System.out.println("Publicacion: "+(numPublicacion));
           imprimirUnaPublicacion(p);
           imprimirReacciones(i);
           System.out.println("");
           numPublicacion++;
        }

    }
    private void imprimirUnaPublicacion(Publicacion p){
        int id = p.getIdUsuario();
        String nombre = servicioUsuarios.buscarUsuario(id).getNombre();
        String fecha = p.getFecha();
        String contenido = p.getContenido();
        System.out.println(nombre +"\n"+ fecha+"\n"+contenido);

    }
    private void imprimirReacciones(int idPublicacion){
        HashMap<Emocion,Integer> emociones = (HashMap<Emocion,Integer>)
                                              servicioReacciones.listarResumenReacciones(idPublicacion);

        for (Map.Entry<Emocion,Integer> e :emociones.entrySet()) {
            System.out.print(e.getKey()+":"+e.getValue()+" ");
        }
        System.out.println("");
    }

    private void publicar(){
        System.out.println("Ingrese el contenido:");
        String contenido = sc.nextLine();
        servicioPublicaciones.agregarPublicacion(idUsuario,contenido);
        imprimirPublicaciones();
    }

    private void reaccionar(){
        List<Integer> publicaciones = servicioPublicaciones.listarPublicaciones();
        System.out.println("Ingrese el número de la publicación");
        int numPublicacion = sc.nextInt()-1;

        if((numPublicacion>=0) && (numPublicacion <= publicaciones.size()-1)){
            System.out.println("Seleccione el número de su reacción");
            System.out.println("1:like  2:Love  3:Sad  4:Happy  5:mad  6:Surprise  7:Care  8:Indifferent  9:Explain");
            int numReaccion = sc.nextInt();
            Emocion e = emocionesDisponibles[numReaccion-1];
            servicioReacciones.agregarReaccion(publicaciones.get(numPublicacion),idUsuario,e);
            imprimirPublicaciones();
        }else{
            System.out.println("Número inválido");
            reaccionar();
        }


    }
}
