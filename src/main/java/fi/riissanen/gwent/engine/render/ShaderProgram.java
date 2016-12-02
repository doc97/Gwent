package fi.riissanen.gwent.engine.render;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glUseProgram;

/**
 * Represents an OpenGL shader program.
 * @author Daniel
 */
public class ShaderProgram {
    
    public static final String POSITION_ATTRIBUTE = "a_position";
    public static final String COLOR_ATTRIBUTE = "a_color";
    public static final String TEXCOORD_ATTRIBUTE = "a_texCoord";
    
    private boolean isCompiled;
    private boolean validated;
    private int programHandle;
    private int vertexShaderHandle;
    private int fragmentShaderHandle;
    private String vertexShaderSrc;
    private String fragmentShaderSrc;
    private String log = "";
    
    /**
     * Creates an OpenGL shader program with a vertex and fragment shader.
     * @param vertexShader The vertex shader as a {@code String}
     * @param fragmentShader The fragment shader as a {@code String}
     */
    public ShaderProgram(String vertexShader, String fragmentShader) {
        if (vertexShader == null) {
            throw new IllegalStateException("Vertex shader must not be null");
        }
        
        if (fragmentShader == null) {
            throw new IllegalStateException("Fragment shader must not be null");
        }
        
        vertexShaderSrc = vertexShader;
        fragmentShaderSrc = fragmentShader;
        compileShaders(vertexShader, fragmentShader);
    }
    
    private void compileShaders(String vertexSrc, String fragmentSrc) {
        vertexShaderHandle = loadShader(GL20.GL_VERTEX_SHADER, vertexSrc);
        fragmentShaderHandle = loadShader(GL20.GL_FRAGMENT_SHADER, fragmentSrc);
        
        if (vertexShaderHandle == -1 || fragmentShaderHandle == -1) {
            isCompiled = false;
            return;
        }
        
        programHandle = linkProgram(createProgram());
        if (programHandle == -1) {
            isCompiled = false;
            return;
        }
        
        isCompiled = true;
    }
    
    private int loadShader(int type, String src) {
        int shader = GL20.glCreateShader(type);
        if (shader == 0) {
            return -1;
        }
        
        GL20.glShaderSource(shader, src);
        GL20.glCompileShader(shader);
        
        if (GL20.glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            log += GL20.glGetShaderInfoLog(shader);
            return -1;
        }
        return shader;
    }
    
    private int createProgram() {
        int program = GL20.glCreateProgram();
        return program != 0 ? program : -1;
    }
    
    private int linkProgram(int program) {
        if (program == -1) {
            return -1;
        }
        
        GL20.glAttachShader(program, vertexShaderHandle);
        GL20.glAttachShader(program, fragmentShaderHandle);
        GL20.glLinkProgram(program);
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL_FALSE) {
            log += GL20.glGetProgramInfoLog(program);
            return -1;
        }
        return program;
    }
    
    /**
     * Recompiles {@code Shader}'s if not validated.
     */
    public void validate() {
        if (!validated) {
            compileShaders(vertexShaderSrc, fragmentShaderSrc);
            validated = true;
        }
    }
    
    /**
     * Revalidates {@code Shader}'s.
     * Revalidates 
     */
    public void reload() {
        validated = false;
        validate();
    }
    
    /**
     * Called to use the {@code Shader}'s.
     */
    public void start() {
        validate();
        glUseProgram(programHandle);
    }
    
    /**
     * Called to stop using the {@code Shader}'s.
     */
    public void stop() {
        glUseProgram(0);
    }
    
    /**
     * Disposes of allocated resources.
     */
    public void dispose() {
        glUseProgram(0);
        GL20.glDeleteShader(vertexShaderHandle);
        GL20.glDeleteShader(fragmentShaderHandle);
        GL20.glDeleteProgram(programHandle);
    }
    
    /**
     * Flag to indicate if the shader is compiled.
     * @return If the shader is compiled.
     */
    public boolean isCompiled() {
        return isCompiled;
    }
    
    /**
     * The log can provide useful information if something goes wrong.
     * @return The shader log
     */
    public String getLog() {
        if (isCompiled) {
            log = GL20.glGetProgramInfoLog(programHandle);
        }
        return log;
    }
}
