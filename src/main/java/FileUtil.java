import org.jf.dexlib2.iface.ClassDef;
import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileUtil {
    public static boolean createFile(String FilePath) {
        try {
            File file = new File(FilePath);
            if (!file.exists()) {
                file.createNewFile();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean writeFile(String FilePath, String content) {
        try {
            createFile(FilePath);
            FileWriter fileWriter = new FileWriter(FilePath);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static List<File> scanApkFile(String apkDirPath) {
        File apkDir = new File(apkDirPath);
        File[] allFile = apkDir.listFiles();
        List<File> apkFiles = new ArrayList<File>();
        for(File file : allFile) {
            if (file.getName().toLowerCase().endsWith(".apk")) {
                apkFiles.add(file);
            }
        }
        return apkFiles;
    }



    public static void save(String FileName, Set<ClassDef> ClassSet) {
        JSONArray jsonArray = new JSONArray();
        for (ClassDef clazz : ClassSet) {
            String clazzName = clazz.toString();
            jsonArray.put(clazzName);
        }
        String jsonStr = jsonArray.toString(4);
        FileUtil.writeFile(FileName, jsonStr);
    }
}
