import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class VisualizarTotales {
    public static void VisualizarTotalesUnidadesImporte(String nombreFichero) {
        Scanner teclado = new Scanner(System.in);

        if (nombreFichero == null || nombreFichero.isEmpty()) {
            System.out.println("Escribe la ruta o nombre de fichero que contiene los artículos:");
            nombreFichero = teclado.nextLine();
        }

        File fichero = new File(nombreFichero);
        if (!fichero.exists() || !nombreFichero.endsWith(".dat")) {
            System.out.println("Fichero NO encontrado o el archivo no tiene la extensión '.dat'!");
            return;
        }

        int totalUnidades = 0;
        float totalImporte = 0.0f;

        String articuloMasCaro = null;
        String articuloMayorImporte = null;
        float precioMasCaro = 0.0f;
        float mayorImporte = 0.0f;

        boolean primerArticulo = true;

        try (DataInputStream dataInput = new DataInputStream(new FileInputStream(fichero))) {
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-25s %-12s %-10s %-10s\n", "COD ARTI", "NOMBRE ART", "UNIDADES", "PRECIO", "IMPORTE");
            System.out.println("--------- ------------------------- ------------ --------- ---------");

            while (dataInput.available() > 0) {
                int codigo = dataInput.readInt();
                String nombre = dataInput.readUTF();
                float precio = dataInput.readFloat();
                int unidades = dataInput.readInt();
                float importe = precio * unidades;

                System.out.printf("%-10d %-25s %-12d %-10.2f %-10.2f\n", codigo, nombre, unidades, precio, importe);

                totalUnidades += unidades;
                totalImporte += importe;

                if (primerArticulo) {
                    precioMasCaro = precio;
                    mayorImporte = importe;
                    articuloMasCaro = nombre;
                    articuloMayorImporte = nombre;
                    primerArticulo = false;
                } else {
                    if (precio > precioMasCaro) {
                        precioMasCaro = precio;
                        articuloMasCaro = nombre;
                    }

                    if (importe > mayorImporte) {
                        mayorImporte = importe;
                        articuloMayorImporte = nombre;
                    }
                }
            }

            if (primerArticulo) {
                System.out.println("El archivo está vacío, no se encontraron artículos.");
            } else {
                System.out.println("--------- ------------------------- ------------ --------- ---------");
                System.out.printf("TOTALES: %-12d %-10.2f\n", totalUnidades, totalImporte);
                System.out.println("Artículo más caro: " + articuloMasCaro);
                System.out.println("Artículo con mayor importe: " + articuloMayorImporte);
            }

        } catch (IOException e) {
            System.out.println("Error al manejar el fichero: " + e.getMessage());
        }
    }
}

