package I18nParser.strategy.parser;

import I18nParser.constants.I18nParserOptions;
import I18nParser.vo.I18nFileVo;
import I18nParser.vo.I18nResourceVo;

import java.io.*;
import java.util.*;

public class JsParseStrategy implements ParseStrategy{

    List<String> subLangList;

    public JsParseStrategy() {
        this.subLangList = Arrays.asList(I18nParserOptions.SUB_LANG.split(";"));
    }

    @Override
    public I18nFileVo parse(String targetFilePath) {
        I18nFileVo i18nFileVo = new I18nFileVo(new File(targetFilePath));

        Map<String, String> defaultResourceMap = this.convertJsI18nToMap(targetFilePath);

        Map<String, Map<String, String>> subLangValMap = new HashMap<>();
        for (String subLang : subLangList) {
            Map<String, String> subLangMap = this.convertJsI18nToMap(targetFilePath, subLang);
            subLangValMap.put(subLang, subLangMap);
        }

        List<I18nResourceVo> i18nResourceVos = new ArrayList<>();
        for (Object key : defaultResourceMap.keySet()) {
            I18nResourceVo resourceVo = buildResourceVo((String) key, defaultResourceMap, subLangValMap);
            i18nResourceVos.add(resourceVo);
        }

        i18nFileVo.setResourceList(i18nResourceVos);

        return i18nFileVo;
    }

    private Map<String, String> convertJsI18nToMap(String targetFilePath) {
        return this.convertJsI18nToMap(targetFilePath, I18nParserOptions.DEFAULT_LANG);
    }

    private Map<String, String> convertJsI18nToMap(String targetFilePath, String langCd) {
        Map<String, String> jsI18nMap = new HashMap<>();

        targetFilePath = targetFilePath.replace("_"+ I18nParserOptions.DEFAULT_LANG, "_"+langCd);   //다른 다국어 파일들도 로드

        try (BufferedReader reader = new BufferedReader(
                new FileReader(targetFilePath)
        )) {
            String str;
            Set strSet = new HashSet();
            while ((str = reader.readLine()) != null) {
                if(str != null && str.trim().length() > 0 && !str.startsWith("//")) {
                    try {
                        int idx = str.indexOf("=");
                        String key = str.substring(0, idx).trim();
                        String val = str.substring(idx + 1).trim();

                        key = key.replaceFirst("var ", "");

                        val = val.substring(1);
                        val = val.substring(0, val.length() - 2);
                        jsI18nMap.put(key, val);
                    }
                    catch(Exception e2) {
                        e2.printStackTrace();
                        System.out.println("js 라인 파싱 에러 , 해당 라인 : " + str);;
                    }
                }
            }

            List strList = new ArrayList(strSet);

            Collections.sort(strList);

            strList.forEach(setStr -> {
                System.out.println(setStr);
            });
        } catch (Exception e) {
            System.out.println("JsParseStrategy getProperties error");
            e.printStackTrace();
        }
        return jsI18nMap;
    }

    /**
     * Map 들을 i18n reosource vo로 변환
     * @param defaultLangValMap
     * @param subLangValMap
     * @param key
     * @return
     */
    private I18nResourceVo buildResourceVo(String key, Map<String, String> defaultLangValMap, Map<String, Map<String, String>> subLangValMap) {
        Map<String, String> valMap = new HashMap<>();
        valMap.put(I18nParserOptions.DEFAULT_LANG, defaultLangValMap.get(key));

        for (String subLang : this.subLangList) {
            Map<String, String> subLangMap = subLangValMap.get(subLang);
            valMap.put(subLang, subLangMap.get(key));
        }

        I18nResourceVo resourceVo = new I18nResourceVo(key, valMap);
        return resourceVo;
    }
}
