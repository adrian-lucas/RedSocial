package backend.serviciopublicaciones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServicioPublicaciones {
    private String ruta;
    private int ultimoId;
    private List<Integer> listaIdPublicaciones;
    private Publicacion publicacion;
    public static  void main(String[] args){
        ServicioPublicaciones servicioPublicaciones = new ServicioPublicaciones();

        //printPublication(servicioPublicaciones.buscarPublicacion(2));
        //servicioPublicaciones.buscarPublicacion(3);
       // System.out.println("id generado: "+servicioPublicaciones.generarId()+" fecha generada: "+servicioPublicaciones.generarFecha());
        printListaidPublicacion(servicioPublicaciones.listarPublicaciones());
       // servicioPublicaciones.agregarPublicacion(5,"Y así, ser va otro año");
        //System.out.println(servicioPublicaciones.quitarComillas("'1'"));
       // servicioPublicaciones.agregarPublicacion(2,"Se acerca la navidad");


    }
    private static  void printPublication(Publicacion p){
        System.out.println("idUsuario: "+ p.getIdUsuario());
        System.out.println("fecha: "+ p.getFecha());
        System.out.println("contenido: "+ p.getContenido());
    }
    private static void printListaidPublicacion(List<Integer> lista){
        for (Integer i:lista) {
            System.out.println(i);
        }
    }
    public ServicioPublicaciones(){
        this.ruta = "./Publicaciones.csv";
        this.listaIdPublicaciones = new ArrayList<>();
    }
    public void agregarPublicacion(int idUsuario,String contenido){
        //String publicacion = generarId()+","+idUsuario+","+contenido+","+generarFecha();
        String publicacion = paraCSV(4+"",idUsuario+"",contenido,generarFecha());

        try{
            BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta,true));
            escritor.write(publicacion+"\n");
            escritor.flush();
        }catch (Exception e){
            System.out.println("Error ServicioUsuarios.agregarUsuario(): "+e);
        }


    }
    public Publicacion buscarPublicacion(int idPublicacion){
        recorrerCSV(idPublicacion);
        return  publicacion;
    }
    public List<Integer> listarPublicaciones(){
        listaIdPublicaciones.clear();
        recorrerCSV();
        return listaIdPublicaciones;
    }
    private int generarId(){
        recorrerCSV();
        return ultimoId+1;
    }
    private String generarFecha(){
        LocalDate fecha = LocalDate.now();
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(fecha).toString();


    }
    private void recorrerCSV(){
         this.ultimoId = 0;
        String linea=null;
        try {
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                String idP = datos[0];

                ultimoId = convertir(idP);
                listaIdPublicaciones.add(ultimoId);

            }
            lector.close();

        } catch (Exception e) {
            System.out.println("Error ServicioPublicaciones.recorrerCSV() :"+ e);
        }
    }
    private void recorrerCSV(int idPublicacion){
        this.ultimoId = 0;


        String linea="";

        try {
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                String idP = datos[0];

                ultimoId = convertir(idP);
                if(ultimoId == idPublicacion){
                    String idUser = datos[1];
                    int idUsuario = convertir(idUser);
                    String contenido = quitarComillas(datos[2]);
                    String fecha = datos[3];
                    this.publicacion = new Publicacion(idPublicacion,idUsuario,contenido,fecha);
                }

            }
            lector.close();

        } catch (Exception e) {
            System.out.println("Error ServicioPublicaciones.recorrerCSV(_idPublicacion_) :"+ e);
        }

    }
    private int convertir(String cadena){
        String numero = cadena.replaceAll("[^0-9]+", "");
        return Integer.parseInt(numero);
    }
    private String quitarComillas(String cadena){
         cadena = cadena.substring(0, cadena.length() - 1);
         cadena = cadena.substring(1,cadena.length());
        return  cadena;

    }
    private String paraCSV(String idPublicacion,String idUsuario,String contenido,String fecha) {

        return String.format("%s,%s,\"%s\",%s",
                idPublicacion,
                idUsuario,
                contenido,
                fecha
        );
    }
}
