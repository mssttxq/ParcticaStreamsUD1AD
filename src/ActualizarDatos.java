import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ActualizarDatos {

    public static void ActualizarDatos(String nombreFichero) {
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


        System.out.println("Escribe el código del artículo que quieres actualizar:");
        int codigoAActualizar = -1;
        try {
            codigoAActualizar = teclado.nextInt();
            teclado.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un número válido para el código.");
            return;
        }

        boolean encontrado = false;
        File tempFichero = new File("temp.dat"); // Archivo temporal

        try (DataInputStream dataInput = new DataInputStream(new FileInputStream(fichero));
             DataOutputStream dataOutput = new DataOutputStream(new FileOutputStream(tempFichero))) {

            while (dataInput.available() > 0) {
                int codigo = dataInput.readInt();
                String nombre = dataInput.readUTF();
                float precio = dataInput.readFloat();
                int unidades = dataInput.readInt();

                if (codigo == codigoAActualizar) {
                    System.out.println("Artículo encontrado: ");
                    System.out.printf("%-9s %-25s %-12s %-10s\n", "COD.ARTI", "NOMBRE.ART", "UNIDADES", "PRECIO");
                    System.out.printf("%-9d %-25s %-12d %-10.2f\n", codigo, nombre, unidades, precio);

                    System.out.println("Escribe el nuevo codigo del articulo (deja vacio para no cambiar)");
                    String nuevoCadigoStr=teclado.nextLine();
                    int nuevoCodigo = codigo;
                    if (!nuevoCadigoStr.isEmpty()){
                        try {
                            nuevoCodigo=Integer.parseInt(nuevoCadigoStr);
                        }catch (NumberFormatException e){
                            System.out.println("Codigo no valido. Mateniendo el codigo actual");
                        }
                    }
                    System.out.println("Escribe el nuevo nombre del artículo (deja vacío para no cambiar):");
                    String nuevoNombre = teclado.nextLine();
                    if (nuevoNombre.isEmpty()) {
                        nuevoNombre = nombre;
                    }

                    System.out.println("Escribe el nuevo precio del artículo (o deja vacío para no cambiar):");
                    String nuevoPrecioStr = teclado.nextLine();
                    float nuevoPrecio = precio;
                    if (!nuevoPrecioStr.isEmpty()) {
                        try {
                            nuevoPrecio = Float.parseFloat(nuevoPrecioStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Precio no válido. Manteniendo precio actual.");
                        }
                    }

                    System.out.println("Escribe las nuevas unidades del artículo (o deja vacío para no cambiar):");
                    String nuevasUnidadesStr = teclado.nextLine();
                    int nuevasUnidades = unidades;
                    if (!nuevasUnidadesStr.isEmpty()) {
                        try {
                            nuevasUnidades = Integer.parseInt(nuevasUnidadesStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Unidades no válidas. Manteniendo unidades actuales.");
                        }
                    }

                    dataOutput.writeInt(nuevoCodigo);
                    dataOutput.writeUTF(nuevoNombre);
                    dataOutput.writeFloat(nuevoPrecio);
                    dataOutput.writeInt(nuevasUnidades);
                    encontrado = true;

                    System.out.println("Artículo actualizado correctamente.");
                } else {
                    dataOutput.writeInt(codigo);
                    dataOutput.writeUTF(nombre);
                    dataOutput.writeFloat(precio);
                    dataOutput.writeInt(unidades);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al manejar el fichero: " + e.getMessage());
        }


        if (encontrado) {
            fichero.delete();
            tempFichero.renameTo(fichero);
        } else {
            tempFichero.delete();
            System.out.println("No se encontró el artículo con el código proporcionado.");
        }
    }

}
