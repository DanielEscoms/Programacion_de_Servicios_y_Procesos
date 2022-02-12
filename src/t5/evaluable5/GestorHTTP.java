package t5.evaluable5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.sun.net.httpserver.*;

public class GestorHTTP implements HttpHandler { // tinc temp actual y termostato com daos, al Get les trau per
													// pantalla, si fique post la varia de grau en grau fins aplegar
													// la actual a la del termostato.

	public int temperaturaActual = 15;
	public int temperaturaTermostato = 15;
	public boolean repite = true;

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		System.out.print("Peticion recibida: Tipo ");
		String requestParamValue = null;
		if ("GET".equalsIgnoreCase(httpExchange.getRequestMethod())) {
			System.out.println("GET");
			requestParamValue = handleGetRequest(httpExchange);
			handleGetResponse(httpExchange, requestParamValue);
		} else if ("POST".equalsIgnoreCase(httpExchange.getRequestMethod())) {
			System.out.println("POST");
			requestParamValue = handlePostRequest(httpExchange);
			try {
				handlePostResponse(httpExchange, requestParamValue);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("DESCONOCIDA");
		}

	}

	private String handleGetRequest(HttpExchange httpExchange) {
		System.out.println("Recibida URI tipo GET: " + httpExchange.getRequestURI().toString());
		return httpExchange.getRequestURI().toString().split("\\?")[1];
	}

	private String handlePostRequest(HttpExchange httpExchange) {
		System.out.println("Recibida URI tipo POST: " + httpExchange.getRequestBody().toString());
		InputStream is = httpExchange.getRequestBody();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private void handleGetResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {

		System.out.println("El servidor pasa a procesar la peticion GET: " + requestParamValue);

		if (requestParamValue.equals("temperaturaActual")) {
			// respuesta: el servidor devuelve al cliente un HTML simple:
			OutputStream outputStream = httpExchange.getResponseBody();
			String htmlResponse = "<html><body><h1>Temperatura actual: " + temperaturaActual + " grados Celsius</h1>"
					+ "<h1> Temperatura estufa: " + temperaturaTermostato + " grados Celsius</h1></body></html>";
			httpExchange.sendResponseHeaders(200, htmlResponse.length());
			outputStream.write(htmlResponse.getBytes());
			outputStream.flush();
			outputStream.close();
			System.out.println("Devuelve respuesta HTML: " + htmlResponse);
		}
	}

	private void handlePostResponse(HttpExchange httpExchange, String requestParamValue)
			throws IOException, InterruptedException {

		System.out.println("El servidor pasa a procesar el body de la peticion POST: " + requestParamValue);
		//danielescomspruebas@gmail.com
		// notificarAveria:email_remitente=danielescomspruebas@gmail.com;pass_remitente=prueba1234
		if (requestParamValue.toString().split(":")[0].equals("notificarAveria")) {
			String email_remitente = requestParamValue.split("=")[1].split(";")[0];
			String pass_remitente = requestParamValue.split("=")[1].split("=")[1];
			
			Servidor servidor = new Servidor();
			try {
				servidor.envioMail(email_remitente, pass_remitente);
				System.out.println("\nENVIO REALIZADO CON EXITO");
			} catch (UnsupportedEncodingException | MessagingException e) {
				System.err.println("ERROR EN EL ENVIO");
				e.printStackTrace();
			}
			
		}
		
		else if (requestParamValue.toString().split("=")[0].equals("setTemperatura")) {

			if (temperaturaTermostato != Integer.parseInt(requestParamValue.split("=")[1])) {
				temperaturaTermostato = Integer.parseInt(requestParamValue.split("=")[1]);

				// respuesta: el servidor devuelve al cliente un HTML:
				OutputStream outputStream = httpExchange.getResponseBody();
				String htmlResponse = "<html><body><h1>Temperatura actual: " + temperaturaActual
						+ " grados Celsius</h1>" + "<h1> Temperatura estufa: " + temperaturaTermostato
						+ " grados Celsius</h1></body></html>";
				httpExchange.sendResponseHeaders(200, htmlResponse.length());
				outputStream.write(htmlResponse.getBytes());
				outputStream.flush();
				outputStream.close();
				System.out.println("Devuelve respuesta HTML: " + htmlResponse);
				regularTemperatura();

			} else {
				OutputStream outputStream = httpExchange.getResponseBody();
				String htmlResponse = "<html><body><h1>Temperatura actual: " + temperaturaActual
						+ " grados Celsius</h1>" + "<h1> Temperatura estufa: " + temperaturaTermostato
						+ " grados Celsius</h1></body></html>";
				httpExchange.sendResponseHeaders(200, htmlResponse.length());
				outputStream.write(htmlResponse.getBytes());
				outputStream.flush();
				outputStream.close();
				System.out.println("Devuelve respuesta HTML: " + htmlResponse);
			}

		} else {
			OutputStream outputStream = httpExchange.getResponseBody();
			String htmlResponse = "<html><body><h1>Instruccion incorrecta, se necesita:</h1><h1>setTemperatura=X "
					+ "(X es el valor de a temp, p.ej. 17)</h1><h1>o bien se necesita: notificarAveria:"
					+ "email_remitente=EMAIL;pass_remitente=PASSWORD</h1></body></html>";
			httpExchange.sendResponseHeaders(200, htmlResponse.length());
			outputStream.write(htmlResponse.getBytes());
			outputStream.flush();
			outputStream.close();
			System.out.println("Devuelve respuesta HTML: " + htmlResponse);
		}

	}

	/*
	 * private void handlePostResponse(HttpExchange httpExchange, String
	 * requestParamValue) throws IOException, InterruptedException {
	 * 
	 * System.out.
	 * println("El servidor pasa a procesar el body de la peticion POST: " +
	 * requestParamValue);
	 * 
	 * if (requestParamValue.toString().split("=")[0].equals("setTemperatura")) {
	 * while(repite) { if (temperaturaTermostato !=
	 * Integer.parseInt(requestParamValue.split("=")[1])) { temperaturaTermostato =
	 * Integer.parseInt(requestParamValue.split("=")[1]); regularTemperatura();
	 * //respuesta: el servidor devuelve al cliente un HTML: OutputStream
	 * outputStream = httpExchange.getResponseBody(); String htmlResponse =
	 * "<html><body><h1>Temperatura actual: " + temperaturaActual +
	 * " grados Celsius</h1>" + "<h1> Temperatura estufa: " + temperaturaTermostato
	 * + " grados Celsius</h1></body></html>"; httpExchange.sendResponseHeaders(200,
	 * htmlResponse.length()); outputStream.write(htmlResponse.getBytes());
	 * outputStream.flush(); outputStream.close();
	 * System.out.println("Devuelve respuesta HTML: " + htmlResponse);
	 * Thread.sleep(1000); } else { OutputStream outputStream =
	 * httpExchange.getResponseBody(); String htmlResponse =
	 * "<html><body><h1>Temperatura actual: " + temperaturaActual +
	 * " grados Celsius</h1>" + "<h1> Temperatura estufa: " + temperaturaTermostato
	 * + " grados Celsius</h1></body></html>"; httpExchange.sendResponseHeaders(200,
	 * htmlResponse.length()); outputStream.write(htmlResponse.getBytes());
	 * outputStream.flush(); outputStream.close();
	 * System.out.println("Devuelve respuesta HTML: " + htmlResponse); repite=false;
	 * } } } else { OutputStream outputStream = httpExchange.getResponseBody();
	 * String htmlResponse =
	 * "<html><body><h1>Instruccion incorrecta, se necesita:</h1><h1>setTemperatura=X "
	 * + "(X es el valor de a temp, p.ej. 17)</h1></body></html>";
	 * httpExchange.sendResponseHeaders(200, htmlResponse.length());
	 * outputStream.write(htmlResponse.getBytes()); outputStream.flush();
	 * outputStream.close(); System.out.println("Devuelve respuesta HTML: " +
	 * htmlResponse); }
	 * 
	 * 
	 * }
	 */

	private void regularTemperatura() {
		while (repite) {
			try {
				Thread.sleep(500);
				if (temperaturaActual < temperaturaTermostato) {
					temperaturaActual++;
				} else if (temperaturaActual > temperaturaTermostato) {
					temperaturaActual--;
				} else {
					repite = false;
				}
				System.out.println("\nTemperatura actual: " + temperaturaActual + "\n Temperatura estufa: "
						+ temperaturaTermostato);

			} catch (InterruptedException e) {
				System.out.println("Error en el bucle");
				e.printStackTrace();
			}
		}
	}

	// FIN BLOQUE RESPONSE
}