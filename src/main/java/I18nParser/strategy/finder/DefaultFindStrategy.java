package I18nParser.strategy.finder;

import org.apache.commons.io.FilenameUtils;
import I18nParser.constants.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DefaultFindStrategy implements FindStrategy{

    public List targetExtension;

    public DefaultFindStrategy() {
        this.targetExtension = Arrays.asList(I18nParserOptions.TARGET_EXTENSION.split(";"));
    }

    @Override
    public boolean isTarget(File file) {
        return isTargetExtension(file) && isTargetNameForm(file);
    }

    private boolean isTargetExtension(File file) {
        String ext = FilenameUtils.getExtension(file.getName());
        return targetExtension.indexOf(ext) > -1;
    }

    private boolean isTargetNameForm(File file) {
        return file.getName().toLowerCase().indexOf("messages_" + I18nParserOptions.DEFAULT_LANG) > -1;
    }

}
