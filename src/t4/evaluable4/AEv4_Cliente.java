package t4.evaluable4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class AEv4_Cliente {

	public static void main(String[] args) {
		
		Scanner teclado = new Scanner(System.in);
		System.out.print("Introduzca IP: ");
		String host = teclado.nextLine();
		System.out.println("CLIENTE >>> Arranca cliente -> esperando mensaje del servidor");
		
		try {
			Socket socket = new Socket(host,1234);
			ObjectInputStream inObjeto = new ObjectInputStream(socket.getInputStream());
			System.out.println("CLIENTE >>> Recibe contrasenya vacia del servidor");
			AEv4_Contrasenya contrasenya = (AEv4_Contrasenya) inObjeto.readObject();
			
			System.out.print("CLIENTE >>> Introduzca la contrasenya que desee");
			String stringContrasenya = teclado.nextLine(); 
			contrasenya.setContrasenyaPlana(stringContrasenya);
			
			System.out.println("CLIENTE >>> Envio de contrasenya al servidor");
			ObjectOutputStream outObjeto = new ObjectOutputStream(socket.getOutputStream());
			outObjeto.writeObject(contrasenya);
			
			System.out.println("CLIENTE >>> Esperando la respuesta de servidor");
			AEv4_Contrasenya contrasenyaEncriptada = (AEv4_Contrasenya) inObjeto.readObject();
			System.out.println("CLIENTE >>> Recibida contrasenya encriptada: " + contrasenyaEncriptada.toString());
			
			socket.close();
			teclado.close();
			
			System.out.println("CLIENTE >>> Fin del cliente");
			
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE >>> Error");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE >>> Error");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("CLIENTE >>> Error");
			e.printStackTrace();
		}
	}
}
