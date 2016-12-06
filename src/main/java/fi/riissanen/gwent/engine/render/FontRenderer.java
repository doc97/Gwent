package fi.riissanen.gwent.engine.render;

import fi.riissanen.gwent.engine.render.fonts.Font;
import fi.riissanen.gwent.engine.render.shaders.FontShader;
import fi.riissanen.gwent.engine.render.fonts.Text;
import fi.riissanen.gwent.engine.render.fonts.TextMeshData;
import fi.riissanen.gwent.engine.render.shaders.ShaderProgram;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Daniel
 */
public class FontRenderer {

    private FontShader shader;
    
    public void setShader(FontShader shader) {
        this.shader = shader;
    }
    
    public void render(SpriteBatch batch, Map<Font, List<Text>> texts) {
        ShaderProgram oldShader = batch.getShader();
        batch.setShader(shader);
        batch.begin();
        for (Font font : texts.keySet()) {
            for (Text text : texts.get(font)) {
                if (shader != null) {
                    shader.loadColor(text.getColor());
                }
                
                TextMeshData data = text.getMeshData();
                float[] quadData = data.getQuadData();
                float[] uvs = data.getUVs();
                int glyphCount = data.getGlyphCount();
                int vertexCount = data.getVertexCount();
                int vertexPerGlyph = vertexCount / glyphCount;
                float[] glyphUVs = new float[vertexPerGlyph];
                for (int i = 0; i < vertexCount; i += vertexPerGlyph) {
                    for (int j = 0; j < glyphUVs.length; j++) {
                        glyphUVs[j] = uvs[i + j];
                    }
                    
                    batch.draw(font.getFontTexture(),
                            quadData[i] + text.getPosition().x,
                            quadData[i + 1] + text.getPosition().y,
                            quadData[i + 2], quadData[i + 3],
                            glyphUVs,
                            0, 0, 0);
                }
            }
        }
        batch.end();
        batch.setShader(oldShader);
    }
}
