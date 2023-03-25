import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class Main {
    public static void saveGame(GameProgress save1, GameProgress save2, GameProgress save3, String savePath1, String savePath2, String savePath3) {
        try (FileOutputStream fos1 = new FileOutputStream(savePath1);
             FileOutputStream fos2 = new FileOutputStream(savePath2);
             FileOutputStream fos3 = new FileOutputStream(savePath3);
             ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
             ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
             ObjectOutputStream oos3 = new ObjectOutputStream(fos3)) {
            oos1.writeObject(save1);
            oos2.writeObject(save2);
            oos3.writeObject(save3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String archivePath, String savePath1, String savePath2, String savePath3) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archivePath));
             FileInputStream fis1 = new FileInputStream(savePath1);
             FileInputStream fis2 = new FileInputStream(savePath2);
             FileInputStream fis3 = new FileInputStream(savePath3)) {
            ZipEntry entry1 = new ZipEntry("savezip1.dat");
            ZipEntry entry2 = new ZipEntry("savezip2.dat");
            ZipEntry entry3 = new ZipEntry("savezip3.dat");
            zout.putNextEntry(entry1);
            zout.putNextEntry(entry2);
            zout.putNextEntry(entry3);
            byte[] buffer1 = new byte[fis1.available()];
            byte[] buffer2 = new byte[fis2.available()];
            byte[] buffer3 = new byte[fis3.available()];
            fis1.read(buffer1);
            fis1.read(buffer2);
            fis1.read(buffer3);
            zout.write(buffer1);
            zout.write(buffer2);
            zout.write(buffer3);
            zout.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFiles(String savePath1, String savePath2, String savePath3) {
        File file1 = new File(savePath1);
        if (file1.delete()) {
            System.out.println("Файл " + file1.getName() + " удалён");
        } else System.out.println("Файл " + file1.getName() + " не удалён");
        File file2 = new File(savePath2);
        if (file2.delete()) {
            System.out.println("Файл " + file2.getName() + " удалён");
        } else System.out.println("Файл " + file2.getName() + " не удалён");
        File file3 = new File(savePath3);
        if (file3.delete()) {
            System.out.println("Файл " + file3.getName() + " удалён");
        } else System.out.println("Файл " + file3.getName() + " не удалён");
    }

    public static void main(String[] args) {
        String savePath1 = "D://Games/savegames/save1.dat";
        String savePath2 = "D://Games/savegames/save2.dat";
        String savePath3 = "D://Games/savegames/save3.dat";
        String archivePath = "D://Games/savegames/archive.zip";

        GameProgress save1 = new GameProgress(85, 137, 20, 649);
        GameProgress save2 = new GameProgress(63, 251, 47, 908);
        GameProgress save3 = new GameProgress(27, 490, 53, 186);

        saveGame(save1, save2, save3, savePath1, savePath2, savePath3);
        zipFiles(archivePath, savePath1, savePath2, savePath3);
        deleteFiles(savePath1, savePath2, savePath3);
    }
}