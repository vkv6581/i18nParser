package I18nParser.tools;

import I18nParser.constants.I18nParserOptions;
import I18nParser.strategy.finder.FindStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {
    private FindStrategy strategy;

    public FileFinder(FindStrategy strategy) {
        this.strategy = strategy;
    }

    public List<String> findTarget() {
        List<String> targetFileList = new ArrayList<>();
        File root = new File(I18nParserOptions.ROOT_PATH);
        checkFile(root, targetFileList);

        return targetFileList;
    }

    public void checkFile(File file, List<String> targetFileList) {
        if (file == null || !file.exists()) {
            return;
        }

        if (file.isDirectory()) {
            String[] files = file.list();
            String path = "";
            try {
                path = file.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    checkFile(new File(file, files[i]), targetFileList);
                }
            }
        } else {
            try {
                if (strategy.isTarget(file)) {
                    targetFileList.add(file.getAbsolutePath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
