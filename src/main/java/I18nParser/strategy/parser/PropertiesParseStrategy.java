package I18nParser.strategy.parser;

import I18nParser.constants.I18nParserOptions;
import I18nParser.vo.I18nFileVo;
import I18nParser.vo.I18nResourceVo;

import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Properties 다국어 파싱
 *
 * @author vkv6581@naonsoft.com
 */
public class PropertiesParseStrategy implements ParseStrategy{

    List<String> subLangList;

    public PropertiesParseStrategy() {
        this.subLangList = Arrays.asList(I18nParserOptions.SUB_LANG.split(";"));
    }

    @Override
    public I18nFileVo parse(String targetFilePath) {

        I18nFileVo i18nFileVo = new I18nFileVo(new File(targetFilePath));

        Properties defaultLangProperties = getProperties(targetFilePath);   //대표언어

        Map<String, Properties> subLangPropertiesMap = new HashMap<>();
        for (String subLang : subLangList) {
            Properties subLangProperties = this.getProperties(targetFilePath, subLang);
            subLangPropertiesMap.put(subLang, subLangProperties);
        }

        List<I18nResourceVo> i18nResourceVos = new ArrayList<>();
        for (Object key : defaultLangProperties.keySet()) {
            I18nResourceVo resourceVo = buildResourceVo((String) key, defaultLangProperties, subLangPropertiesMap);
            i18nResourceVos.add(resourceVo);
        }

        i18nFileVo.setResourceList(i18nResourceVos);

        return i18nFileVo;
    }

    private Properties getProperties(String targetFilePath) {
        return this.getProperties(targetFilePath, I18nParserOptions.DEFAULT_LANG);
    }

    private Properties getProperties(String targetFilePath, String langCd) {
        Properties properties = new Properties();
        try {
            targetFilePath = targetFilePath.replace("_"+ I18nParserOptions.DEFAULT_LANG, "_"+langCd);   //다른 다국어 파일들도 로드

            FileReader resources = null;
            resources = new FileReader(targetFilePath);
            properties.load(resources);

        } catch (Exception e) {
            System.out.println("PropertiesParseStrategy getProperties error");
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * Properties 들을 i18n reosource vo로 변환
     * @param defaultLangProperties
     * @param subLangPropertiesMap
     * @param key
     * @return
     */
    private I18nResourceVo buildResourceVo(String key, Properties defaultLangProperties, Map<String, Properties> subLangPropertiesMap) {
        Map<String, String> valMap = new HashMap<>();
        valMap.put(I18nParserOptions.DEFAULT_LANG, defaultLangProperties.getProperty(key));

        for (String subLang : this.subLangList) {
            Properties subLangProperties = subLangPropertiesMap.get(subLang);
            valMap.put(subLang, subLangProperties.getProperty(key));
        }

        I18nResourceVo resourceVo = new I18nResourceVo(key, valMap);
        return resourceVo;
    }
}
