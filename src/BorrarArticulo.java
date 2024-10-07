import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BorrarArticulo {

    public static void BorrarArticulo(String nombreFichero) {
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


        System.out.println("Escribe el código del artículo que quieres borrar:");
        int codigoABorrar = -1;
        try {
            codigoABorrar = teclado.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un número válido para el código.");
            return;
        }

        boolean encontrado = false;
        File tempFichero = new File("temp.dat");

        try (DataInputStream dataInput = new DataInputStream(new FileInputStream(fichero));
             DataOutputStream dataOutput = new DataOutputStream(new FileOutputStream(tempFichero))) {

            while (dataInput.available() > 0) {
                int codigo = dataInput.readInt();
                String nombre = dataInput.readUTF();
                float precio = dataInput.readFloat();
                int unidades = dataInput.readInt();

                if (codigo == codigoABorrar) {
                    System.out.println("Artículo con código " + codigo + " borrado correctamente.");
                    encontrado = true;
                } else {
                    dataOutput.writeInt(codigo);
                    dataOutput.writeUTF(nombre);
                    dataOutput.writeFloat(precio);
                    dataOutput.writeInt(unidades);
                }
            }

            if (!encontrado) {
                System.out.println("No pude borrar el artículo. Código no encontrado.");
            }

        } catch (IOException e) {
            System.out.println("Error al manejar el fichero: " + e.getMessage());
        }

        if (encontrado) {
            fichero.delete();
            tempFichero.renameTo(fichero);
        } else {
            tempFichero.delete();
        }
    }
}
