public class CalculParallele {
    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int MULTIPLICATEUR = 1000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Calcul Parallèle ===");

        long debut = System.nanoTime();
        long sommeTotal = 0;

        Calcule[] threads = new Calcule[DONNEES.length];

        // Lancement des threads
        for (int i = 0; i < DONNEES.length; i++) {
            int valeur = DONNEES[i];
            threads[i] = new Calcule(valeur, MULTIPLICATEUR, i);
            threads[i].start();
        }
        try {
        	for(Calcule thread : threads) {
        		thread.join();
        	} 
        } catch(InterruptedException e) {
    		System.err.println("Interruption lors de l'attente des threads");
    		Thread.currentThread().interrupt();
    	}

        // Attente de la fin de tous les threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
            long resultat = threads[i].getResultat();
            sommeTotal += resultat;
        }

        long duree = (System.nanoTime() - debut) / 1_000_000;
        System.out.println("Résultat total : " + sommeTotal);
        System.out.println("Durée : " + duree + " ms");
    }
}