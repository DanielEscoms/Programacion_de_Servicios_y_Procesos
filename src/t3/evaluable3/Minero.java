package t3.evaluable3;

public class Minero implements Runnable {
	int bolsa;
	long tiempoExtraccion;
	Mina mina;

	Minero(Mina mina) {
		this.bolsa = 0;
		this.tiempoExtraccion = 1000;
		this.mina = mina;
	}

	synchronized public void extraerRecurso(String nombre, int cantidadARecolectar) {
		if (cantidadARecolectar <= mina.stock) {
			System.out.println("Stock de " + mina.stock);
			mina.stock--;
			mina.recolectado++;
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
			int cantidadARecolectar = 1;
			extraerRecurso(nombre,cantidadARecolectar);
			try {
				Thread.sleep(tiempoExtraccion);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
