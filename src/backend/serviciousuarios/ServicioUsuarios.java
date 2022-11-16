package backend.serviciousuarios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioUsuarios {
    private  final static int NO_EXISTE = -1;
    private String ruta;

    private int ultimoId;
    private List<Integer> listaIdsUsuarios;
    private String nombreUsuario;

    public static void main(String[] args){
        ServicioUsuarios servicioUsuarios = new ServicioUsuarios();
       //System.out.println(servicioUsuarios.existe("Homero"));
        //int res = servicioUsuarios.obtenerId();
        //System.out.println(res);
        System.out.println(servicioUsuarios.agregarUsuario("Ronald Teran"));;
        //servicioUsuarios.eliminarUsuario("Ronald Teran");
        //borrarLinea("Hector");
        //System.out.println(servicioUsuarios.buscarUsuario(1));
        //for (Integer i:servicioUsuarios.listarUsuarios()) {
          //  System.out.println(i);
      // }
    }
    public ServicioUsuarios(){
        this.ruta = "./Usuarios.csv";
        this.ultimoId = 0;
        this.nombreUsuario="";

    }
    public int agregarUsuario(String nombre){
        int res = existe(nombre);

        if(res == NO_EXISTE){
            res = guardar(nombre);
        }
       return res;
    }
    private int guardar(String nombreUsuario){
        int id = obtenerId();
        String usuario = id+","+nombreUsuario;

        try{
            BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta,true));
            escritor.newLine();
            escritor.write(usuario);
            escritor.close();
        }catch (Exception e){
            System.out.println("Error ServicioUsuarios.agregarUsuario(): "+e);
        }
        return id;

    }
    private int existe(String nombreUsuario){    //devuleve un -1 si el usuario no existe
        int idUsuario=NO_EXISTE;
        boolean existe = false;
        int i = 0;
        List<Integer> listaUsuarios = listarUsuarios();

        while(!existe && (i<listaUsuarios.size())){

            idUsuario = listaUsuarios.get(i);
            String nombre = buscarUsuario(idUsuario).getNombre();

            if(nombre.equals(nombreUsuario)){
                existe= true;
            }else{
                idUsuario = NO_EXISTE;
            }

            i++;
        }
        return idUsuario;
    }
    public void eliminarUsuario(String nombre){
        File inputFile = new File("./Usuarios.csv");
        File tempFile = new File("./UsuariosTemp.csv");
       // File deleteFile = new File("./UsuariosTemp.csv");
        try {
            BufferedReader lector = new BufferedReader(new FileReader(inputFile));
            BufferedWriter escritor = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = lector.readLine()) != null) {
                String[] line_arr = currentLine.split(",");
                String usuarioEliminar = line_arr[1];
                if (usuarioEliminar.equals(nombre)) {
                    continue;
                } else {
                   escritor.write(currentLine + System.getProperty("line.separator"));
                }

            }
            escritor.close();
            lector.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);



        }catch (Exception e){
            System.out.println("Error ServicioUsuarios.eliminarUsuario(_nombre_): "+e);
        }

    }
    public Usuario buscarUsuario(int id){
        recorrerCsv(id);
        Usuario u= (nombreUsuario.equals(""))?null:new Usuario(id,nombreUsuario);
        return u;
    }
    public List<Integer> listarUsuarios(){
        recorrerCsv();
        return  listaIdsUsuarios;
    }
    private int obtenerId() {
        recorrerCsv();
        return ultimoId+1;
    }
    private void recorrerCsv(){
        this.ultimoId = 0;
        this.nombreUsuario="";
        this.listaIdsUsuarios = new ArrayList<>();
        String linea="";

        try {
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            while ((linea = lector.readLine()) != null) {

                    String[] datos = linea.split(",");
                if(!datos[0].equals("")) {
                    ultimoId = Integer.parseInt(datos[0]);
                    listaIdsUsuarios.add(ultimoId);
                }

            }
            lector.close();

        } catch (Exception e) {
            System.out.println("Error ServicioUsuarios.getId() :"+ e);
        }

    }
    private void recorrerCsv(int id){
        this.ultimoId = 0;
        this.nombreUsuario="";
        this.listaIdsUsuarios = new ArrayList<>();
        String linea="";

        try {
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if(!datos[0].equals("")) {
                    ultimoId = Integer.parseInt(datos[0]);
                    listaIdsUsuarios.add(ultimoId);
                    nombreUsuario = (ultimoId == id) ? datos[1] : nombreUsuario;
                }

            }
            lector.close();

        } catch (Exception e) {
            System.out.println("Error ServicioUsuarios.getId() :"+ e);
        }

    }


}
