package fi.riissanen.gwent.engine.render.shaders;

/**
 * The default shader.
 * @author Daniel
 */
public class DefaultShader extends ShaderProgram {

    private static final String VERTEX_SHADER = "#version 400\n" //
        + "layout(location = 0) in vec3 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
        + "layout(location = 1) in vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + ";\n" //
        + "layout(location = 2) in vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
        + "out vec4 v_color;\n" //
        + "out vec2 v_texCoords;\n" //
        + "\n" //
        + "void main()\n" //
        + "{\n" //
        + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
        + "   v_color.a = v_color.a * (255.0/254.0);\n" //
        + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + ";\n" //
        + "   gl_Position = vec4(" + ShaderProgram.POSITION_ATTRIBUTE + ", 1.0);\n" //
        + "}\n";
    private static final String FRAGMENT_SHADER = "#version 400\n" //
        + "in vec4 v_color;\n" //
        + "in vec2 v_texCoords;\n" //
        + "out vec4 frag_color;\n" //
        + "uniform sampler2D u_texture;\n" //
        + "void main()\n"//
        + "{\n" //
        + "  frag_color = v_color * texture(u_texture, v_texCoords);\n" //
        + "}";
    
    /**
     * Creates a default shader.
     */
    public DefaultShader() {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
    }

    @Override
    protected void getAllUniformLocations() {
    }
}
