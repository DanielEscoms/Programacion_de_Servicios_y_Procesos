package t3.evaluable3;

public class App {

	public static class Ventilador {

		boolean encendido = true;
		int tiempo = 1000;

		public void encenderVentilador() {
			while (true) {
				synchronized (this) {
					try {
						while (encendido == false)
							wait();
						System.err.println("Ventilador encendido");
						Thread.sleep(tiempo);
						System.out.println("Ventilador ha estado encendido durante " + tiempo / 1000 + " segundos.");
						encendido = false;
						notify();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		public void apagarVentilador() {
			while (true) {
				synchronized (this) {
					try {
						while (encendido == true)
							wait();
						System.err.println("Ventilador apagado");
						Thread.sleep(tiempo);
						System.out.println("Ventilador ha estado apagado durante " + tiempo / 1000 + " segundos.");
						encendido = true;
						notify();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	public static void main(String[] args) {
		Ventilador v = new Ventilador();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				v.encenderVentilador();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				v.apagarVentilador();
			}
		});
		t1.start();
		t2.start();

		Mina mina = new Mina(150);
		Minero minero = new Minero(mina);
		Thread t;
		for (int i = 0; i < 20; i++) {
			t = new Thread(minero);
			t.setName("Minero " + (i + 1));
			t.start();
		}
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Se ha recolectado un total de " + mina.recolectado + "oro.");
	}

}
