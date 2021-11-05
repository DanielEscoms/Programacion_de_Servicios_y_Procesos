package t2.evaluable2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class AEv2 {

	public static void getResultadoFichero(String nombreFichero) {
		
		try {
			FileInputStream fichero = new FileInputStream(nombreFichero);
			InputStreamReader isr = new InputStreamReader(fichero);
			BufferedReader br = new BufferedReader(isr);
			String linea = br.readLine();
			double probabilidad = Double.parseDouble(linea);
			System.out.println("La probabilidad de que el NEO colisione es del " + probabilidad + "%");
			if (probabilidad > 10) {
				System.err.println("Alerta Mundial!! Riesgo elevado de impacto terrestre");
			}else {
				System.out.println("Riesgo bajo de impacto terrestre");
			}
		}catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		int numeroCores = Runtime.getRuntime().availableProcessors();
		System.out.println(numeroCores);
		for (int i = 0; i < 4; i++) {

		}

	}

}
