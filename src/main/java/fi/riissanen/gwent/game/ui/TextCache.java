package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.fonts.Font;
import fi.riissanen.gwent.engine.render.fonts.Text;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cache containing loaded texts.
 * @author Daniel
 */
public class TextCache {

    private final Map<Font, List<Text>> cache = new HashMap<>();
    
    public void addText(Text text) {
        List<Text> cachedTexts = cache.get(text.getFont());
        if (cachedTexts == null) {
            cachedTexts = new ArrayList<>();
            cache.put(text.getFont(), cachedTexts);
        }
        text.getMeshData().create(text);
        cachedTexts.add(text);
    }
    
    public void removeText(Text text) {
        List<Text> cachedTexts = cache.get(text.getFont());
        cachedTexts.remove(text);
        if (cachedTexts.isEmpty()) {
            cache.remove(text.getFont());
        }
    }
    
    public Map<Font, List<Text>> getCache() {
        return cache;
    }
}
