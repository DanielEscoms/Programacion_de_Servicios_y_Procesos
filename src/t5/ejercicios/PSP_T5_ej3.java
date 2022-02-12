package t5.ejercicios;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
//import java.util.Scanner;

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
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class PSP_T5_ej3 {
	
	public static void envioMail(String mensaje, String asunto, String email_remitente, String email_remitente_pass, String host_email, String port_email, String[] email_destino, String[] anexo) throws UnsupportedEncodingException, MessagingException{
		
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

	public static void main(String[] args) {
		
		System.out.println("Prueba Email");
		
		String strMensaje = "Cuerpo del mensaje.";
		String strAsunto = "Asunto";
		String emailRemitente = "danielescomspruebas@gmail.com";
		/*Scanner teclado = new Scanner(System.in);
		System.out.print("Introducir contrasenya: ");
		String emailRemitentePass = teclado.nextLine();*/
		JPasswordField pwd = new JPasswordField(15);
		JOptionPane.showConfirmDialog(null, pwd, "Introducir contrasenya: ", JOptionPane.OK_CANCEL_OPTION);
		String emailRemitentePass = new String(pwd.getPassword());
		String hostEmail = "smtp.gmail.com";
		String portEmail = "587";
		String[] emailDestino = {"danielescoms56@gmail.com", "daesdo@floridauniversitaria.es"};
		String[] anexo = {"anexo/Imagen.jpg", "anexo/Prueba.txt"};
		
		try {
			envioMail(strMensaje, strAsunto, emailRemitente, emailRemitentePass, hostEmail, portEmail, emailDestino, anexo);
			System.out.println("\nENVIO REALIZADO CON EXITO");
		} catch (UnsupportedEncodingException | MessagingException e) {
			System.err.println("ERROR EN EL ENVIO");
			e.printStackTrace();
		}
		
	}

}
