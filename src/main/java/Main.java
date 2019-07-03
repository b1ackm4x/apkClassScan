import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.DexFile;
import org.jf.dexlib2.iface.MultiDexContainer;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String processDir = "apk";
        String logDir = "log";
        List<File> apkList = FileUtil.scanApkFile(processDir);
        for (File apk : apkList) {
            ApkFile apkObject = ApkFile.loadFromFile(apk);
            Set<ClassDef> okHttpReleatedClassList = new HashSet<ClassDef>();
            MultiDexContainer<? extends DexBackedDexFile> apkDexFiles = apkObject.getDexContainer();
            try {
                for (String DexEntryName : apkDexFiles.getDexEntryNames()) {
                    DexFile dexFile = apkDexFiles.getEntry(DexEntryName);
                    Set<ClassDef> allClasses = new HashSet<ClassDef>();
                    allClasses.addAll(dexFile.getClasses());
                    for (ClassDef clazz : allClasses) {
                        String clazzName = clazz.toString();
                        // 过滤一些需要的类
                        if (clazzName.toLowerCase().contains("okhttp")) {
                            okHttpReleatedClassList.add(clazz);
                        }
                    }
                }
                String logPath = logDir + File.separator + apk.getName().replace(".apk", ".xml");
                FileUtil.save(logPath, okHttpReleatedClassList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
