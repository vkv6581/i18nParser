package I18nParser.tools;

import I18nParser.strategy.parser.ParseStrategy;
import I18nParser.vo.I18nFileVo;

public class FileParser {
    private ParseStrategy strategy;
    private String targetFilePath;

    public FileParser(ParseStrategy strategy, String targetFilePath) {
        this.strategy = strategy;
        this.targetFilePath = targetFilePath;
    }

    public I18nFileVo parse() {
        return strategy.parse(targetFilePath);
    }
}
