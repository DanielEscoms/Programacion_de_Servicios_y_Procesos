package examen.ev1ra;

import java.util.ArrayList;

public class Subproceso implements Runnable {

	static String[] colores = { "amarillo", "verde", "rojo", "azul", "naranja" };
	static ArrayList<String> coloresLista = new ArrayList<String>();

	public static void anyadirColor(String nombrePersona, ArrayList<String> coloresLista) {
		int numRandom = (int) Math.round(Math.random() * 4);
		String colorRandom = colores[numRandom];
		coloresLista.add(colorRandom);
		System.out.println("La " + nombrePersona + " ha anyadido el color " + colorRandom);
	}

	public static void main(String[] args) {

		int numeroPersonas;

		if (args.length != 0) {
			numeroPersonas = Integer.parseInt(args[0]);
		} else {
			numeroPersonas = 100;
		}

		Subproceso s = new Subproceso();
		Thread t;
		for (int i = 1; i <= numeroPersonas; i++) {
			t = new Thread(s);
			t.setName("Persona" + i);
			t.start();

		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.err.println("El numero total de personas es: " + numeroPersonas);
		System.err.println("El tamanyo de la lista es: " + coloresLista.size());
	}

	@Override
	public void run() {
		String nombrePersona = Thread.currentThread().getName();
		anyadirColor(nombrePersona, coloresLista);
	}
}
