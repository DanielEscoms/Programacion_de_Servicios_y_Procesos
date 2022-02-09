package t4.evaluable4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AEv4_Hilo implements Runnable {

	Socket socket;

	public AEv4_Hilo(Socket socket) {
		this.socket = socket;
	}
	
	/* metodo encriptacion() Sustituye cada carácter del string de contrasenyaPlana por el carácter ASCII
	inmediatamente posterior de la tabla ASCII. Si el carácter sustituto fuera un carácter ASCII no
	imprimible, utiliza el carácter asterisco (*) en su lugar.
	Entrada: Objeto contrasenya
	Salida: Objeto contrasenya (actualizado con la contrasenyaEncriptada calculada)
	*/
	public AEv4_Contrasenya encriptacion(AEv4_Contrasenya contrasenya) {

		String contrasenyaPlana = contrasenya.getContrasenyaPlana();
		String contrasenyaEncriptada = "";

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
		contrasenya.setContrasenyaEncriptada(contrasenyaEncriptada);
		return contrasenya;
	}

	public void run() {

		try {
			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Envia a cliente el objeto contrasenya vacio");
			ObjectOutputStream outputObjeto = new ObjectOutputStream(socket.getOutputStream());

			AEv4_Contrasenya contrasenya = new AEv4_Contrasenya("", "");
			outputObjeto.writeObject(contrasenya);

			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Esperando la respuesta de cliente");
			ObjectInputStream inputObjeto = new ObjectInputStream(socket.getInputStream());

			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Recibe objeto contrasenya completado");
			AEv4_Contrasenya contrasenyaPlanaActualizada = (AEv4_Contrasenya) inputObjeto.readObject();

			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Realiza la encriptacion");
			AEv4_Contrasenya contrasenyaEncriptada = encriptacion(contrasenyaPlanaActualizada);
			// Se puede modificar el objeto contrasenya pero mejor crear otro objeto con otro nombre para saber cuando esta la encriptacion realizada.

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
