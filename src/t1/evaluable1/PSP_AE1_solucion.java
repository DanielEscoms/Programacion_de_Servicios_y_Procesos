package t1.evaluable1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class PSP_AE1_solucion {

	public static void sayHello() {
		System.out.println("Hola mundo");
	}
	
	public static void companyeros() {
		String arrayNombres[] = {"nombre1","nombre2","nombre3","nombre4","nombre5","nombre6"};
		System.out.println("Array de nombres: ");
		for (String nombre : arrayNombres)
			System.out.println(nombre);
		ArrayList<String> listaNombres = new ArrayList<String>()
			{{add("nombre1");add("nombre2");add("nombre3");add("nombre4");add("nombre5");add("nombre6");}};
		System.out.println("\nLista de nombres: ");
		for (String nombre : listaNombres)
			System.out.println(nombre);
	}
	
	public static void sumaPares(int numeroFin) {
		int suma = 0;
		for (int i = 0; i <= numeroFin; i++) {
			if (i%2 == 0) {
				suma = suma + i;
			}
		}
		System.out.println("Suma de pares de 0 a " + numeroFin + ": " + suma);
	}
	
	public static void factorial(int numeroFin) {
		long factorial = 1;
		for (int i = numeroFin; i >= 1; i--) {
			factorial = factorial * i;
		}
		System.out.println("Factorial de " + numeroFin + ": "+ factorial);
	}
	
	public static void buscaMayor() {
		int arrayInt[] = {3, 4, 7, 8, 9, 10, 1, 5, 2, 6};
		int numMayor = 0;
		for (int i = 0; i < arrayInt.length; i++) {
			System.out.print(arrayInt[i] + " ");
			if (arrayInt[i] > numMayor) {
				numMayor = arrayInt[i];
			}
		}
		//Tambien esta la posibilidad de utilizar el metodo sort de Arrays o Collections:
//		Arrays.sort(arrayInt);
//		numMayor = arrayInt[arrayInt.length-1];
		System.out.println("\nMayor: " + numMayor);
	}
	
	public static void ordenaSuma() {
		Scanner teclado = new Scanner(System.in);
		int arrayInt[] = new int[5];
		int numeroActual = 0;
		int suma = 0;
		for (int i = 0; i < 5; i++) {
			System.out.print("Numero " + (i + 1) + " de 5: ");
			numeroActual = teclado.nextInt();
			arrayInt[i] = numeroActual;
			suma = suma + numeroActual;
		}
		Arrays.sort(arrayInt);  //Ordeno primero los numeros de menor a mayor, aunque esto no era necesario
		for (int i = 4; i >= 0; i--) {
			System.out.println(arrayInt[i]);
		}
		System.out.println("Suma: " + suma);
	}
	
	public static void experiencia() {
		Scanner teclado = new Scanner(System.in);
		System.out.print("Nombre: ");
		String nombre = teclado.nextLine();
		System.out.print("Experiencia: ");
		int anyosExperiencia = Integer.parseInt(teclado.nextLine());
		if (anyosExperiencia < 1)
			System.out.println(nombre + " Desarrollador Junior L1 � 15000-18000");
		else if (anyosExperiencia >= 1 && anyosExperiencia < 3)
			System.out.println(nombre + " Desarrollador Junior L2 � 18000-22000");
		else if (anyosExperiencia >= 3 && anyosExperiencia < 5)
			System.out.println(nombre + " Desarrollador Senior L1 � 22000-28000");
		else if (anyosExperiencia >= 5 && anyosExperiencia <= 8)
			System.out.println(nombre + " Desarrollador Senior L2 � 28000-36000");
		else
			System.out.println(nombre + " Analista / Arquitecto. Salario a convenir en base a rol");
	}
	
	public static void intervaloPrimos() {
		Scanner teclado = new Scanner(System.in);
		System.out.print("N�mero 1: ");
		int numero1 = teclado.nextInt();
		System.out.print("N�mero 2: ");
		int numero2 = teclado.nextInt();
		boolean esPrimo;
		long tiempoInicio = System.nanoTime();
		for (int i = numero1; i <= numero2; i++) {
			if (i == 0 || i == 1) {
				esPrimo = false;
			} else {
				esPrimo = true;
				for (int j = 2; j <= i/2; j++) {
					if ((i % j) == 0) {
						esPrimo = false;
						break;
					}
				}	
			}
			if (!esPrimo)
				System.out.println("N�mero " + i + " -> NO es primo");
			else
				System.out.println("N�mero " + i + " -> ES primo");
		}
		long tiempoFin = System.nanoTime();
		long duracion = (tiempoFin - tiempoInicio)/1000000;  //milisegundos
		System.out.println("Tiempo ejecucion: " + duracion + " ms");
	}
	
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		System.out.print("Indicar ejercicio (1 a 8): ");
		int seleccion = teclado.nextInt();
		switch (seleccion) {
			case 1:
				sayHello();
				break;
			case 2:
				companyeros();
				break;
			case 3:
				sumaPares(10);
				break;
			case 4:
				factorial(15);
				break;
			case 5:
				buscaMayor();
				break;
			case 6:
				ordenaSuma();
				break;
			case 7:
				experiencia();
				break;
			case 8:
				intervaloPrimos();
				break;	
			default:
				break;		
		}
	}

}
