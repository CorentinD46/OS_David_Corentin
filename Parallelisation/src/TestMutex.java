
public class TestMutex {
	private static final int NOMBRE_TESTS = 50;
	private static final int NOMBRE_THREADS = 9;
	private static final int INCREMENTS_PAR_THREAD = 1111111;
	private static final int RESULTAT_ATTENDU = NOMBRE_THREADS * INCREMENTS_PAR_THREAD;

	public static void main(String[] args) throws InterruptedException {
		System.out.println("<=== Test des Race Conditions ===>");
		int nombreSucces = 0;
        int valeurMin = Integer.MAX_VALUE;
        int valeurMax = Integer.MIN_VALUE;
		for (int test = 1; test <= NOMBRE_TESTS; test++) {
			CompteurSecurise.resetCompteur();
			Thread[] threads = new Thread[NOMBRE_THREADS];
			for (int i = 0; i < NOMBRE_THREADS; i++) {
				threads[i] = new Thread(
						new CompteurSecurise.Incrementeur("Thread-" + i, INCREMENTS_PAR_THREAD)
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
			
			int resultatFinal = CompteurSecurise.getCompteur();
            boolean correct = (resultatFinal == RESULTAT_ATTENDU);
			
			if (correct) {
                nombreSucces++;
            }
			
			valeurMin = Math.min(valeurMin, resultatFinal);
            valeurMax = Math.max(valeurMax, resultatFinal);
            System.out.println(resultatFinal);
		}
		
		System.out.println("<=== Statistiques ===>");
        System.out.println("Tests réussis : " + nombreSucces + "/" + NOMBRE_TESTS);
        System.out.println("Taux de réussite " + (nombreSucces * 100.0 / NOMBRE_TESTS) + " %");
        System.out.println("Valeur minimale obtenue : " + valeurMin);
        System.out.println("Valeur maximale obtenue : " + valeurMax);
	}

}
