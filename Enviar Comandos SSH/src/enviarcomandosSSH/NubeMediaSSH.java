package enviarcomandosSSH;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Clase encargada de establecer conexi�n y ejecutar comandos SSH.
 */
public class NubeMediaSSH {

    /**
     * Constante que representa un enter.
     */
    private static final String TECLA_ENTER = "n";
    
    /**
     * Sesi�n SSH establecida.
     */
    private Session session;

    /**
     * Establece una conexi�n SSH.
     *
     * @param usuario Nombre de usuario.
     * @param clave Contrase�a.
     * @param host Host a conectar.
     * @param puerto Puerto del Host.
     *
     * @throws JSchException Cualquier error al establecer conexi�n SSH.
     * @throws IllegalAccessException Indica que ya existe una conexi�n SSH
     * establecida.
     */
    public void connect(String host, int puerto, String usuario, String clave)
            throws JSchException, IllegalAccessException {
        if (this.session == null || !this.session.isConnected()) {
            JSch jsch = new JSch();

            session = jsch.getSession(usuario, host, puerto);
            session.setPassword(clave);

            // Parametro para no que no se requiera validaci�n de la key por parte del usuario.
            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();
        } else {
            Constantes.FinalizarPrograma(Constantes.CONSOLA.SESI�N_INICIADA.string);
        }
    }

    /**
     * Ejecuta un comando SSH y devuelve un string.
     *
     * @param comando Comando SSH a ejecutar.
     *
     * @return El string resultado de la ejecuci�n de los comandos.
     *
     * @throws IllegalAccessException Excepci�n lanzada cuando no hay conexi�n
     * establecida.
     * @throws JSchException Excepci�n lanzada por alg�n error en la ejecuci�n
     * del comando SSH.
     * @throws IOException Excepci�n al leer el texto arrojado luego de la
     * ejecuci�n del comando SSH.
     */
    public final String ejecutarComando(String comando)
            throws IllegalAccessException, JSchException, IOException {
        if (session != null && session.isConnected()) {

            // Abrimos un canal SSH.
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            // Ejecutamos el comando.
            channelExec.setCommand(comando);
            channelExec.connect();

            // Obtenemos el texto impreso en la consola.
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String linea;

            while ((linea = reader.readLine()) != null) {
                builder.append(linea);
                builder.append(TECLA_ENTER);
            }

            // Cerramos el canal SSH.
            channelExec.disconnect();

            // Retornamos el texto impreso en la consola.
            return builder.toString();
        } else {
            Constantes.FinalizarPrograma(Constantes.CONSOLA.SESI�N_NO_INICIADA.string);
            return null;
        }
    }

    /**
     * Cierra la sesi�n SSH.
     */
    public final void disconnect() {
        this.session.disconnect();
    }
}
