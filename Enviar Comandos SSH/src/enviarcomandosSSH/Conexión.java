package enviarcomandosSSH;

import com.jcraft.jsch.JSchException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Encargada de conectar con los par�metros que recibe y escribir los resultados
 * en el archivo de texto.
 *
 * @author Aitor
 */
public class Conexi�n {

    /**
     * Crea una nueva conexi�n con los par�metros indicados, ejecuta los comandos y escribe los resultados.
     *
     * @param usuario Nombre de usuario.
     * @param clave Contrase�a.
     * @param host Host a conectar.
     * @param puerto Puerto del Host.
     *
     * @throws JSchException Cualquier error al establecer conexi�n SSH.
     * @throws IllegalAccessException Indica que ya existe una conexi�n SSH
     * establecida.
     * @throws IOException Error de entrada-salida al escribir en el fichero
     */
    public Conexi�n(String host, int puerto, String usuario, String clave)
            throws JSchException, IllegalAccessException {
        NubeMediaSSH sshConnector = new NubeMediaSSH();
        String[] resultados = ObtenerStrings(sshConnector, host, puerto, usuario, clave);
        EscribirResultados(host, resultados);
    }

    private String[] ObtenerStrings(NubeMediaSSH sshConnector, String host, int puerto, String usuario, String clave) throws JSchException, IllegalAccessException {
        sshConnector.connect(host, puerto, usuario, clave);
        try {
            String uptime = sshConnector.ejecutarComando(Constantes.COMANDOS.UPTIME.string).toUpperCase();
            String df = sshConnector.ejecutarComando(Constantes.COMANDOS.DFH.string).toUpperCase();
            sshConnector.disconnect();
            String[] resultados = {uptime, df};
            return resultados;
        } catch (IOException e) {
            Constantes.FinalizarPrograma(Constantes.CONSOLA.PROBLEMA_CONEXI�N.string);
            return null;
        }
    }

    private void EscribirResultados(String host, String[] resultados) {
        try {
            FileWriter fw = new FileWriter(Constantes.NOMBRE_ARCHIVO, false);
            fw.write("An�lisis de " + host
                    + System.lineSeparator() + Constantes.COMANDOS.UPTIME.string + " ----- " + resultados[0]
                    + System.lineSeparator() + Constantes.COMANDOS.DFH.string + " ----- " + resultados[1]);
            fw.close();
        } catch (IOException e) {
            Constantes.FinalizarPrograma(Constantes.CONSOLA.PROBLEMA_CREACI�N.string);
        }
    }

}
