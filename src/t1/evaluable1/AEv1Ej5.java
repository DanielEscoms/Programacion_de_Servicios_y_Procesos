package t1.evaluable1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AEv1Ej5 {

	public static void main(String[] args) throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		ArrayList<Integer> arraynumeros = new ArrayList<Integer>();
		String numerostring = null;
		int numero = 1;
		while (numero != 0) {
			System.out.print("Escribe un numero: ");
			numerostring = br.readLine();
			numero = Integer.parseInt(numerostring);
			if (numero != 0) arraynumeros.add(numero);
		}
		int nummax = 0;
		for (int numeroi : arraynumeros) {
			if (nummax < numeroi) {
				nummax = numeroi;
			}
		}

		System.out.println("El número mayor es: " + nummax);
		isr.close();
		br.close();
	}
}