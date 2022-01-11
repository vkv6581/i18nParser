package I18nParser.strategy.filter;

import I18nParser.vo.I18nFileVo;

import java.util.List;

public class AllFilterStrategy implements FilterStrategy{

    @Override
    public List<I18nFileVo> filter(List<I18nFileVo> targetFileVoList) {
        return targetFileVoList;
    }
}
