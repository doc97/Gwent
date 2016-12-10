package fi.riissanen.gwent.engine.render.fonts;

/**
 * Contains data about a glyph.
 * @author Daniel
 */
public class Glyph {

    private final int asciiCode;
    private final double u1;
    private final double v1;
    private final double u2;
    private final double v2;
    private final double xOffset;
    private final double yOffset;
    private final double width;
    private final double height;
    private final double xAdvance;
    
    /**
     * Creates a glyph.
     * @param asciiCode The ASCII code of the character
     * @param u1 The start U coordinate
     * @param v1 The start V coordinate
     * @param u2 The end U coordinate
     * @param v2 The end V coordinate
     * @param xOffset The x offset to draw glyph from cursor
     * @param yOffset The y offset to draw glyph from cursor
     * @param width The width of the glyph
     * @param height The height of the glyph
     * @param xAdvance The amount to advance cursor after this glyph
     */
    public Glyph(int asciiCode, double u1, double v1, double u2, double v2,
            double xOffset, double yOffset, double width, double height,
            double xAdvance) {
        this.asciiCode = asciiCode;
        this.u1 = u1;
        this.v1 = v1;
        this.u2 = u2;
        this.v2 = v2;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.xAdvance = xAdvance;
    }

    public int getAsciiCode() {
        return asciiCode;
    }

    public double getU1() {
        return u1;
    }

    public double getV1() {
        return v1;
    }

    public double getU2() {
        return u2;
    }

    public double getV2() {
        return v2;
    }

    public double getXOffset() {
        return xOffset;
    }

    public double getYOffset() {
        return yOffset;
    }

    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
    
    public double getXAdvance() {
        return xAdvance;
    }
}
