import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class Main {
    public static void saveGame(GameProgress gameProgress, String savePath) {
        try (FileOutputStream fos = new FileOutputStream(savePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipPath, ArrayList<String> pathList) {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (int i = 0; i < pathList.size(); i++) {
                try (FileInputStream fis = new FileInputStream(pathList.get(i))) {
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.putNextEntry(new ZipEntry("save_" + (i + 1) + ".dat"));
                    zos.write(buffer);
                    zos.closeEntry();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFiles(ArrayList<String> pathList) {
        for (int i = 0; i < pathList.size(); i++) {
            File file = new File(pathList.get(i));
            file.delete();
        }
    }

    public static void main(String[] args) {
        String savePath = "D://Games/savegames/";
        String zipPath = "D://Games/savegames/archive.zip";
        ArrayList<String> pathList = new ArrayList<>(Arrays.asList());

        GameProgress progress1 = new GameProgress(85, 137, 20, 649);
        GameProgress progress2 = new GameProgress(63, 251, 47, 908);
        GameProgress progress3 = new GameProgress(27, 490, 53, 186);

        File save1 = new File(savePath, "save1.dat");
        saveGame(progress1, save1.getPath());
        pathList.add(save1.getPath());
        File save2 = new File(savePath, "save2.dat");
        saveGame(progress2, save2.getPath());
        pathList.add(save2.getPath());
        File save3 = new File(savePath, "save3.dat");
        saveGame(progress3, save3.getPath());
        pathList.add(save3.getPath());

        zipFiles(zipPath, pathList);

        deleteFiles(pathList);
    }
}