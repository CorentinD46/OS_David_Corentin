
public class TestCompteur {
	private static final int NOMBRE_TESTS = 50;
	private static final int NOMBRE_THREADS = 9;
	private static final int INCREMENTS_PAR_THREAD = 1111111;
	private static final int RESULTAT_ATTENDU = NOMBRE_THREADS * INCREMENTS_PAR_THREAD;

	public static void main(String[] args) throws InterruptedException {
		System.out.println("=== Test des Race Conditions ===");
		for (int test = 1; test <= NOMBRE_TESTS; test++) {
			CompteurDangereux.resetCompteur();
			Thread[] threads = new Thread[NOMBRE_THREADS];
			for (int i = 0; i < NOMBRE_THREADS; i++) {
				threads[i] = new Thread(
						new CompteurDangereux.Incrementeur("Thread-" + i, INCREMENTS_PAR_THREAD)
						);
			}
			//Demarre les thread
			for (Thread thread : threads) {
				thread.start();
			}

			//Attente de tous les threads
			for (Thread thread : threads) {
				thread.join();
			}
		}
		int resultatFinal = CompteurDangereux.getCompteur();
		System.out.println(resultatFinal);
		
		System.out.println("=== Statistiques ===");
	}

}
