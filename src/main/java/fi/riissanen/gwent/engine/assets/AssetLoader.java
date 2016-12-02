package fi.riissanen.gwent.engine.assets;

/**
 * An abstract loader of an {@code Asset}.
 * @author Daniel
 */
public abstract class AssetLoader {
    protected String log;
    protected AssetParams params;
    
    /**
     * Used to load an asset.
     * @param params The parameters needed for the loading
     * @return The loaded asset
     */
    public abstract Asset load(AssetParams params);
    
    public AssetParams getParameters() {
        return params;
    }
    
    public String getLog() {
        return log;
    }
}
