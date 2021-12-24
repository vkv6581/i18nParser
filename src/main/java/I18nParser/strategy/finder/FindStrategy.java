package I18nParser.strategy.finder;

import java.io.File;

public interface FindStrategy {
    boolean isTarget(File file);
}
