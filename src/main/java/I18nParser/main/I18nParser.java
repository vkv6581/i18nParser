package I18nParser.main;

import I18nParser.strategy.filter.AllFilterStrategy;
import I18nParser.strategy.filter.FilterStrategy;
import I18nParser.strategy.filter.NoTranslationFilterStrategy;
import I18nParser.strategy.finder.DefaultFindStrategy;
import I18nParser.strategy.finder.FindStrategy;
import I18nParser.strategy.parser.JsParseStrategy;
import I18nParser.strategy.parser.ParseStrategy;
import I18nParser.strategy.parser.PropertiesParseStrategy;
import I18nParser.tools.FileFinder;
import I18nParser.tools.FileParser;
import I18nParser.tools.I18nExcelExporter;
import I18nParser.tools.I18nFilter;
import I18nParser.vo.I18nFileVo;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;

public class I18nParser {
    public void start() {
        List<String> targetFileList = findTargetFileList();
        List<I18nFileVo> i18nFileVoList = new ArrayList<>();

        for (String targetFilePath : targetFileList) {
            I18nFileVo i18nFileVo = this.parseFile(targetFilePath);
            i18nFileVoList.add(i18nFileVo);
        }

        i18nFileVoList = filterTargetFileVoList(i18nFileVoList);

        excelExport(i18nFileVoList);
    }

    private List<String> findTargetFileList() {
        FindStrategy strategy = new DefaultFindStrategy();
        FileFinder finder = new FileFinder(strategy);

        List<String> targetFileList = finder.findTarget();

        System.out.println(targetFileList);
        return targetFileList;
    }

    private I18nFileVo parseFile(String targetFilePath) {
        ParseStrategy strategy = this.getStrategy(targetFilePath);
        FileParser parser = new FileParser(strategy, targetFilePath);
        I18nFileVo i18nFileVo = parser.parse();

        System.out.println("parse done : " + targetFilePath);
        return i18nFileVo;
    }

    private ParseStrategy getStrategy(String targetFilePath) {
        if("properties".equals(FilenameUtils.getExtension(targetFilePath).toLowerCase())) {
            return new PropertiesParseStrategy();
        }
        else if("js".equals(FilenameUtils.getExtension(targetFilePath).toLowerCase())) {
            return new JsParseStrategy();
        }
        return null;
    }

    private List<I18nFileVo> filterTargetFileVoList(List<I18nFileVo> fileVoList) {
        FilterStrategy strategy = new AllFilterStrategy();
        I18nFilter filter = new I18nFilter(strategy, fileVoList);
        return filter.filter();
    }

    private void excelExport(List<I18nFileVo> fileVoList) {
        for (I18nFileVo i18nFileVo : fileVoList) {
            this.excelExport(i18nFileVo);
        }
    }

    private void excelExport(I18nFileVo i18nFileVo) {
        I18nExcelExporter excelExporter = new I18nExcelExporter(i18nFileVo);
        excelExporter.export();
    }
}
