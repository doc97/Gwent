package fi.riissanen.gwent.engine.render;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * For batching render calls
 * @author Daniel
 */
public class SpriteBatch {

    private final ArrayList<Integer> vbos;
    private final IntBuffer intBuff;
    private final FloatBuffer floatBuff1, floatBuff2, floatBuff3;
    private final Color color;
    private Texture lastTexture;
    private ShaderProgram customShader;
    private final ShaderProgram defaultShader;
    private int vao;
    private int ibo;
    private int idx = 0;
    private final int[] indices;
    private final float[] vertices;
    private final float[] texCoords;
    private final float[] colors;
    private float scale = 1;
    private boolean isDrawing;
    private boolean createdShader;
    
    public SpriteBatch() {
        this(null);
    }
    
    public SpriteBatch(ShaderProgram defaultShader) {
        indices = new int[6000];
        vertices = new float[12000];
        texCoords = new float[8000];
        colors = new float[16000];
        vbos = new ArrayList<>();
        intBuff = BufferUtils.createIntBuffer(6000);
        floatBuff1 = BufferUtils.createFloatBuffer(12000);
        floatBuff2 = BufferUtils.createFloatBuffer(8000);
        floatBuff3 = BufferUtils.createFloatBuffer(16000);

        color = new Color(1, 1, 1, 1);

        for (int i = 0, j = 0; i < 6000; i += 6, j += 4) {
            indices[i    ] = j;
            indices[i + 1] = (j + 1);
            indices[i + 2] = (j + 2);
            indices[i + 3] = (j + 2);
            indices[i + 4] = (j + 3);
            indices[i + 5] = j;
        }
        vao = glGenVertexArrays();
        ibo = glGenBuffers();
        
        if (defaultShader == null) {
            this.defaultShader = createDefaultShader();
            createdShader = true;
        } else {
            this.defaultShader = defaultShader;
        }
    }
    
    private ShaderProgram createDefaultShader() {
        String vertexShader = "#version 400\n" //
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
        String fragmentShader = "#version 400\n" //
                + "in vec4 v_color;\n" //
                + "in vec2 v_texCoords;\n" //
                + "out vec4 frag_color;\n" //
                + "uniform sampler2D u_texture;\n" //
                + "void main()\n"//
                + "{\n" //
                + "  frag_color = v_color * texture(u_texture, v_texCoords);\n" //
                + "}";

        ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
        if (!shader.isCompiled()) {
            throw new IllegalArgumentException(
                    "Error compiling shader: " + shader.getLog());
        }
        return shader;
    }

    public void begin() {
        if (isDrawing) {
            throw new IllegalStateException(
                    "SpriteBatch#end() must be called before begin.");
        }
        isDrawing = true;
        if (customShader == null) {
            defaultShader.start();
        } else {
            customShader.start();
        }
    }

    public void end() {
        if (!isDrawing) {
            throw new IllegalStateException(
                "SpriteBatch#begin() must be called before end.");
        }
        flush();
        
        if (customShader == null) {
            defaultShader.start();
        } else {
            customShader.start();
        }
        
        lastTexture = null;
        isDrawing = false;
    }

    public void flush() {
        if (idx == 0) {
            return;
        }
        
        int sprites = idx / 12;
        
        bindVAO();
        loadToVAO(vertices, texCoords, colors, indices);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
        glDrawElements(GL_TRIANGLES, sprites * 6, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);

        unbindVAO();
        glDeleteBuffers(ibo);
        for (int vbo : vbos) {
            glDeleteBuffers(vbo);
        }
        vbos.clear();
        idx = 0;
    }

    private void changeTexture(Texture texture) {
        flush();
        if (texture != null) {
            lastTexture = texture;
            glBindTexture(GL_TEXTURE_2D, texture.getID());
        } else {
            lastTexture = null;
            glBindTexture(GL_TEXTURE_2D, 0);
        }
    }

    public void draw(Texture texture, float x, float y, float width, float height) {
        draw(texture, x, y, width, height, texture.getUVs(), 0, 0, 0);
    }

    public void draw(Texture texture, float x, float y, float width, float height,
                    float[] uvs, float rotation, float anchorPX, float anchorPY) {
        Color[] currColorArr = { color, color, color, color };
        draw(texture, x, y + height, x + width, y + height, x + width, y, x, y, uvs,
                currColorArr, rotation, anchorPX, anchorPY);
    }

    public void draw(Texture texture, float x1, float y1,
            float x2, float y2, float x3, float y3, float x4, float y4,
            float[] uvs, Color[] colours, float rotation,
            float anchorPX, float anchorPY) {

        if (!isDrawing) {
            throw new IllegalStateException(
                "SpriteBatch#begin() must be called before draw.");
        }
        
        if (texture != lastTexture) {
            changeTexture(texture);
        }
        
        if (idx == vertices.length) {
            flush();
        }

        //coords for the vertices
        float vx1 = 2 * scale * x1 - (1 - scale);
        float vy1 = 2 * scale * y1 - (1 - scale);
        float vx2 = 2 * scale * x2 - (1 - scale);
        float vy2 = 2 * scale * y2 - (1 - scale);
        float vx3 = 2 * scale * x3 - (1 - scale);
        float vy3 = 2 * scale * y3 - (1 - scale);
        float vx4 = 2 * scale * x4 - (1 - scale);
        float vy4 = 2 * scale * y4 - (1 - scale);

        int tempidx = this.idx;
        int vertexCount = tempidx / 3;
        int cdx = vertexCount << 2;
        int tdx = vertexCount << 1;

        if (rotation != 0) {
            float p1d = (float) Math.hypot(anchorPX - vx1, anchorPY - vy1);
            float p2d = (float) Math.hypot(anchorPX - vx2, anchorPY - vy2);
            float p3d = (float) Math.hypot(anchorPX - vx3, anchorPY - vy3);
            float p4d = (float) Math.hypot(anchorPX - vx4, anchorPY - vy4);

            double angle = Math.atan2(vy1 - anchorPY, vx1 - anchorPX);
            vx1 = (float) (anchorPX + Math.cos(rotation + angle) * p1d);
            vy1 = (float) (anchorPY + Math.sin(rotation + angle) * p1d);

            angle = Math.atan2(vy2 - anchorPY, vx2 - anchorPX);
            vx2 = (float) (anchorPX + Math.cos(rotation + angle) * p2d);
            vy2 = (float) (anchorPY + Math.sin(rotation + angle) * p2d);

            angle = Math.atan2(vy3 - anchorPY, vx3 - anchorPX);
            vx3 = (float) (anchorPX + Math.cos(rotation + angle) * p3d);
            vy3 = (float) (anchorPY + Math.sin(rotation + angle) * p3d);

            angle = Math.atan2(vy4 - anchorPY, vx4 - anchorPX);
            vx4 = (float) (anchorPX + Math.cos(rotation + angle) * p4d);
            vy4 = (float) (anchorPY + Math.sin(rotation + angle) * p4d);
        }

        vertices[tempidx++] = vx1;
        vertices[tempidx++] = vy1;
        vertices[tempidx++] = 0;
        vertices[tempidx++] = vx2;
        vertices[tempidx++] = vy2;
        vertices[tempidx++] = 0;
        vertices[tempidx++] = vx3;
        vertices[tempidx++] = vy3;
        vertices[tempidx++] = 0;
        vertices[tempidx++] = vx4;
        vertices[tempidx++] = vy4;
        vertices[tempidx++] = 0;

        if (texture != null) {
            texCoords[tdx++] = uvs[0];
            texCoords[tdx++] = uvs[1];

            texCoords[tdx++] = uvs[2];
            texCoords[tdx++] = uvs[1];

            texCoords[tdx++] = uvs[2];
            texCoords[tdx++] = uvs[3];

            texCoords[tdx++] = uvs[0];
            texCoords[tdx++] = uvs[3];
        }

        for (int i = 0; i < 4; i++) {
            colors[cdx++] = colours[i].getRed();
            colors[cdx++] = colours[i].getGreen();
            colors[cdx++] = colours[i].getBlue();
            colors[cdx++] = colours[i].getAlpha();
        }

        this.idx = tempidx;
    }

    private void loadToVAO(float[] positions, float[] texCoords, float[] colors, int[] indices) {
        bindIndicesBuffer(indices, intBuff);
        storeDataInAttributeList(0, floatBuff1, 3, positions);
        storeDataInAttributeList(1, floatBuff2, 2, texCoords);
        storeDataInAttributeList(2, floatBuff3, 4, colors);
    }

    private void bindVAO() {
        glBindVertexArray(vao);
    }

    private void unbindVAO() {
        glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices, IntBuffer buffer) {
        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        storeDataInIntBuffer(buffer, indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(IntBuffer buffer, int[] data) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private void storeDataInAttributeList(int attribute, FloatBuffer buffer, int size, float[] data) {
        int vbo = glGenBuffers();
        vbos.add(vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        storeDataInFloatBuffer(buffer, data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attribute, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @SuppressWarnings("unused")
    private void storeDataInAttributeList(int attribute, IntBuffer buffer, int size, int[] data) {
        int vbo = glGenBuffers();
        vbos.add(vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        storeDataInIntBuffer(buffer, data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attribute, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private FloatBuffer storeDataInFloatBuffer(FloatBuffer buffer, float[] data) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void dispose() {
        glDeleteBuffers(ibo);
        for (int vbo : vbos) {
            glDeleteBuffers(vbo);
        }
        vbos.clear();

        BufferUtils.zeroBuffer(intBuff);
        BufferUtils.zeroBuffer(floatBuff1);
        BufferUtils.zeroBuffer(floatBuff2);
        BufferUtils.zeroBuffer(floatBuff3);
        
        if (createdShader && customShader != null) {
            defaultShader.dispose();
        }
    }
    
    public Color getColor() {
        return color.cpy();
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setColor(Color color) {
        color.set(color);
    }

    public void setColor(float r, float g, float b, float a) {
        color.set(r, g, b, a);
    }

    public void setShader(ShaderProgram shader) {
        if (isDrawing) {
            flush();
            if (customShader == null) {
                defaultShader.stop();
            } else {
                customShader.stop();
            }
        }
        
        customShader = shader;
        
        if (isDrawing) {
            flush();
            if (customShader == null) {
                defaultShader.start();
            } else {
                customShader.start();
            }
        }
    }

    public float getScale() {
        return scale;
    }

    public ShaderProgram getShader() {
        return (customShader == null) ? defaultShader : customShader;
    }
}