package t3.evaluable3;

public class App {

	public static void main(String[] args) {
		Mina mina = new Mina(10000);
		Minero minero = new Minero(mina);
		Thread t;
		for (int i = 0; i < 20; i++) {
			t = new Thread(minero);
			t.setName("Minero "+ (i+1));
			t.start();
			
		}
	}

}
