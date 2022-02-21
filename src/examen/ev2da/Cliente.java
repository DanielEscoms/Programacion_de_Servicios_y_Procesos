package examen.ev2da;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;



public class Cliente {

	public static void main(String[] args) {
		
		Scanner teclado = new Scanner(System.in);
		System.out.println("CLIENTE >>> Arranca cliente.");
		try {
			Socket socket = new Socket("localhost",1234);
		
			System.out.print("CLIENTE >>> Escriba el nombre de usuario: ");
			String nombreUsuario = teclado.nextLine(); 
			System.out.print("CLIENTE >>> Escriba la contrasenya: ");
			String contrasenya = teclado.nextLine();
			
			ObjectOutputStream outputObjeto = new ObjectOutputStream(socket.getOutputStream());
			UserPass userPass = new UserPass(nombreUsuario, contrasenya);
			outputObjeto.writeObject(userPass);
			System.out.println("CLIENTE >>> Envio de usuario y contrasenya al servidor");
			
			System.out.println("CLIENTE >>> Recibe mensaje de autorizacion");
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String mensajeAutorizacion = br.readLine();
			System.out.println("CLIENTE >>> Recibe mensaje de autorizacion " + mensajeAutorizacion);
			
			if (mensajeAutorizacion.equals("200 OK")) {
				System.out.print("CLIENTE >>> Escriba el numero de lineas de texto: ");
				String numLineasString = teclado.nextLine(); 
				
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os);
				pw.write(numLineasString + "\n"); // envio de numLineas
				System.out.println("CLIENTE >>> Envio de numero de lineas: " + numLineasString);
				pw.flush();
				
				int numLineas = Integer.parseInt(numLineasString);
				
				
				String mensajePreparado = br.readLine();
				System.out.println("CLIENTE >>> Recibe mensaje de: "+ mensajePreparado);
				
				if (mensajePreparado.equals("PREPARADO")) {
					for (int i=1; i<=numLineas; i++) {
						System.out.print("CLIENTE >>> Escriba la linea numero "+i);
						String linea = teclado.nextLine();
						pw.write(linea + "\n"); // envio de numLineas
						System.out.println("CLIENTE >>> Envio de la linea " +i +": " + linea);
						pw.flush();
					}
					
					pw.write("FIN CLIENTE\n"); // envio de FIN CLIENTE
					System.err.println("CLIENTE >>> Envio de FIN CLIENTE");
					pw.flush();
					
					
					String finServidor = br.readLine();
					System.out.println("CLIENTE >>> Recibe mensaje de servidor: " + finServidor);
					
					teclado.close();
					System.out.println("CLIENTE >>> Finaliza la conexion");
					socket.close();
				}
				else {
					teclado.close();
					System.out.println("CLIENTE >>> Fin del cliente"); //diferente a PREPARADO
					socket.close();
				}
			}
			else if (mensajeAutorizacion.equals("ERROR")){
				teclado.close();
				System.out.println("CLIENTE >>> Fin del cliente");//no ok
				socket.close();
			}
			
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE >>> Error");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE >>> Error");
			e.printStackTrace();
		}
	}

}
