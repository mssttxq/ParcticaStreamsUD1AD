import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ConsultarRegistro {
    public static void ConsultarRegistro(String nombreFichero) {
        String nombreArticulo;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Escribe la ruta del fichero de que quieres consultar el registro:");
        nombreFichero = teclado.nextLine();
        File fichero = new File(nombreFichero);

        if (!nombreFichero.endsWith(".dat")) {
            System.out.println("Tienes que escribir el nombre de fichero con el .dat.");
        } else if (!fichero.exists()) {
            System.out.println("Fichero NO encontrado!");
        } else {
            System.out.println("Fichero encontrado.");
            System.out.println("Escribe el nombre de  articulo que quieres consultar:");
            nombreArticulo = teclado.nextLine();

            boolean encontrado = false;

            try (FileInputStream ficheroInput = new FileInputStream(fichero);
                 DataInputStream ficheroDATAinput = new DataInputStream(ficheroInput)) {

                while (ficheroDATAinput.available() > 0) {
                    int codigo = ficheroDATAinput.readInt();
                    String nombre = ficheroDATAinput.readUTF();
                    float precio = ficheroDATAinput.readFloat();
                    int unidades = ficheroDATAinput.readInt();

                    if (nombre.equals(nombreArticulo)) {
                        System.out.println("-------------------------------------------------------------------");
                        System.out.printf("%-9s %-25s %-12s %-10s\n", "COD.ARTI", "NOMBRE.ART", "UNIDADES", "PRECIO");
                        System.out.println("--------- ------------------------- ------------ ---------");
                        System.out.printf("%-9d %-25s %-12d %-10.2f\n", codigo, nombre, unidades, precio);
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    System.out.println("Artículo no encontrado.");
                }

            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        }
    }
}
