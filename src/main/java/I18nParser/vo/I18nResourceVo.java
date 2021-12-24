package I18nParser.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class I18nResourceVo {
    private String key;
    private Map<String, String> valMap;

    public I18nResourceVo() {
        HashMap<String, String> newMap = new HashMap<>();
        this.valMap = newMap;
    }

    public I18nResourceVo(String key, Map<String, String> valMap) {
        this.key = key;
        this.valMap = valMap;
    }
}
