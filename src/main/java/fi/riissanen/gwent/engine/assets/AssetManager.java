package fi.riissanen.gwent.engine.assets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Manages asset loading and retrieval.
 * @author Daniel
 */
public class AssetManager {
    
    public static final TextureLoader TEXTURE_LOADER = new TextureLoader();
    public static final FontLoader FONT_LOADER = new FontLoader();
    
    private final Map<AssetLoader, AssetParams> queue = new HashMap<>();
    private final Map<String, Asset> assets = new HashMap<>();
    
    /**
     * Queues an {@code Asset} to be loaded.
     * @param filename The name of the asset file
     * @param loader The loader to load the asset with
     */
    public void load(String filename, AssetLoader loader) {
        AssetParams params = new AssetParams(filename);
        load(loader, params);
    }
    
    /**
     * Queues an {@code Asset} to be loaded.
     * @param loader The asset loader
     * @param params Parameters such as filename
     * @see AssetManager#load(String, AssetLoader) 
     */
    public void load(AssetLoader loader, AssetParams params) {
        queue.put(loader, params);
    }
    
    /**
     * Loads assets into memory from the queue.
     */
    public void processQueue() {
        for (Iterator<AssetLoader> it = queue.keySet().iterator(); it.hasNext();) {
            AssetLoader loader = it.next();
            AssetParams params = queue.get(loader);
            Asset asset = loader.load(params);
            if (asset != null) {
                assets.put(params.getFilename(), asset);
            }
            
            it.remove();
        }
    }
    
    /**
     * Retrieves an {@code Asset} by their filename.
     * @param filename The file name of the asset
     * @return Returns the asset if it has been loaded, otherwise null
     */
    public Asset get(String filename) {
        Asset asset = assets.get(filename);
        if (asset != null) {
            return asset;
        }
        return null;
    }
}
