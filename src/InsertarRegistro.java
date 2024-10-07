import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InsertarRegistro {
    public static void InstertarRegistros(String nombreFichero) {
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

        System.out.println("Escribe el código del artículo que quieres añadir:");
        int nuevoCodigo;
        try {
            nuevoCodigo = teclado.nextInt();
            teclado.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un número válido para el código.");
            teclado.nextLine();
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

                dataOutput.writeInt(codigo);
                dataOutput.writeUTF(nombre);
                dataOutput.writeFloat(precio);
                dataOutput.writeInt(unidades);

                if (codigo == nuevoCodigo) {
                    System.out.println("Error: El código del artículo ya existe. No se puede añadir.");
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("Escribe el nombre del artículo:");
                String nuevoNombre = teclado.nextLine();

                if (nombreYaExiste(fichero, nuevoNombre)) {
                    System.out.println("Error: El nombre del artículo ya existe. No se puede añadir.");
                    return;
                }

                System.out.println("Escribe el precio del artículo:");
                float nuevoPrecio;
                try {
                    nuevoPrecio = teclado.nextFloat();
                } catch (InputMismatchException e) {
                    System.out.println("Error: Debes ingresar un número válido para el precio.");
                    return;
                }

                System.out.println("Escribe las unidades del artículo:");
                int nuevasUnidades;
                try {
                    nuevasUnidades = teclado.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Error: Debes ingresar un número válido para las unidades.");
                    return;
                }

                dataOutput.writeInt(nuevoCodigo);
                dataOutput.writeUTF(nuevoNombre);
                dataOutput.writeFloat(nuevoPrecio);
                dataOutput.writeInt(nuevasUnidades);

                System.out.println("Artículo añadido correctamente.");
            }

        } catch (IOException e) {
            System.out.println("Error al manejar el fichero: " + e.getMessage());
        }

        if (!encontrado) {
            fichero.delete();
            tempFichero.renameTo(fichero);
        } else {
            tempFichero.delete();
        }
    }

    public static boolean nombreYaExiste(File fichero, String nuevoNombre) {
        try (DataInputStream dataInput = new DataInputStream(new FileInputStream(fichero))) {
            while (dataInput.available() > 0) {
                int codigo = dataInput.readInt();
                String nombre = dataInput.readUTF();
                dataInput.readFloat();
                dataInput.readInt();

                if (nombre.equalsIgnoreCase(nuevoNombre)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return false;
    }


}
