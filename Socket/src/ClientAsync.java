import java.net.*;
import java.io.*;

public class ClientAsync {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("A202D1", 5000);
        System.out.println("Connecté au serveur");

        // Thread de lecture (messages du serveur)
        Thread lectureThread = new Thread(new ReceptionHandler(socket));
        lectureThread.setDaemon(true); // Permet à la JVM de terminer proprement
        lectureThread.start();

        // Thread principal = écriture (envoi au serveur)
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader clavier = new BufferedReader(
            new InputStreamReader(System.in)
        );

        String texte;
        while ((texte = clavier.readLine()) != null) {
            out.println(texte);
        }

        socket.close();
        System.out.println("Déconnecté");
    }
}

class ReceptionHandler implements Runnable {
    private Socket socket;

    public ReceptionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            
            String ligne;
            while ((ligne = in.readLine()) != null) {
                System.out.println(ligne);
            }
        } catch (IOException e) {
            System.err.println("Connexion perdue : " + e.getMessage());
        }
    }
}