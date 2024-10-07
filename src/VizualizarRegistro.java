import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class VizualizarRegistro {
    public static void VizualizarRegistros(String nombreFichero) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Escribe la ruta de fichero o solo nombre de fichero de que quieres vizualizar:");
        nombreFichero = teclado.nextLine();
        File fichero = new File(nombreFichero);
        if (!nombreFichero.endsWith(".dat")) {
            System.out.println("Tienes que escribir el nombre de fichero con el .dat, porque sin .dat programa no va a poder a encontrar este archivo");
        } else if (!fichero.exists()) {
            System.out.println("Fichero NO encontrado!");
        } else if (fichero.exists()) {
            System.out.println("Fichero encontrado!");
            try (
                    FileInputStream fileinput = new FileInputStream(fichero); //para abrir el archivo
                    DataInputStream fileDATAinput = new DataInputStream(fileinput) //para leer el los datos en el archivo
            ) {
                System.out.println("-------------------------------------------------------------------");
                System.out.printf("%-9s %-25s %-12s %-10s\n", "COD.ARTI", "NOMBRE.ART", "UNIDADES", "PRECIO");
                /*
                explicaión:
                - %-9s: Alinéa el código a la izquierda, ocupando 9 espacios.
                - %-25s: Alinéa el nombre a la izquierda, ocupando 25 espacios.
                - %-12s: Alinéa las unidades a la izquierda, ocupando 12 espacios.
                - %-10s: Alinéa las unidades a la izquierda, ocupando 10 espacios.
                 */
                System.out.println("--------- ------------------------- ------------ ---------");
                while (fileDATAinput.available() > 0) {
                    int codigo = fileDATAinput.readInt();       // lee el codigo
                    String nombre = fileDATAinput.readUTF();    // lee el nombre de articulo
                    float precio = fileDATAinput.readFloat();   // lee los precios
                    int unidades = fileDATAinput.readInt();     // lee los unidades

                    System.out.printf("%-9d %-25s %-12d %-10.2f\n", codigo, nombre, unidades, precio);
                    /*
                    explicacion
                    - %-9d: Alinéa el código a la izquierda, ocupando 9 espacios.
                    - %-25s: Alinéa el nombre a la izquierda, ocupando 25 espacios.
                    - %-12d: Alinéa las unidades a la izquierda, ocupando 12 espacios.
                    - %-10.2f: Muestra el precio con 2 decimales y ocupa 10 espacios.
                     */
                }
            } catch (IOException e) {
                System.out.println("No se pude leer el archivo " + e.getMessage());
            }
        }
    }
}
