package fi.riissanen.gwent.engine.render;

import fi.riissanen.gwent.engine.math.Vector2f;

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
    
    public Viewport() {
        this(-0.5f, 0.5f, -0.5f, 0.5f);
    }
    
    public Viewport(float left, float right, float bottom, float top) {
        this.left = left;
        this.bottom = bottom;
        this.width = right - left;
        this.height = top - bottom;
    }
    
    public float toGlCoordinateX(float x) {
        float relativeX = x - left;
        float transformedX = glLeft + (relativeX / width) * glWidth;
        return transformedX;
    }
    
    public float toGlCoordinateY(float y) {
        float relativeY = y - bottom;
        float transformedY = glBottom + (relativeY / height) * glHeight;
        return transformedY;
    }
    
    public void set(float left, float right, float bottom, float top) {
        this.left = left;
        this.bottom = bottom;
        this.width = right - left;
        this.height = top - bottom;
    }
    
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
