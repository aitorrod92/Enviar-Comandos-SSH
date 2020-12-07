package enviarcomandosSSH;

import java.util.Scanner;

public class Constantes {

    /**
     * Nombre del archivo que albergará los resultados.
     */
    public final static String NOMBRE_ARCHIVO = "resultados.txt";

    /**
     * Formato CSV
     */
    public final static String FORMATO = ".csv";

    /**
     * Distintos mensajes invariables que se muestran por consola.
     */
    public enum CONSOLA {
        SALUDO("Saludo"),
        PROBLEMA_LECTURA("Problema lectura"),
        PROBLEMA_CREACIÓN("Problema creación"),
        PROBLEMA_NO_CSV("Problema no csv"),
        PROBLEMA_CONEXIÓN("Problema conexión"),
        SESIÓN_INICIADA("Sesión iniciada"),
        SESIÓN_NO_INICIADA("Sesión no iniciada"),
        CONEXIONES("Conexiones"),
        DESPEDIDA("Despedida"),
        FALTAN_DATOS("Faltan datos");

        public String string;

        CONSOLA(String comando) {
            switch (comando) {
                case "Saludo":
                    string = "Bienvenido al programa para comprobar la salud de los servidores. Pulsa"
                            + " cualquier tecla si tu archivo CSV ya está en la carpeta. Si hay varios archivos CSV, se"
                            + " utilizará el primero por orden alfabético. El resultado del análisis se mostrará en el fichero "
                            + NOMBRE_ARCHIVO + ".";
                    break;
                case "Problema lectura":
                    string = "No se ha podido leer el archivo CSV. Pulsa cualquier tecla para finalizar el programa.";
                    break;
                case "Problema creación":
                    string = "No se ha podido crear o escribir el archivo de texto con los resultados. ¿Dispones de permisos? "
                            + " Pulsa cualquier tecla para finalizar el programa.";
                    break;
                case "Problema no csv":
                    string = "No hay ningún archivo CSV en la carpeta. Introdúcelo e inténtalo de nuevo. Pulsa cualquier"
                            + " tecla para finalizar el programa.";
                    break;
                case "Problema conexión":
                    string = "No se ha podido establecer la conexión. Pulsa cualquier tecla para finalizar el programa.";
                    break;
                case "Sesión iniciada":
                    string = "Sesión SSH ya iniciada. Pulsa cualquier tecla para finalizar el programa.";
                    break;
                case "Sesión no iniciada":
                    string = "No existe sesion SSH iniciada. Pulsa cualquier tecla para finalizar el programa";
                    break;
                case "Despedida":
                    string = "Fichero " + NOMBRE_ARCHIVO + " creado. Que tengas un buen día";
                    break;
                case "Conexiones":
                    string = "Creando conexiones y enviando comandos. Esto puede tardar un poco...\n";
                    break;
                case "Faltan datos":
                    string = "Uno de los dominios en el CSV carece de todos los datos necesarios. Recuerda que el"
                            + "formato es \"host;puerto;usuario;clave\". Pulsa cualquier tecla para finalizar el programa.";
                    break;
            }
        }
    }

    /**
     * Distintos comandos posibles.
     */
    public enum COMANDOS {
        UPTIME("uptime"),
        DFH("df -h");

        public String string;

        COMANDOS(String comando) {
            string = comando;
        }
    }

    /**
     * Método común para mostrar un error y finalizar el programa tras pulsar
     * una tecla.
     */
    public static void FinalizarPrograma(String mensaje) {
        System.out.println(mensaje);
        new Scanner(System.in).nextLine();
        System.exit(0);
    }

}
