package fi.riissanen.gwent.engine.render;

import fi.riissanen.gwent.engine.math.Vector2f;
import fi.riissanen.gwent.engine.math.Vector3f;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author Daniel
 */
public class UniformHandler {

    private int programID;

    /**
     * Creates an uniform handler without an assigned shader program id.
     */
    public UniformHandler() {
    }
    
    /**
     * Creates an uniform handler with a shader program id.
     * @param programID The shader program id
     */
    public UniformHandler(int programID) {
        this.programID = programID;
    }
    
    /**
     * Sets a new program id.
     * @param programID The new program id
     */
    public void setProgramID(int programID) {
        this.programID = programID;
    }
    
    public int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }
    
    /**
     * GLSL has no boolean, loads up a float representing a boolean.
     * 
     * <p>
     * A 0 means false and 1 means true
     * @param location The location of the uniform variable
     * @param value The boolean value
     */
    public void loadBoolean(int location, boolean value) {
        GL20.glUniform1f(location, value ? 1.0f : 0.0f);
    }
    
    /**
     * Loads a float uniform variable.
     * @param location The location of the uniform variable
     * @param value The float to load
     */
    public void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }
    
    /**
     * Loads a vec2 uniform variable.
     * @param location The location of the uniform variable
     * @param vec The vector to load
     */
    public void loadVector2f(int location, Vector2f vec) {
        GL20.glUniform2f(location, vec.x, vec.y);
    }
    
    /**
     * Loads a vec3 uniform variable.
     * @param location The location of the uniform variable
     * @param vec The vector to load
     */
    public void loadVector3f(int location, Vector3f vec) {
        GL20.glUniform3f(location, vec.x, vec.y, vec.z);
    }
}
