package t4.evaluable4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AEv4_Hilo implements Runnable {

	Socket socket;

	public AEv4_Hilo(Socket socket) {
		this.socket = socket;
	}

	/*
	 * metodo encriptacion() Sustituye cada carácter del string de contrasenyaPlana
	 * por el carácter ASCII inmediatamente posterior de la tabla ASCII. Si el
	 * carácter sustituto fuera un carácter ASCII no imprimible, utiliza el carácter
	 * asterisco (*) en su lugar. Entrada: Objeto contrasenya Salida: Objeto
	 * contrasenya (actualizado con la contrasenyaEncriptada calculada)
	 */
	public AEv4_Contrasenya encriptacion(AEv4_Contrasenya contrasenya, String tipoEncriptacion) {

		String contrasenyaPlana = contrasenya.getContrasenyaPlana();
		String contrasenyaEncriptada = "";
		if (tipoEncriptacion.equals("1")) {
			for (int i = 0; i < contrasenyaPlana.length(); i++) {
				char caracter = contrasenyaPlana.charAt(i);
				int asciiCaracter = (int) caracter;
				int asciiCaracterSiguiente = asciiCaracter + 1;

				// Ahora se procesa si el caracter es no imprimible
				if (asciiCaracterSiguiente < 32 || asciiCaracterSiguiente > 126) {
					asciiCaracterSiguiente = 42; // Numero ascii del caracter asterisco (*)
				}

				contrasenyaEncriptada += (char) asciiCaracterSiguiente; // Se encadena los caracteres siguientes al
																		// pasarlos a tipo (char)
			}
		} else if (tipoEncriptacion.equals("2")) {
			try {
				// para escribir la contrasenya en MD5
				MessageDigest mesDig = MessageDigest.getInstance("MD5");
				byte[] arrayContr = mesDig.digest(contrasenya.getContrasenyaPlana().getBytes(StandardCharsets.UTF_8));
				StringBuilder sb = new StringBuilder();
				for (byte b : arrayContr) {
					sb.append(String.format("%02x", b));
				}

				contrasenyaEncriptada = sb.toString();

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		contrasenya.setContrasenyaEncriptada(contrasenyaEncriptada);
		return contrasenya;
	}

	public void run() {

		try {
			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName()
					+ " >>> Envia a cliente el objeto contrasenya vacio");
			ObjectOutputStream outputObjeto = new ObjectOutputStream(socket.getOutputStream());

			AEv4_Contrasenya contrasenya = new AEv4_Contrasenya("", "");
			outputObjeto.writeObject(contrasenya);

			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Envio opciones de contrasenya al cliente");
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.write("Encriptacion (1 = poco segura, 2 = MD5)\n");
			pw.flush();

			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Esperando la respuesta de cliente");
			ObjectInputStream inputObjeto = new ObjectInputStream(socket.getInputStream());

			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Recibe objeto contrasenya completado");
			AEv4_Contrasenya contrasenyaPlanaActualizada = (AEv4_Contrasenya) inputObjeto.readObject();
			
			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Recibe el tipo de encriptacion elegido por el cliente");
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String tipoEncriptacion = br.readLine();

			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Realiza la encriptacion");
			AEv4_Contrasenya contrasenyaEncriptada = encriptacion(contrasenyaPlanaActualizada, tipoEncriptacion);
			// Se puede modificar el objeto contrasenya pero mejor crear otro objeto con
			// otro nombre para saber cuando esta la encriptacion realizada.

			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Devuelve contrasenyas a cliente");
			outputObjeto.writeObject(contrasenyaEncriptada);

			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Fin del hilo");
			System.err.println("SERVIDOR >>> Espera peticion...");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Error.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Error.");
		}
	}
}
