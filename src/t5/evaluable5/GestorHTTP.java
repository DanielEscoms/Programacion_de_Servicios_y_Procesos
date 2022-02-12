package t5.evaluable5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.*;

public class GestorHTTP implements HttpHandler {
	
	public String temperaturaActual = "15";
	public String temperaturaTermostato = "15";
	
	@Override    
	public void handle(HttpExchange httpExchange) throws IOException {
		
		System.out.print("Peticion recibida: Tipo ");
		String requestParamValue=null; 
		if("GET".equalsIgnoreCase(httpExchange.getRequestMethod())) { 
			System.out.println("GET");
			requestParamValue = handleGetRequest(httpExchange);
			handleGetResponse(httpExchange,requestParamValue); 
		} else if ("POST".equalsIgnoreCase(httpExchange.getRequestMethod())) { 
			System.out.println("POST");
			requestParamValue = handlePostRequest(httpExchange);
			handlePostResponse(httpExchange,requestParamValue);
		} else {
			System.out.println("DESCONOCIDA");
		}
		
	}
	
	
	//INICIO BLOQUE REQUEST
	
	
	private String handleGetRequest(HttpExchange httpExchange) {
		System.out.println("Recibida URI tipo GET: " + httpExchange.getRequestURI().toString());
		return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
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
	
	//FIN BLOQUE REQUEST
	
	
	//INICIO BLOQUE RESPONSE
	
	private void handleGetResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
		
		System.out.println("El servidor pasa a procesar la peticion GET: " + requestParamValue);
		
		//Ejemplo de respuesta: el servidor devuelve al cliente un HTML simple:
		OutputStream outputStream = httpExchange.getResponseBody();
        String htmlResponse = "<html><body><h1>Hola " + requestParamValue + "</h1></body></html>";
		httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
        System.out.println("Devuelve respuesta HTML: " + htmlResponse);
	}
	
	private void handlePostResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
		
		System.out.println("El servidor pasa a procesar el body de la peticion POST: " + requestParamValue);
		
		//Opcion 1: si queremos que el servidor devuelva al cliente un HTML:
		//OutputStream outputStream = httpExchange.getResponseBody();
		//String htmlResponse = "Parametro/s POST: " + requestParamValue + " -> Se procesara por parte del servidor";
        //outputStream.write(htmlResponse.getBytes());
        //outputStream.flush();
        //outputStream.close();
        //System.out.println("Devuelve respuesta HTML: " + htmlResponse);
		//httpExchange.sendResponseHeaders(200, htmlResponse.length());
		//System.out.println("Devuelve respuesta HTML: " + htmlResponse);
		
		//Opcion 2: el servidor devuelve al cliente un codigo de ok pero sin contenido HTML
		httpExchange.sendResponseHeaders(204, -1);
		System.out.println("El servidor devuelve codigo 204");
		
	}
	
	//FIN BLOQUE RESPONSE
}