package fi.riissanen.gwent.engine.render.fonts;

import fi.riissanen.gwent.engine.math.Vector2f;
import fi.riissanen.gwent.engine.render.Color;

/**
 * Represents a text with a font.
 * 
 * <p>
 * It has support for multi-line text
 * @author Daniel
 */
public class Text {

    private final String text;
    private final Font font;
    private final TextMeshData meshData;
    private final Vector2f position;
    private final Color color;
    private final float lineLength;
    private final float fontSize;
    
    private int numberOfLines;
    
    /**
     * Creates a text.
     * @param text The string of text
     * @param font The font to use
     * @param fontSize The relative scale of the font
     * @param lineLength The length of one line of text, -1 indicates no limit
     */
    public Text(String text, Font font, float fontSize, float lineLength) {
        this.text = text;
        this.font = font;
        this.fontSize = fontSize;
        this.lineLength = lineLength;
        position = new Vector2f();
        color = new Color(1, 1, 1, 1);
        meshData = new TextMeshData();
    }

    /**
     * Sets the color's components.
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     */
    public void setColor(float r, float g, float b) {
        color.set(r, g, b, 1);
    }
    
    /**
     * Sets the position of the text.
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public void setPosition(float x, float y) {
        position.set(x, y);
    }
    
    public void setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
    }
    
    public String getText() {
        return text;
    }
    
    public Font getFont() {
        return font;
    }
    
    public Color getColor() {
        return color.cpy();
    }
    
    public Vector2f getPosition() {
        return position;
    }
    
    public TextMeshData getMeshData() {
        return meshData;
    }
    
    public float getFontSize() {
        return fontSize;
    }
    
    public float getLineLength() {
        return lineLength;
    }
    
    public int getNumberOfLines() {
        return numberOfLines;
    }
}
