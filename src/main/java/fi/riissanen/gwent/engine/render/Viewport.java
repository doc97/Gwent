package fi.riissanen.gwent.engine.render;

/**
 * Used to control the window viewport.
 * @author Daniel
 */
public class Viewport {

    private float glLeft = -0.5f;
    private float glBottom = -0.5f;
    private float glWidth = 1.0f;
    private float glHeight = 1.0f;
    private float left;
    private float bottom;
    private float width;
    private float height;
    
    /**
     * Initializes the viewport to glViewport's default values.
     */
    public Viewport() {
        this(-0.5f, 0.5f, -0.5f, 0.5f);
    }
    
    /**
     * Initializes the viewport to specified values.
     * @param left The left of the viewport
     * @param right The right of the viewport
     * @param bottom The bottom of the viewport
     * @param top The top of the viewport
     */
    public Viewport(float left, float right, float bottom, float top) {
        this.left = left;
        this.bottom = bottom;
        this.width = right - left;
        this.height = top - bottom;
    }
    
    /**
     * Transforms a x coordinate in viewport space to glViewport space.
     * @param x The x coordinate
     * @return The transformed coordinate
     */
    public float toGlCoordinateX(float x) {
        float relativeX = x - left;
        float transformedX = glLeft + (relativeX / width) * glWidth;
        return transformedX;
    }
    
    /**
     * Transforms an y coordinate in viewport space to glViewport space.
     * @param y The y coordinate
     * @return The transformed coordinate
     */
    public float toGlCoordinateY(float y) {
        float relativeY = y - bottom;
        float transformedY = glBottom + (relativeY / height) * glHeight;
        return transformedY;
    }
    
    /**
     * Sets the viewport coordinates.
     * @param left The left of the viewport
     * @param right The right of the viewport
     * @param bottom The bottom of the viewport
     * @param top The top of the viewport
     */
    public void set(float left, float right, float bottom, float top) {
        this.left = left;
        this.bottom = bottom;
        this.width = right - left;
        this.height = top - bottom;
    }
    
    /**
     * Sets the glViewport coordinates.
     * @param glLeft The left of the viewport
     * @param glRight The right of the viewport
     * @param glBottom The bottom of the viewport
     * @param glTop The top of the viewport
     */
    public void glViewport(float glLeft, float glRight, float glBottom, float glTop) {
        this.glLeft = glLeft;
        this.glBottom = glBottom;
        this.glWidth = glRight - glLeft;
        this.glHeight = glTop - glBottom;
    }
    
    public float getGlLeft() {
        return glLeft;
    }
    
    public float getGlWidth() {
        return glWidth;
    }

    public float getGlBottom() {
        return glBottom;
    }
    
    public float getGlHeight() {
        return glHeight;
    }
    
    public float getLeft() {
        return left;
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getBottom() {
        return bottom;
    }
    
    public float getHeight() {
        return height;
    }
}
