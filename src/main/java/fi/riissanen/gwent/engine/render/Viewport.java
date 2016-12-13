package fi.riissanen.gwent.engine.render;

/**
 * Used to control the window viewport.
 * @author Daniel
 */
public class Viewport {

    private float targetLeft = -0.5f;
    private float targetBottom = -0.5f;
    private float targetWidth = 1.0f;
    private float targetHeight = 1.0f;
    private float srcLeft;
    private float srcBottom;
    private float srcWidth;
    private float srcHeight;
    
    /**
     * Initializes the viewport to glViewport's default values.
     */
    public Viewport() {
        this(-0.5f, 0.5f, -0.5f, 0.5f);
    }
    
    /**
     * Initializes the source viewport to specified values.
     * @param srcLeft The left of the viewport
     * @param srcRight The right of the viewport
     * @param srcBottom The bottom of the viewport
     * @param srcTop The top of the viewport
     */
    public Viewport(float srcLeft, float srcRight, float srcBottom, float srcTop) {
        this.srcLeft = srcLeft;
        this.srcBottom = srcBottom;
        this.srcWidth = srcRight - srcLeft;
        this.srcHeight = srcTop - srcBottom;
    }
    
    /**
     * Transforms a x coordinate in viewport space to target viewport space.
     * @param x The x coordinate
     * @return The transformed coordinate
     */
    public float toTargetCoordinateX(float x) {
        float relativeX = x - srcLeft;
        float transformedX = targetLeft + (relativeX / srcWidth) * targetWidth;
        return transformedX;
    }
    
    /**
     * Transforms an y coordinate in viewport space to target viewport space.
     * @param y The y coordinate
     * @return The transformed coordinate
     */
    public float toTargetCoordinateY(float y) {
        float relativeY = y - srcBottom;
        float transformedY = targetBottom + (relativeY / srcHeight) * targetHeight;
        return transformedY;
    }
    
    /**
     * Sets the source viewport coordinates.
     * @param srcLeft The left of the viewport
     * @param srcBottom The bottom of the viewport
     * @param srcRight The right of the viewport
     * @param srcTop The top of the viewport
     */
    public void sourceViewport(float srcLeft, float srcBottom, float srcRight, float srcTop) {
        this.srcLeft = srcLeft;
        this.srcBottom = srcBottom;
        this.srcWidth = srcRight - srcLeft;
        this.srcHeight = srcTop - srcBottom;
    }
    
    /**
     * Sets the target viewport coordinates.
     * @param targetLeft The left of the viewport
     * @param targetBottom The bottom of the viewport
     * @param targetRight The right of the viewport
     * @param targetTop The top of the viewport
     */
    public void targetViewport(float targetLeft, float targetBottom, float targetRight, float targetTop) {
        this.targetLeft = targetLeft;
        this.targetBottom = targetBottom;
        this.targetWidth = targetRight - targetLeft;
        this.targetHeight = targetTop - targetBottom;
    }
    
    public float getTargetLeft() {
        return targetLeft;
    }
    
    public float getTargetWidth() {
        return targetWidth;
    }

    public float getTargetBottom() {
        return targetBottom;
    }
    
    public float getTargetHeight() {
        return targetHeight;
    }
    
    public float getSrcLeft() {
        return srcLeft;
    }
    
    public float getSrcWidth() {
        return srcWidth;
    }
    
    public float getSrcBottom() {
        return srcBottom;
    }
    
    public float getSrcHeight() {
        return srcHeight;
    }
}
