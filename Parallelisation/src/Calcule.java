public class Calcule extends Thread {
    // : Déclarer les attributs nécessaires
	    private int valeur;
	    private int multiplicateur;
	    private int index;
	    private long resultat = 0;
	    
		public Calcule(int valeur, int multiplicateur, int index) {
			this.valeur = valeur;
			this.multiplicateur = multiplicateur;
			this.index = index;
		}
		
		@Override
		public void run() {
		    // : Implémenter le calcul intensif
		    // Même logique que dans la boucle interne de CalculSequentiel
			long resultat = 0;
            for (int j = 0; j < multiplicateur; j++) {
                resultat += valeur * valeur + valeur;
            }
            System.out.println("Traitement de " + valeur + " terminé : " + resultat);
            
		}

		public long getResultat() {
			return resultat;
		}
		
		
		
}
