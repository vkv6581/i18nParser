package I18nParser.strategy.filter;

import I18nParser.vo.I18nFileVo;
import I18nParser.vo.I18nResourceVo;

import java.util.List;
import java.util.stream.Collectors;

public class NoTranslationFilterStrategy implements FilterStrategy{

    @Override
    public List<I18nFileVo> filter(List<I18nFileVo> targetFileVoList) {

        targetFileVoList = targetFileVoList.stream().filter(i18nFileVo -> {
            return i18nFileVo.getResourceList() != null && i18nFileVo.getResourceList().size() > 0;
        }).collect(Collectors.toList());

        //번역된 것 제거
        for (I18nFileVo i18nFileVo : targetFileVoList) {
            List<I18nResourceVo> resourceVoList = i18nFileVo.getResourceList().stream().filter(i18nResourceVo -> {
                long distinctCnt = i18nResourceVo.getValMap().values().stream().distinct().count();
                long cnt = i18nResourceVo.getValMap().values().size();
                return distinctCnt != cnt;
            }).collect(Collectors.toList());

            i18nFileVo.setResourceList(resourceVoList);
        }

        targetFileVoList = targetFileVoList.stream().filter(i18nFileVo -> {
            return i18nFileVo.getResourceList() != null && i18nFileVo.getResourceList().size() > 0;
        }).collect(Collectors.toList());

        return targetFileVoList;
    }
}
