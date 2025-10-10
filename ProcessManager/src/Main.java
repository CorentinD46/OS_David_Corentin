import java.io.File;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		ProcessController pc = new ProcessController();
		try {
			pc.executeSimple("cmd",new String[]{"/c", "echo Hello World"});
			pc.executeInteractive("cmd",new String[]{"/c", "python"});
		} catch (IOException e) {
			System.out.println("Erreur lors de l'exécution du processus : " + e.getMessage());
			e.printStackTrace();
		}
    }
	
	private static void demonstrateInteractiveProcess(ProcessController controller)
			throws IOException, InterruptedException {
		Process
	}
}
