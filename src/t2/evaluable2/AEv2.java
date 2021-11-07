package t2.evaluable2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AEv2 {

	public String[] leerNEO(String linea) {
		String[] propiedadesNEO = linea.split(",");
		System.out.println("El NEO " + propiedadesNEO[0] + ", tiene la posicion " + propiedadesNEO[1]
				+ " y tiene una velocidad de " + propiedadesNEO[2] + " km/s.");
		return propiedadesNEO;
	}

	public void lanzarProbabilidad(String[] propiedadesNEO) {
		String clase = "t2.evaluable2.CalculaProbabilidad";
		try {
			File directorioProbabilidad = new File("...");
			File fichProbabilidad = new File(propiedadesNEO[0]);
			String javaHome = System.getProperty("java.home");
			String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
			String classpath = System.getProperty("java.class.path");
			System.out.println(classpath);
			String className = clase;

			List<String> command = new ArrayList<>();
			command.add(javaBin);
			command.add("-cp");
			command.add(classpath);
			command.add(className);
			command.add(propiedadesNEO[0].toString());
			command.add(propiedadesNEO[1].toString());
			command.add(propiedadesNEO[2].toString());

			System.out.println(command);

			ProcessBuilder builder = new ProcessBuilder(command);
			builder.directory(directorioProbabilidad);
			builder.redirectOutput(fichProbabilidad);
			Process process = builder.start();
			process.waitFor();
			System.out.println(process.exitValue());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			} else {
				System.out.println("Riesgo bajo de impacto terrestre");
			}
		} catch (FileNotFoundException e) {
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
		AEv2 objeto = new AEv2();
		try {
			File f = new File("NEOs.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			
			for (int i = 0; i < numeroCores; i++) {
				String[] propiedadesNEO = objeto.leerNEO(linea);
				objeto.lanzarProbabilidad(propiedadesNEO);
				linea = br.readLine();
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
