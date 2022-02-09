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
		} catch (IOException e) {
			System.err.println("SERVIDOR >>> Error");
			return;
		}
		
		while (true) {
			try {
				Socket conexion = socketEscucha.accept();
				System.err.println("SERVIDOR >>> Conexion recibida --> Lanza hilo clase Peticion");
				AEv4_Hilo h = new AEv4_Hilo(conexion);
				Thread hilo = new Thread(h);
				hilo.start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
