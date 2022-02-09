package t4.evaluable4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AEv4_Servidor {

	public static void main(String[] args) {
		
		System.err.println("SERVIDOR >>> Arranca el servidor, espera peticion");
		ServerSocket socketEscucha = null;
		
		try {
			socketEscucha = new ServerSocket(1234);
			// aparece warning porque nunca se cierra la conexión pero de eso se trata ya que es un bucle infinito
			// recreando el funcionamiento de un servidor real que siempre está a la escucha de nuevos clientes
		} catch (IOException e) {
			System.err.println("SERVIDOR >>> Error");
			return;
		}
		
		while (true) {
			try { // el servidor esta escuhando y cuando se recibe la conexion por parte d eun cliente se crea un
					// un hilo para este y sigue escuchando el servidor en un bucle infinito
				Socket conexion = socketEscucha.accept();
				System.err.println("SERVIDOR >>> Conexion recibida --> Lanza hilo clase Peticion");
				AEv4_Hilo h = new AEv4_Hilo(conexion);
				Thread hilo = new Thread(h);
				hilo.start();
				
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("SERVIDOR >>> Error");
			}
		}
	}
}
