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
    private final double xAdvance;
    
    public Glyph(int asciiCode, double u1, double v1, double u2, double v2,
            double xOffset, double yOffset, double xAdvance) {
        this.asciiCode = asciiCode;
        this.u1 = u1;
        this.v1 = v1;
        this.u2 = u2;
        this.v2 = v2;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
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

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public double getxAdvance() {
        return xAdvance;
    }
    
    
}
