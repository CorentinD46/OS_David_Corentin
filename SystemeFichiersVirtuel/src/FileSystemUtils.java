public class FileSystemUtils {

    public static void displayStats(VirtualFileSystem vfs) {
        MemoryManager mm = vfs.getMemoryManager();

        System.out.println("=== Statistiques du système de fichiers ===");
        System.out.println("Taille totale : " + (MemoryManager.TOTAL_MEMORY / 1024) + " Ko");
        System.out.println("Nombre total de blocs : " + MemoryManager.NUM_BLOCKS);

        // Compter les blocs libres
        int freeBlocks = 0;
        for (int i = 129; i < MemoryManager.NUM_BLOCKS; i++) { // Commencer après les blocs système
            if (!mm.isBlockUsed(i))
                freeBlocks++;
        }

        int usedBlocks = MemoryManager.NUM_BLOCKS - freeBlocks;
        System.out.println("Blocs libres : " + freeBlocks);
        System.out.println("Blocs utilisés : " + usedBlocks);

        double utilization = (double) usedBlocks / MemoryManager.NUM_BLOCKS * 100;
        System.out.printf("Taux d'utilisation : %.2f%%\n", utilization);

        // Analyser la fragmentation
        double fragmentation = calculateFragmentation(mm);
        System.out.printf("Fragmentation : %.2f%%\n", fragmentation * 100);

        // Compter les inodes utilisés
        int usedInodes = countUsedInodes(vfs);
        System.out.println("Inodes utilisés : " + usedInodes + "/" + MemoryManager.MAX_INODES);
    }

    private static double calculateFragmentation(MemoryManager mm) {
        int consecutiveGroups = 0;
        int totalFreeBlocks = 0;
        boolean inFreeGroup = false;

        for (int i = 129; i < MemoryManager.NUM_BLOCKS; i++) {
            if (!mm.isBlockUsed(i)) {
                totalFreeBlocks++;
                if (!inFreeGroup) {
                    consecutiveGroups++;
                    inFreeGroup = true;
                }
            } else {
                inFreeGroup = false;
            }
        }

        if (totalFreeBlocks == 0)
            return 0.0;

        return (double) consecutiveGroups / totalFreeBlocks;
    }

    private static int countUsedInodes(VirtualFileSystem vfs) {
        // Parcourir la table des inodes et compter ceux qui sont utilisés
        byte[] memory = vfs.getMemoryManager().getFilesystemMemory();
        int count = 0;

        for (int i = 0; i < MemoryManager.MAX_INODES; i++) {
            int offset = MemoryManager.INODE_TABLE_OFFSET + (i * Inode.INODE_SIZE);
            int inodeNumber = readInt(memory, offset);

            // Si le numéro d'inode correspond à l'index, l'inode est utilisé
            if (inodeNumber == i)
                count++;
        }

        return count;
    }

    public static void dumpMemoryLayout(VirtualFileSystem vfs) {
        System.out.println("\n=== Layout mémoire détaillé ===");
        System.out.printf("Superbloc        : %d - %d\n", 0, MemoryManager.BLOCK_SIZE - 1);
        System.out.printf("Bitmap           : %d - %d\n", MemoryManager.BITMAP_OFFSET, 
                         MemoryManager.BITMAP_OFFSET + MemoryManager.BLOCK_SIZE - 1);
        System.out.printf("Table inodes     : %d - %d\n", MemoryManager.INODE_TABLE_OFFSET,
                         MemoryManager.DATA_OFFSET - 1);
        System.out.printf("Zone données     : %d - %d\n", MemoryManager.DATA_OFFSET,
                         MemoryManager.TOTAL_MEMORY - 1);
    }
}