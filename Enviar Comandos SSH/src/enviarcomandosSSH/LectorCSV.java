package enviarcomandosSSH;

import com.jcraft.jsch.JSchException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Lee el CSV, comprueba que el formato de sus elementos tiene sentido, crea el
 * archivo de resultados e inicia las conexiones.
 *
 * @author Aitor
 */
public class LectorCSV {

    /**
     * LISTADO con todas las filas en formato ArrayList<String>.
     */
    private final static ArrayList<ArrayList<String>> LISTADO = new ArrayList();

    /**
     * Scanner para interactuar con el usuario.
     */
    private final static Scanner SCANER = new Scanner(System.in);

    public static void main(String[] args) throws IOException, JSchException, IllegalAccessException {
        System.out.println(Constantes.CONSOLA.SALUDO.string);
        SCANER.nextLine();

        File archivoCSV = ObtenerCSV();
        ObtenerFilasCSV(archivoCSV);
        ComprobacionesDeFormato();
        CrearArchivo();
        System.out.println(Constantes.CONSOLA.CONEXIONES.string);
        CrearConexiones();
        System.out.println(Constantes.CONSOLA.DESPEDIDA.string);
    }

    private static File ObtenerCSV() {
        File directorio = new File(".");
        File archivoCSV;
        for (File archivo : directorio.listFiles()) {
            if (archivo.getName().toLowerCase().endsWith(Constantes.FORMATO)) {
                archivoCSV = archivo;
                return archivoCSV;
            }
        }
        Constantes.FinalizarPrograma(Constantes.CONSOLA.PROBLEMA_NO_CSV.string);
        return null;
    }

    /**
     * Lee el CSV y añade cada fila a LISTADO.
     */
    private static void ObtenerFilasCSV(File archivoCSV) {
        try {
            BufferedReader csvReader;
            csvReader = new BufferedReader(new FileReader(archivoCSV));
            String fila = "";
            while ((fila = csvReader.readLine()) != null && !fila.equals(";;;")) {
                String[] dato = fila.split(";");
                ArrayList<String> contenidoFila = new ArrayList();
                for (int i = 0; i < dato.length; i++) {
                    dato[i] = EliminarBOM(dato[i]);
                    contenidoFila.add(dato[i]);
                }
                LISTADO.add(contenidoFila);
            }
            csvReader.close();
        } catch (IOException ex) {
            Constantes.FinalizarPrograma(Constantes.CONSOLA.PROBLEMA_LECTURA.string);
        }
    }

    /**
     * Elimina los Byte Order Marks incluídos en el archivo CSV automáticamente.
     *
     * @param elemento Elemento en el que se va a procededer a la eliminación de
     * los BOM.
     * @return El elemento recibido como parámetro sin BOM
     */
    private static String EliminarBOM(String elemento) {
        elemento = elemento.replace("\"", "").replace("ï»¿", "");
        return elemento;
    }

    /**
     * Comprueba que el formato de cada uno de los datos de una fila es correcto
     * y, en caso contrario, notifica al usuario y finaliza el programa.
     *
     */
    private static void ComprobacionesDeFormato() {
        try {
            for (ArrayList<String> contenidoFila : LISTADO) {
                int NúmeroDeFallos = 0;
                if (!contenidoFila.get(0).matches(".+[.]com$")) {
                    System.out.println("El dominio \"" + contenidoFila.get(0) + "\" carece de un formato adecuado.");
                    NúmeroDeFallos++;
                }

                if (!contenidoFila.get(1).matches("\\d+")) {
                    System.out.println("El puerto correspondiente al dominio \"" + contenidoFila.get(0) + "\" no es un número.");
                    NúmeroDeFallos++;
                }

                if (NúmeroDeFallos > 0) {
                    System.out.println(NúmeroDeFallos + " error(es) de formato detectado(s) en el "
                            + "dominio \"" + contenidoFila.get(0) + "\". Corrige el CSV y vuelve a intentarlo.\n"
                            + "Pulsa cualquier tecla para finalizar el programa.");
                    Constantes.FinalizarPrograma(null);
                }
            }
        } catch (IndexOutOfBoundsException e){
            Constantes.FinalizarPrograma(Constantes.CONSOLA.FALTAN_DATOS.string);
        }
    }

    /**
     * Crea el archivo de resultados, sobreescribiéndolo en caso de que ya
     * exista
     */
    private static void CrearArchivo() {
        File archivoResultados = new File(Constantes.NOMBRE_ARCHIVO);
        try {
            archivoResultados.createNewFile();
        } catch (IOException ex) {
            Constantes.FinalizarPrograma(Constantes.CONSOLA.PROBLEMA_CREACIÓN.string);
        }
    }

    /**
     * Crea conexiones para cada una de los datos contenidos en las filas.
     *
     * @throws JSchException Cualquier error al establecer conexión SSH.
     * @throws IllegalAccessException Indica que ya existe una conexión SSH
     * establecida.
     * @throws IOException Error de entrada-salida al escribir en el fichero.
     */
    private static void CrearConexiones() throws JSchException, IllegalAccessException, IOException {
        for (ArrayList<String> contenidoFila : LISTADO) {
            new Conexión(contenidoFila.get(0), Integer.valueOf(contenidoFila.get(1)), contenidoFila.get(2), contenidoFila.get(3));
        }
    }
}
