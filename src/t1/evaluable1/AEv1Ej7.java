package t1.evaluable1;

import java.util.Scanner;

public class AEv1Ej7 {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		String nombre ="";
		System.out.print("Escriba su nombre: ");
		nombre = teclado.nextLine();
		int numero = 0;
		System.out.print("Escriba los años de experiencia en números enteros, si no llega al año escriba 0 : ");
		numero = teclado.nextInt();
		String descripcion;
		if (numero >= 0) {
			switch (numero) {
				case 0:
					descripcion = "Desarrollador Junior L1 -- 15000-18000";
					break;
				case 1,2:
					descripcion = "Desarrollador Junior L2 -- 18000-22000";
					break;
				case 3,4,5:
					descripcion = "Desarrollador Senior L1 -- 22000-28000";
					break;
				case 6,7,8:
					descripcion = "Desarrollador Senior L2 -- 28000-36000";
					break;
				default:
					descripcion = "Analista/Arquitecto. Salario a convenir en base a rol.";
					break;
				
			}
			System.out.println("El Sr/a " + nombre + " tiene el siguiente nivel y salario: " + descripcion + " euros.");
		} else {
			System.out.println("Ha escrito una cantidad incorrecta de años.");
		}
		teclado.close();
	}

}
