package t1.evaluable1;

import java.util.Scanner;

public class AEv1Ej6 {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		int[] arrayNumeros = new int[5];
		int numero = 0;
		for (int i = 0; i < 5; i++) {
			System.out.print("Escribe el nº" + (i + 1) + ": ");
			numero = teclado.nextInt();
			arrayNumeros[i] = numero;
		}
		int suma = 0;
		for (int i = 4; i >= 0; i--) {
			System.out.print(arrayNumeros[i]);
			suma += arrayNumeros[i];
		}

		System.out.println("\nLa suma es: " + suma);
		teclado.close();
	}

}
