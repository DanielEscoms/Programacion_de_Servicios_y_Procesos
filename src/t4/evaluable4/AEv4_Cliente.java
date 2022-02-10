package t4.evaluable4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
			
			System.out.println("CLIENTE >>> Recibe opciones para la encriptacion");
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String opcionesEncriptacion = br.readLine();
			
			System.out.print("CLIENTE >>> Indique tipo de " + opcionesEncriptacion + ": ");
			String tipoEncriptacion = teclado.nextLine(); 
			
			System.out.println("CLIENTE >>> Envio de contrasenya y tipo de encriptacion al servidor");
			ObjectOutputStream outObjeto = new ObjectOutputStream(socket.getOutputStream());
			outObjeto.writeObject(contrasenya); // envio de contrasenya
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			pw.write(tipoEncriptacion + "\n"); // envio de tipo de encriptacion
			pw.flush();
			
			System.out.println("CLIENTE >>> Esperando la respuesta de servidor");
			Thread.sleep(1000); // le metemos una pausa de un segundo para obserbar mejor la interaccion del servidor
			
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
