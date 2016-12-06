package fi.riissanen.gwent.engine.math;

/**
 * A 2D vector.
 * @author Daniel
 */
public class Vector2f {

    public float x;
    public float y;
    
    /**
     * Creates a vector with components set to 0.
     */
    public Vector2f() {
        x = 0;
        y = 0;
    }
    
    /**
     * Creates a vector with x and y components.
     * @param x The x component
     * @param y The y component
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Sets the vector components.
     * @param x The x component
     * @param y The y component
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Adds a vector to this one.
     * @param vec The vector to add
     */
    public void add(Vector2f vec) {
        this.x += vec.x;
        this.y += vec.y;
    }
    
    /**
     * Subtracts a vector from this one.
     * @param vec The vector to subtract
     */
    public void sub(Vector2f vec) {
        this.x -= vec.x;
        this.y -= vec.y;
    }
    
    /**
     * Scales this vector with a scalar.
     * @param scale The scalar
     */
    public void scl(float scale) {
        this.x *= scale;
        this.y *= scale;
    }
    
    /**
     * Returns a copy of this vector.
     * @return The copy of this vector
     */
    public Vector2f cpy() {
        return new Vector2f(x, y);
    }
}
