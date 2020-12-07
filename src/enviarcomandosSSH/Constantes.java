package enviarcomandosSSH;

import java.util.Scanner;

public class Constantes {

    /**
     * Nombre del archivo que albergar� los resultados.
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
        PROBLEMA_CREACI�N("Problema creaci�n"),
        PROBLEMA_NO_CSV("Problema no csv"),
        PROBLEMA_CONEXI�N("Problema conexi�n"),
        SESI�N_INICIADA("Sesi�n iniciada"),
        SESI�N_NO_INICIADA("Sesi�n no iniciada"),
        CONEXIONES("Conexiones"),
        DESPEDIDA("Despedida"),
        FALTAN_DATOS("Faltan datos");

        public String string;

        CONSOLA(String comando) {
            switch (comando) {
                case "Saludo":
                    string = "Bienvenido al programa para comprobar la salud de los servidores. Pulsa"
                            + " cualquier tecla si tu archivo CSV ya est� en la carpeta. Si hay varios archivos CSV, se"
                            + " utilizar� el primero por orden alfab�tico. El resultado del an�lisis se mostrar� en el fichero "
                            + NOMBRE_ARCHIVO + ".";
                    break;
                case "Problema lectura":
                    string = "No se ha podido leer el archivo CSV. Pulsa cualquier tecla para finalizar el programa.";
                    break;
                case "Problema creaci�n":
                    string = "No se ha podido crear o escribir el archivo de texto con los resultados. �Dispones de permisos? "
                            + " Pulsa cualquier tecla para finalizar el programa.";
                    break;
                case "Problema no csv":
                    string = "No hay ning�n archivo CSV en la carpeta. Introd�celo e int�ntalo de nuevo. Pulsa cualquier"
                            + " tecla para finalizar el programa.";
                    break;
                case "Problema conexi�n":
                    string = "No se ha podido establecer la conexi�n. Pulsa cualquier tecla para finalizar el programa.";
                    break;
                case "Sesi�n iniciada":
                    string = "Sesi�n SSH ya iniciada. Pulsa cualquier tecla para finalizar el programa.";
                    break;
                case "Sesi�n no iniciada":
                    string = "No existe sesion SSH iniciada. Pulsa cualquier tecla para finalizar el programa";
                    break;
                case "Despedida":
                    string = "Fichero " + NOMBRE_ARCHIVO + " creado. Que tengas un buen d�a";
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
     * M�todo com�n para mostrar un error y finalizar el programa tras pulsar
     * una tecla.
     */
    public static void FinalizarPrograma(String mensaje) {
        System.out.println(mensaje);
        new Scanner(System.in).nextLine();
        System.exit(0);
    }

}
