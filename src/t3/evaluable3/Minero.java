package t3.evaluable3;

public class Minero implements Runnable {
	int bolsa;
	long tiempoExtraccion;
	Mina mina;
	int recolectaEsperada;

	Minero(Mina mina) {
		this.bolsa = 0;
		this.tiempoExtraccion = 1000;
		this.mina = mina;
	}

	synchronized public void extraerRecurso(String nombre) {
		if (mina.stock > 0) {
//			System.out.println("Stock de" + mina.stock);
			mina.stock--;
			bolsa = 1;
			System.out.println(
					"El " + nombre + " ha recolectado " + bolsa + " de oro, Stock de mina restante: " + mina.stock);
			
		} else {
			System.err.println("No hay stock suficiente");
		}
		
	}

	public void run() {

		while (mina.stock > 0) {
			String nombre = Thread.currentThread().getName();
			extraerRecurso(nombre);
			try {
				Thread.sleep(tiempoExtraccion);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mina.recolectado++;
			
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Se ha recolectado un total de " + mina.recolectado + "oro.");
	}
}
