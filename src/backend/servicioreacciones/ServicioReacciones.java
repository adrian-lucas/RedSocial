package backend.servicioreacciones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class ServicioReacciones {
    private String ruta;
    private HashMap<Emocion,Integer> reacciones;

    public static void main(String[] args){
        ServicioReacciones servicioReacciones = new ServicioReacciones();
       // servicioReacciones.listarResumenReacciones(9);
        printReacts((HashMap<Emocion, Integer>) servicioReacciones.listarResumenReacciones(2));
        //servicioReacciones.agregarReaccion(9,3,Emocion.Explain);

    }
    private static void printReacts(HashMap<Emocion,Integer> reacts){
        for (Map.Entry<Emocion,Integer> e :reacts.entrySet()) {
            System.out.print(e.getKey()+":"+e.getValue()+" ");

        }
    }
    public ServicioReacciones(){
        this.ruta = "./Reacciones.csv";
        this.reacciones = new HashMap<>();
    }
    public void agregarReaccion(int idPublicacion,int idUsuario,Emocion reaccion){

        String agreagarReaccion = idPublicacion+","+reaccion+","+idUsuario+"\n";

        try{
            BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta,true));
            escritor.write(agreagarReaccion);
            escritor.flush();
        }catch (Exception e){
            System.out.println("Error ServicioUsuarios.agregarReaccion(): "+e);
        }

    }
    public Map<Emocion,Integer> listarResumenReacciones(int idPublicacion) {
        reacciones.clear();
        agregarReacciones();

        String linea="";

        try {
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");

                int idPublicacionCsv= Integer.parseInt(datos[0]);
                if(idPublicacionCsv == idPublicacion){
                    String emocion = datos[1];
                    Emocion e = Emocion.valueOf(emocion);
                    int contador = reacciones.get(e);
                    reacciones.put(e,contador+1);
                }

            }
            lector.close();

        } catch (Exception e) {
            System.out.println("Error ServicioReacciones.listarResumenReacciones :"+ e);
        }
        return  reacciones;
    }
    private  void agregarReacciones(){
        reacciones.put(Emocion.Like,0);
        reacciones.put(Emocion.Love,0);
        reacciones.put(Emocion.Sad,0);
        reacciones.put(Emocion.Happy,0);
        reacciones.put(Emocion.Mad,0);
        reacciones.put(Emocion.Surprise,0);
        reacciones.put(Emocion.Care,0);
        reacciones.put(Emocion.Indifferent,0);
        reacciones.put(Emocion.Explain,0);
    }
}
