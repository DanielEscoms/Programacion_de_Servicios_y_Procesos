package t3.evaluable3;

public class Minero implements Runnable {
	int bolsa;
	long tiempoExtraccion;
	Mina mina;
	
	Minero(Mina mina){
		this.bolsa= 0;
		this.tiempoExtraccion = 1000;
		this.mina = mina;
	}
	
	synchronized public void extraerRecurso(String nombre) {
		if(mina.stock > 0) {
			System.out.println("Stock de" +mina.stock);
			mina.stock--;
			bolsa =1;
			try {
				Thread.sleep(tiempoExtraccion);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.err.println("No hay stock suficiente");
		}
	}
	
	public void run() {
		String nombre = Thread.currentThread().getName();
		extraerRecurso(nombre);
	}
}
