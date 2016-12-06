package fi.riissanen.gwent.engine.render;

import fi.riissanen.gwent.engine.render.shaders.DefaultShader;
import fi.riissanen.gwent.engine.render.shaders.ShaderProgram;
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
 * For batching render calls.
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
    private final Viewport viewport;
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
    
    /**
     * Initializes without a default shader.
     * 
     * <p>
     * The default shader is created with
     * {@code SpriteBatch#createDefaultShader()}.
     */
    public SpriteBatch() {
        this(null);
    }
    
    /**
     * Initializes with a default shader.
     * @param defaultShader The default shader
     */
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
        viewport = new Viewport();

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
        ShaderProgram shader = new DefaultShader();
        if (!shader.isCompiled()) {
            throw new IllegalArgumentException(
                    "Error compiling shader: " + shader.getLog());
        }
        return shader;
    }

    /**
     * Starts a new batch of draw calls.
     * 
     * <p>
     * Must not be called after another begin() call without an end() call in
     * between. Must be called before a draw() call.
     */
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

    /**
     * Ends the batching and flushes the batch.
     */
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

    /**
     * Flushes the batch of draw calls to the graphics card.
     */
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

    /**
     * Draws a texture with it's default size.
     * @param texture The texture
     * @param x The x position (upper-left)
     * @param y The y position (upper-left)
     */ 
    public void draw(Texture texture, float x, float y) {
        draw(texture, x, y, texture.getWidth(), texture.getHeight());
    }
    
    /**
     * Draws a texture with a size.
     * @param texture The texture
     * @param x The x position (upper-left)
     * @param y The y position (upper-left)
     * @param width The width to draw the texture as
     * @param height The height to draw the texture as
     */
    public void draw(Texture texture, float x, float y, float width, float height) {
        draw(texture, x, y, width, height, texture.getUVs(), 0, 0, 0);
    }

    /**
     * Draws a texture with a size, rotation and specified UV coordinates.
     * @param texture The texture
     * @param x The x position (upper-left)
     * @param y The y position (upper-left)
     * @param width The width to draw the texture as
     * @param height The height to draw the texture as
     * @param uvs UV coordinates, can be used to draw a part of the texture
     * @param rotation Rotation in radians
     * @param anchorPX The point of origin around which to rotate
     * @param anchorPY The point of origin around which to rotate
     */
    public void draw(Texture texture, float x, float y, float width, float height,
                    float[] uvs, float rotation, float anchorPX, float anchorPY) {
        Color[] currColorArr = { color, color, color, color };
        draw(texture, x, y + height, x + width, y + height, x + width, y, x, y, uvs,
                currColorArr, rotation, anchorPX, anchorPY);
    }

    /**
     * Draws a texture with a 4 vertex polygon, rotation, UV coordinates and colors.
     * @param texture The texture
     * @param x1 Vertex 1
     * @param y1 Vertex 1
     * @param x2 Vertex 2
     * @param y2 Vertex 2
     * @param x3 Vertex 3
     * @param y3 Vertex 3
     * @param x4 Vertex 4
     * @param y4 Vertex 4
     * @param uvs UV coordinates, can be used to draw a part of the texture
     * @param colours The colors for each vertex (should contain four elements)
     * @param rotation Rotation in radians
     * @param anchorPX The point of origin around which to rotate
     * @param anchorPY The point of origin around which to rotate
     */
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

        // To viewport coordinates
        x1 = viewport.toGlCoordinateX(x1);
        y1 = viewport.toGlCoordinateY(y1);
        x2 = viewport.toGlCoordinateX(x2);
        y2 = viewport.toGlCoordinateY(y2);
        x3 = viewport.toGlCoordinateX(x3);
        y3 = viewport.toGlCoordinateY(y3);
        x4 = viewport.toGlCoordinateX(x4);
        y4 = viewport.toGlCoordinateY(y4);
        
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

    private FloatBuffer storeDataInFloatBuffer(FloatBuffer buffer, float[] data) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Releases all allocated resources.
     */
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
    
    public void setViewport(float left, float bottom, float right, float top) {
        viewport.set(left, right, bottom, top);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * Sets the color components to another color's.
     * @param color The new color
     */
    public void setColor(Color color) {
        color.set(color);
    }

    /**
     * Sets the color by component values.
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @param a The alpha component
     */
    public void setColor(float r, float g, float b, float a) {
        color.set(r, g, b, a);
    }

    /**
     * Sets a new custom shader.
     * @param shader The new shader
     */
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

    /**
     * Gets the custom shader, or if null then the default one.
     * @return The shader
     */
    public ShaderProgram getShader() {
        return (customShader == null) ? defaultShader : customShader;
    }
    
    /**
     * Gets the current viewport.
     * @return The viewport
     */
    public Viewport getViewport() {
        return viewport;
    }
}