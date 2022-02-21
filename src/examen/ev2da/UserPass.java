package examen.ev2da;

import java.io.Serializable;

public class UserPass implements Serializable{
	
	private static final long serialVersionUID = 1L; 
	String usuario, contrasenya;
	
	public UserPass() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserPass(String usuario, String contrasenya) {
		super();
		this.usuario = usuario;
		this.contrasenya = contrasenya;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getContrasenya() {
		return contrasenya;
	}
	
	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}
	
	@Override
	public String toString() {
		return "UserPass [usuario=" + usuario + ", contrasenya=" + contrasenya + "]";
	}
}
