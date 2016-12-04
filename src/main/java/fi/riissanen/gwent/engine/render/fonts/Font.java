package fi.riissanen.gwent.engine.render.fonts;

import fi.riissanen.gwent.engine.assets.Asset;
import fi.riissanen.gwent.engine.render.Texture;
import java.util.Map;

/**
 * Contains data about a font.
 * @author Daniel
 */
public class Font implements Asset {

    private final Texture atlas;
    private final Map<Integer, Glyph> glyphs;
    
    public Font(Texture atlas, Map<Integer, Glyph> glyphs) {
        this.atlas = atlas;
        this.glyphs = glyphs;
    }
    
    public Texture getFontTexture() {
        return atlas;
    }
    
    public Glyph getGlyph(int asciiCode) {
        return glyphs.get(asciiCode);
    }
    
    public int getGlyphCount() {
        return glyphs.size();
    }
}
