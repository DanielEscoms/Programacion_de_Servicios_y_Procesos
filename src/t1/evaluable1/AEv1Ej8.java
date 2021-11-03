package t1.evaluable1;

import java.util.Scanner;

public class AEv1Ej8 {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		int numero1 = 0;
		int numero2 = 0;
		int numeroMenor = 0;
		int numeroMayor = 0;
		System.out.print("Escriba un numero: ");
		numero1 = teclado.nextInt();
		System.out.print("Escriba un numero: ");
		numero2 = teclado.nextInt();
		long inicio = System.currentTimeMillis();
		if (numero1<numero2) {
			numeroMenor = numero1;
			numeroMayor = numero2;
		}else {
			numeroMenor = numero2;
			numeroMayor = numero1;
		}
		
		for(int i = numeroMenor; i<=numeroMayor; i++) {
			if (esPrimo(i)) {
				System.out.println("El numero " + i + " es primo.");
			}else {
				System.out.println("El numero " + i + " no es primo.");
			}
		}
		teclado.close();
        long fin = System.currentTimeMillis();
        double tiempo = (double) ((fin - inicio)/1d);
        System.out.println(tiempo +" milisegundos");

	}
	public static boolean esPrimo(int numero){
		  int contador = 2;
		  boolean primo=true;
		  while ((primo) && (contador!=numero)){
		    if (numero % contador == 0)
		      primo = false;
		    contador++;
		  }
		  return primo;
		}
}
