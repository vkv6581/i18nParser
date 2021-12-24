package I18nParser.vo;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class I18nFileVo {
    private String fileName;
    private String extension;
    private List<I18nResourceVo> resourceList;

    public I18nFileVo(File file) {
        this.fileName = file.getName();
        this.extension = FilenameUtils.getExtension(file.getName());
        this.resourceList = new ArrayList<>();
    }
}
