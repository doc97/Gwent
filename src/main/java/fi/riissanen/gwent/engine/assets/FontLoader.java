package fi.riissanen.gwent.engine.assets;

import de.matthiasmann.twl.utils.PNGDecoder.Format;
import fi.riissanen.gwent.engine.files.FileUtils;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.engine.render.fonts.Font;
import fi.riissanen.gwent.engine.render.fonts.Glyph;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Daniel
 */
public class FontLoader extends AssetLoader {
    
    private static final String PAIR_DELIM = " ";
    private static final String KEY_VALUE_DELIM = "=";
    private static final int META_DATA_LINES = 4;
    
    private static final int DESIRED_PADDING = 3;
    private static final int PAD_TOP = 0;
    private static final int PAD_LEFT = 1;
    private static final int PAD_BOTTOM = 2;
    private static final int PAD_RIGHT = 3;
    
    private final FileUtils files = new FileUtils();
    private List<String> lines = new ArrayList<>();
    private Map<String, String> values = new HashMap<>();
    private Map<Integer, Glyph> glyphs = new HashMap<>();
    
    private int[] padding;
    private int paddingWidth;
    private int paddingHeight;
    
    private int lineHeight;
    private int spaceWidth;
    private int imageWidth;
    private int imageHeight;
    
    private int glyphCount;
    private int currLine;
    
    private String fontFileName;
    private String textureName;
    private Texture fontTexture;
    
    @Override
    public Font load(AssetParams params) {
        reset();
        fontFileName = params.getFilename();
        if (!fontFileName.endsWith(".fnt")) {
            throw new IllegalArgumentException("Font file must be .fnt");
        }
        
        try {
            lines.addAll(files.readLines(fontFileName));
            parseMetaData();
            parseGlyphData();
            loadFontTexture();
            return new Font(fontTexture, glyphs, spaceWidth, lineHeight);
        } catch (IOException ex) {
            log = ex.getMessage();
        }
        return null;
    }
    
    private void reset() {
        lines = new ArrayList<>();
        values = new HashMap<>();
        glyphs = new HashMap<>();
        padding = null;
        paddingWidth = 0;
        paddingHeight = 0;
        lineHeight = 0;
        spaceWidth = 0;
        imageWidth = 0;
        imageHeight = 0;
        glyphCount = 0;
        currLine = 0;
        fontFileName = "";
        textureName = "";
        fontTexture = null;
    }
    
    private void parseMetaData() {
        values.clear();
        for (;currLine < META_DATA_LINES; currLine++) {
            String line = lines.get(currLine);
            values.putAll(files.getKeyValues(
                    line, KEY_VALUE_DELIM, PAIR_DELIM));
        }
        
        // Padding
        String paddingData = values.get("padding");
        String[] paddings = paddingData.split(",");
        padding = new int[paddings.length];
        for (int i = 0; i < paddings.length; i++) {
            padding[i] = Integer.parseInt(paddings[i]);
        }
        paddingWidth = padding[PAD_LEFT] + padding[PAD_RIGHT];
        paddingHeight = padding[PAD_TOP] + padding[PAD_BOTTOM];
        
        // Line height
        lineHeight = getIntegerValue("lineHeight") - paddingHeight;
        
        // Image size
        imageWidth = getIntegerValue("scaleW");
        imageHeight = getIntegerValue("scaleH");
        
        // Glyph count
        glyphCount = getIntegerValue("count");
        
        // Texture name
        File file = new File(fontFileName);
        textureName = file.getParent() + "/" + getStringValue("file");
    }
    
    private void parseGlyphData() {
        for (int i = 0; i < glyphCount; i++) {
            Glyph glyph = loadGlyph();
            if (glyph != null) {
                glyphs.put(glyph.getAsciiCode(), glyph);
            }
        }
    }
    
    private Glyph loadGlyph() {
        String line = lines.get(currLine++);
        values.putAll(files.getKeyValues(
                line, KEY_VALUE_DELIM, PAIR_DELIM));
        int id = getIntegerValue("id");
        
        // Handle space differently since it is not actually a glyph
        if (id == Font.ASCII_SPACE) {
            spaceWidth = getIntegerValue("xadvance") - paddingWidth;
            return null;
        }
        
        int x = getIntegerValue("x");
        int y = getIntegerValue("y");
        int width = getIntegerValue("width");
        int height = getIntegerValue("height");
        
        double u1 = ((double) x + padding[PAD_LEFT] - DESIRED_PADDING) / imageWidth;
        double v1 = ((double) y + padding[PAD_TOP] - DESIRED_PADDING) / imageHeight;
        double u2 = u1 + ((double) width - paddingWidth + 2 * DESIRED_PADDING) / imageWidth;
        double v2 = v1 + ((double) height - paddingHeight + 2 * DESIRED_PADDING) / imageHeight;
        int xOffset = getIntegerValue("xoffset") + padding[PAD_LEFT] - DESIRED_PADDING;
        int yOffset = getIntegerValue("yoffset") + padding[PAD_TOP] - DESIRED_PADDING;
        int xAdvance = getIntegerValue("xadvance");
        return new Glyph(id, u1, v1, u2, v2, xOffset, yOffset, width, height, xAdvance);
    }
    
    private void loadFontTexture() {
        TextureLoader loader = new TextureLoader();
        System.out.println(textureName);
        fontTexture = loader.load(new AssetParams(textureName, Format.RGBA));
    }
    
    private int getIntegerValue(String key) {
        return Integer.parseInt(values.get(key));
    }
    
    private String getStringValue(String key) {
        String value = values.get(key);
        return value.substring(1, value.length() - 1);
    }
    
    public Glyph getGlyph(int asciiCode) {
        return glyphs.get(asciiCode);
    }
}
