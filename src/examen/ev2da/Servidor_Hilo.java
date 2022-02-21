package examen.ev2da;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Servidor_Hilo implements Runnable{

	Socket socket;
	private String usuarios_autorizados = "Usuarios_autorizados.txt"; //fichero
	private String contrasenyas_autorizadas = "Contrasenyas_autorizadas.txt"; //fichero2
	public String fichero3 = "contenido.txt";
	private Boolean verificado = false;
	

	public Servidor_Hilo(Socket socket) {
		this.socket = socket;
	}
	
	public Boolean compruebaVerificacion(UserPass userPass, String fichero, String fichero2) {
		
		File f = new File(fichero);
		File f2 = new File(fichero2);
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			FileReader fr2 = new FileReader(f2);
			BufferedReader br2 = new BufferedReader(fr2);
			String linea2 = br2.readLine();
			while (linea != null) {
				if(linea.equals(userPass.getUsuario()) && linea2.equals(userPass.getContrasenya())) {
					verificado = true;
					break;
				}
				linea = br.readLine();
				linea2 = br.readLine();
			}
			br.close();
			fr.close();
			br2.close();
			fr2.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			
		}
		return verificado;
	}
	
	public void run() {

		System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Esperando el objeto de cliente");
		
		try {
			
			ObjectInputStream inputObjeto = new ObjectInputStream(socket.getInputStream());

			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Recibe objeto userPass rellenado");
			UserPass userPass = (UserPass) inputObjeto.readObject();
			
			Boolean siNo = compruebaVerificacion(userPass, usuarios_autorizados, contrasenyas_autorizadas);
			
			if(siNo) {
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os);
				pw.write("200 OK\n"); // envio de OK
				System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Envio de mensaje de autorizacion");
				pw.flush();
				
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String numLineasString = br.readLine();
				int numLineas = Integer.parseInt(numLineasString);
				System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Recibe numero de lineas: " + numLineas);
				
				File f3 = new File(fichero3);
				System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Crea el fichero: "+ fichero3);
				
				pw.write("PREPARADO\n"); // envio de PREPARADO
				System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Envio de PREPARADO");
				pw.flush();
				
				FileWriter fw3 = new FileWriter(f3);
				BufferedWriter bw3 = new BufferedWriter(fw3);
				
				for (int i=1; i<=numLineas; i++) {
					String linea = br.readLine(); //recibo lineas
					System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Recibe la linea numero "+i);
					System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Contenido linea: "+linea);
					
					bw3.write(linea);
					System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Guardada en: " + fichero3);
					bw3.newLine();
				}
				bw3.close();
				fw3.close();
				
				
				String mensajeFinCliente = br.readLine(); //recibo mensaje fin cliente
				System.err.print("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Recibe mensaje de cliente: " + mensajeFinCliente);
				
				pw.write("FIN SERVIDOR\n"); // envio de FIN SERVIDOR
				System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Envio de FIN SERVIDOR");
				
				pw.flush();
				socket.close();
				
			} else {
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os);
				pw.write("ERROR\n"); // envio de ERROR
				pw.flush();
				System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Fin de la conexion");
				socket.close();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Error.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("HILO SERVIDOR " + Thread.currentThread().getName() + " >>> Error.");
		}
	}

}
