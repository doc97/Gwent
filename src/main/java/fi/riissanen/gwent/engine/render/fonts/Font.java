package fi.riissanen.gwent.engine.render.fonts;

import fi.riissanen.gwent.engine.assets.Asset;
import fi.riissanen.gwent.engine.render.Texture;
import java.util.Map;

/**
 * Contains data about a font.
 * @author Daniel
 */
public class Font implements Asset {

    public static final int ASCII_SPACE = 32;
    private final Texture atlas;
    private final Map<Integer, Glyph> glyphs;
    private final int spaceWidth;
    private final int lineHeight;
    
    /**
     * Creates a new font.
     * @param atlas The texture atlas of the font
     * @param glyphs Glyphs mapped by ASCII code
     * @param spaceWidth The width of a space for this font
     * @param lineHeight The height of a line
     */
    public Font(Texture atlas, Map<Integer, Glyph> glyphs, int spaceWidth, int lineHeight) {
        this.atlas = atlas;
        this.glyphs = glyphs;
        this.spaceWidth = spaceWidth;
        this.lineHeight = lineHeight;
    }
    
    /**
     * Get the font texture atlas.
     * @return The atlas
     */
    public Texture getFontTexture() {
        return atlas;
    }
    
    /**
     * Get a glyph based on it's ASCII code.
     * @param asciiCode The code
     * @return The glyph corresponding to the code
     */
    public Glyph getGlyph(int asciiCode) {
        return glyphs.get(asciiCode);
    }
    
    /**
     * Get the glyph count.
     * @return The size of the map
     */
    public int getGlyphCount() {
        return glyphs.size();
    }
    
    /**
     * The width of the space of this font.
     * @return The width of the space glyph
     */
    public int getSpaceWidth() {
        return spaceWidth;
    }
    
    /**
     * The height of a line of this font.
     * @return The height of a line
     */
    public int getLineHeight() {
        return lineHeight;
    }
}
