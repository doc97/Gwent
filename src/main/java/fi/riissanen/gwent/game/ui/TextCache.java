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
    
    /**
     * Adds a text to the cache.
     * @param text The text to add
     */
    public void addText(Text text) {
        List<Text> cachedTexts = cache.get(text.getFont());
        if (cachedTexts == null) {
            cachedTexts = new ArrayList<>();
            cache.put(text.getFont(), cachedTexts);
        }
        text.getMeshData().create(text);
        cachedTexts.add(text);
    }
    
    /**
     * Removes a text from the cache.
     * @param text The text to remove
     */
    public void removeText(Text text) {
        List<Text> cachedTexts = cache.get(text.getFont());
        if (cachedTexts != null) {
            cachedTexts.remove(text);
            if (cachedTexts.isEmpty()) {
                cache.remove(text.getFont());
            }
        }
    }
    
    /**
     * Checks whether the text has been added to the cache.
     * @param text The text
     * @return True if the text exists in the cache
     */
    public boolean hasText(Text text) {
        List<Text> cachedTexts = cache.get(text.getFont());
        return cachedTexts != null && cachedTexts.contains(text);
    }
    
    public Map<Font, List<Text>> getCache() {
        return cache;
    }
}
