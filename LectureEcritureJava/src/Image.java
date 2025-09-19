import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Image {
    private int width;
    private int height;
    // pixels[y][x][0=R,1=G,2=B]
    private int[][][] pixels;

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    /**
     * Constructeur : initialise une image vide.
     */
    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[height][width][3];
    }

    /**
     * Définit la couleur d'un pixel à la position (x, y)
     */
    public void setPixel(int x, int y, int r, int g, int b) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixels[y][x][0] = r;
            pixels[y][x][1] = g;
            pixels[y][x][2] = b;
        }
    }

    /**
     * Sauvegarde l'image au format texte PPM (P3)
     */
    public void save_txt(String filename) throws IOException {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("P3\n");
            fw.write(width + " " + height + "\n");
            fw.write("255\n");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    fw.write(pixels[y][x][0] + " " +
                             pixels[y][x][1] + " " +
                             pixels[y][x][2] + " ");
                }
                fw.write("\n");
            }
        }
    }
    
    static public read_txt(String filename) throws IOException {
       byte[]data = fs.readAllBytes();
       String txt = new String(data,StandardCharsets.UTF_8);
    }
}
