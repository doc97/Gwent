package fi.riissanen.gwent.engine.render.shaders;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.math.Vector2f;
import fi.riissanen.gwent.engine.math.Vector3f;
import fi.riissanen.gwent.engine.render.Color;
import java.io.IOException;

/**
 * Shader used by fonts.
 * @author Daniel
 */
public class FontShader extends ShaderProgram {

    private int colorUniformLoc;
    private int translationUniformLoc;
    
    /**
     * Creates a font shader from shader files.
     * @param vertexFile The vertex shader file
     * @param fragmentFile The fragment shader file
     * @throws IOException If an I/O error occurs while reading the shader files
     */
    public FontShader(String vertexFile, String fragmentFile) throws IOException {
        super(Engine.INSTANCE.files.readFileAsString(vertexFile),
                Engine.INSTANCE.files.readFileAsString(fragmentFile));
    }

    @Override
    protected void getAllUniformLocations() {
        colorUniformLoc = uniforms.getUniformLocation("color");
        translationUniformLoc = uniforms.getUniformLocation("translation");
    }
    
    /**
     * Loads the color uniform.
     * @param color The color to load
     */
    public void loadColor(Color color) {
        Vector3f vec = new Vector3f(color.getRed(), color.getGreen(), color.getBlue());
        uniforms.loadVector3f(colorUniformLoc, vec);
    }
    
    /**
     * Loads the translation uniform.
     * @param vec The translation vector
     */
    public void loadTranslation(Vector2f vec) {
        uniforms.loadVector2f(translationUniformLoc, vec);
    }
}
