package t5.evaluable5;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.net.httpserver.*;

public class Servidor {

	public String mensaje = "Cuerpo del mensaje.";
	public String asunto = "AVERIA";
	public String host_email = "smtp.gmail.com";
	public String port_email = "587";
	public String[] email_destino = {"mantenimientoinvernalia@gmail.com", "megustaelfresquito@gmail.com"};
	public String[] anexo = {"anexo/Imagen.jpg", "anexo/AVERIA.pdf"};
	
	
	public void envioMail(String email_remitente, String email_remitente_pass) throws UnsupportedEncodingException, MessagingException{
		
		System.out.println("Envio de correo");
		System.out.println(" > Remitente: " + email_remitente);
		for (int i = 0; i < email_destino.length; i++) {
			System.out.println(" > Destino: " + (i + 1) + ": " + email_destino[i]);
		}
		System.out.println(" > Asunto: " + asunto);
		System.out.println(" > Mensaje: " + mensaje);
		for (int i = 0; i < anexo.length; i++) {
			System.out.println(" > Anexo: " + (i + 1) + ": " + anexo[i]);
		}
		
		Properties props= System.getProperties();
		props.put("mail.smtp.host", host_email);
		props.put("mail.smtp.user", email_remitente);
		props.put("mail.smtp.clave", email_remitente_pass);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port_email);
		
		Session session= Session.getDefaultInstance(props);
		
		MimeMessage message= new MimeMessage(session);
		message.setFrom(new InternetAddress(email_remitente));
		for (int i = 0; i < email_destino.length; i++) {
			message.addRecipients(Message.RecipientType.TO, email_destino[i]);
		}
		message.setSubject(asunto);
		
		Multipart multipart= new MimeMultipart();
		
		BodyPart messageBodyPart1= new MimeBodyPart();
		messageBodyPart1.setText(mensaje);
		multipart.addBodyPart(messageBodyPart1);
		
		for (int i = 0; i < anexo.length; i++) {
			BodyPart messageBodyPartAnexo= new MimeBodyPart();
			DataSource src= new FileDataSource(anexo[i]);
			messageBodyPartAnexo.setDataHandler(new DataHandler(src));
			messageBodyPartAnexo.setFileName(anexo[i]);
			multipart.addBodyPart(messageBodyPartAnexo);
		}
		
		message.setContent(multipart);
		
		Transport transport= session.getTransport("smtp");
		transport.connect(host_email, email_remitente, email_remitente_pass);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
	
	public static void main(String[] args) throws Exception {
		
		String host = "localhost"; // 127.0.0.1
		int puerto = 7777;
		InetSocketAddress direccionTCPIP = new InetSocketAddress(host, puerto);
		int backlog = 0;
		HttpServer servidor = HttpServer.create(direccionTCPIP, backlog);
		
		GestorHTTP gestorHTTP = new GestorHTTP();
		String rutaRespuesta = "/estufa";
		servidor.createContext(rutaRespuesta, gestorHTTP);
		
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		servidor.setExecutor(threadPoolExecutor);
		
		servidor.start();
		System.out.println("Servidor arranca en el puerto " + puerto);
	}
}