package t1.evaluable1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AEv1Ej3 {

	public static void main(String[] args) throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		System.out.print("Escribe un numero: ");
		String numerostring = br.readLine();
		int numero = Integer.parseInt(numerostring);
		int suma = 0;
		for (int i = 0; i < numero; i+=2) {
			suma += i;
		}
		System.out.println("Suma: "+ suma);
		isr.close();
		br.close();
	}

}
