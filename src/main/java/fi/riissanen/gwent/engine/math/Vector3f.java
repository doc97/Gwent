package fi.riissanen.gwent.engine.math;

/**
 * A 3D vector.
 * @author Daniel
 */
public class Vector3f {

    public float x;
    public float y;
    public float z;
    
    /**
     * Creates a vector with components set to 0.
     */
    public Vector3f() {
        x = 0;
        y = 0;
        z = 0;
    }
    
    /**
     * Creates a vector with x, y, z components.
     * @param x The x component
     * @param y The y component
     * @param z The z component
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Sets the vector components.
     * @param x The x component
     * @param y The y component
     * @param z The z component
     */
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Adds a vector to this one.
     * @param vec The vector to add
     */
    public void add(Vector3f vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
    }
    
    /**
     * Subtracts a vector from this one.
     * @param vec The vector to subtract
     */
    public void sub(Vector3f vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
    }
    
    /**
     * Scales this vector with a scalar.
     * @param scale The scalar
     */
    public void scl(float scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
    }
    
    /**
     * Returns a copy of this vector.
     * @return The copy of this vector
     */
    public Vector3f cpy() {
        return new Vector3f(x, y, z);
    }
}
