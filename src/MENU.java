import java.io.IOException;
import java.util.Scanner;

public class MENU {
    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);
        String opcion;
        String nombreFichero="";

        while (true){
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("------------------------------");
            System.out.println("MENU:");
            System.out.println("------------------------------");
            System.out.println("1) Crear fichero articulos binario.");
            System.out.println("2) Visualizar los registros.");
            System.out.println("3) Consultar un registro.");
            System.out.println("4) Insertar n registros.");
            System.out.println("5) Actualizar los datos de un articulo.");
            System.out.println("6) Visualizar totales unidades e importe.");
            System.out.println("7) Borrar un articulo.");
            System.out.println("0) Salir.");
            System.out.println("------------------------------");
            System.out.println("Escribe la opcion que quieres elegir:");
            opcion = teclado.nextLine();

            if(opcion.isEmpty()){
                System.out.println("Saliendo del programa...");
                break;
            }
            switch (opcion){
                case "1":
                    CrearFichero.CrearFicheroBytes(nombreFichero);
                    break;
                case "2":
                    VizualizarRegistro.VizualizarRegistros(nombreFichero);
                    break;
                case "3":
                    ConsultarRegistro.ConsultarRegistro(nombreFichero);
                    break;
                case "4":
                    InsertarRegistro.InstertarRegistros(nombreFichero);
                    break;
                case "5":
                    ActualizarDatos.ActualizarDatos(nombreFichero);
                    break;
                case "6":
                    VisualizarTotales.VisualizarTotalesUnidadesImporte(nombreFichero);
                    break;
                case "7":
                   BorrarArticulo.BorrarArticulo(nombreFichero);
                    break;
                case "0":
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
        teclado.close();
    }
}

