package fi.riissanen.gwent.engine.render;

import fi.riissanen.gwent.engine.assets.Asset;

/**
 * A wrapper around the OpenGL texture
 * @author Daniel
 */
public class Texture implements Asset {
    private int id;
    private float width, height;
    private final float[] uvs;
    
    public Texture(int textureID, float width, float height) {
        id = textureID;
        this.width = width;
        this.height = height;
        uvs = new float[] {0, 0, 1, 1};
    }
    
    public void setID(int textureID) {
        id = textureID;
    }
    
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    public void setUVs(float[] uvs) {
        if (uvs.length == 4) {
            this.uvs[0] = Math.min(Math.max(uvs[0], 0), 1);
            this.uvs[1] = Math.min(Math.max(uvs[0], 0), 1);
            this.uvs[2] = Math.min(Math.max(uvs[0], 0), 1);
            this.uvs[3] = Math.min(Math.max(uvs[0], 0), 1);
        }
    }
    
    public int getID() {
        return id;
    }

    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public float[] getUVs() {
        return uvs;
    }
}
