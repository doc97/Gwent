package fi.riissanen.gwent.engine.assets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Manages asset loading and retrieval
 * @author Daniel
 */
public class AssetManager {
    
    public static final TextureLoader TEXTURE_LOADER = new TextureLoader();
    
    private final Map<AssetLoader, AssetParams> queue = new HashMap<>();
    private final Map<String, Asset> assets = new HashMap<>();
    
    public void load(String filename, AssetLoader loader) {
        AssetParams params = new AssetParams(filename);
        load(loader, params);
    }
    
    public void load(AssetLoader loader, AssetParams params) {
        queue.put(loader, params);
    }
    
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
    
    public Asset get(String filename) {
        Asset asset = assets.get(filename);
        if (asset != null) {
            return asset;
        }
        return null;
    }
}
