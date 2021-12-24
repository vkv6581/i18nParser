package I18nParser.strategy.parser;

import I18nParser.vo.I18nFileVo;

public interface ParseStrategy {
    I18nFileVo parse(String targetFilePath);
}
