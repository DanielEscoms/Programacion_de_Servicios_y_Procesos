package t4.evaluable4;

import java.io.Serializable;

public class AEv4_Contrasenya implements Serializable{ // He creado la siguiente clase con el nombre de   
								// los atributos, luego clic derecho, Source y los distintos campos,
								// Constructor bacío, constructor con atributos, getters y setters y toString().
	
	// Add generated serial version ID,  se añade este texto auto para que no de error en Serializar.
	private static final long serialVersionUID = 1L; 
	String contrasenyaPlana, contrasenyaEncriptada;

	public AEv4_Contrasenya() {
		super();
	}

	public AEv4_Contrasenya(String contrasenyaPlana, String contrasenyaEncriptada) {
		super();
		this.contrasenyaPlana = contrasenyaPlana;
		this.contrasenyaEncriptada = contrasenyaEncriptada;
	}

	public String getContrasenyaPlana() {
		return contrasenyaPlana;
	}

	public void setContrasenyaPlana(String contrasenyaPlana) {
		this.contrasenyaPlana = contrasenyaPlana;
	}

	public String getContrasenyaEncriptada() {
		return contrasenyaEncriptada;
	}

	public void setContrasenyaEncriptada(String contrasenyaEncriptada) {
		this.contrasenyaEncriptada = contrasenyaEncriptada;
	}

	@Override
	public String toString() {
		return "AEv4_Contrasenya [contrasenyaPlana=" + contrasenyaPlana + ", contrasenyaEncriptada="
				+ contrasenyaEncriptada + "]";
	}
}
