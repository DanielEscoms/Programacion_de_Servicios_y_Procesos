package t2.evaluable2;

public class CalculaProbabilidad {

	public double calcula(double posicionNEO, double velocidadNEO) {
		double posicionTierra = 1;
		double velocidadTierra = 100;
		for (int i = 0; i < (50 * 365 * 24 * 60 * 60); i++) {
		posicionNEO = posicionNEO + velocidadNEO * i;
		posicionTierra = posicionTierra + velocidadTierra * i;
		}
		double resultado = 100 * Math.random() *
		Math.pow( ((posicionNEO-posicionTierra)/(posicionNEO+posicionTierra)), 2);
		return resultado;
	}

	public static double main(String[] args) {
		CalculaProbabilidad cp = new CalculaProbabilidad();
		double n1 = Double.parseDouble(args[1]);
		double n2 = Double.parseDouble(args[2]);
		double resultado = cp.calcula(n1, n2);
		System.out.println(resultado);
		return resultado;
	}

}
