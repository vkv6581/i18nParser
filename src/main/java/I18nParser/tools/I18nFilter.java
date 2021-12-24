package I18nParser.tools;

import I18nParser.strategy.filter.FilterStrategy;
import I18nParser.vo.I18nFileVo;

import java.util.List;

public class I18nFilter {
    private FilterStrategy strategy;
    private List<I18nFileVo> fileVoList;

    public I18nFilter(FilterStrategy strategy, List<I18nFileVo> fileVoList) {
        this.strategy = strategy;
        this.fileVoList = fileVoList;
    }

    public List<I18nFileVo> filter() {
        return strategy.filter(fileVoList);
    }

}
