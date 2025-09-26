public class Image {
    public void save_txt(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("P3\n");
            writer.write(width + " " + height + "\n");
            writer.write("255\n");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int r = pixels[y][x][0];
                    int g = pixels[y][x][1];
                    int b = pixels[y][x][2];
                    writer.write(r + " " + g + " " + b + " ");
                }
                writer.write("\n");
            }
        }
    }

    /**
     * Lecture d'une image au format texte PPM (P3)
     */
    public static Image read_txt(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename);
             Scanner sc = new Scanner(fis)) {

            // Vérification du format
            String magic = sc.next();
            if (!magic.equals("P3")) {
                throw new IOException("Format PPM non supporté : " + magic);
            }

            // Lecture dimensions + valeur max
            int width = sc.nextInt();
            int height = sc.nextInt();
            int maxVal = sc.nextInt(); // typiquement 255

            // Création de l'image
            Image img = new Image(width, height);

            // Remplissage des pixels
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int r = sc.nextInt();
                    int g = sc.nextInt();
                    int b = sc.nextInt();
                    img.setPixel(x, y, r, g, b);
                }
            }

            return img;
        }
    }
}