package fi.riissanen.gwent.engine.assets;

/**
 * Container for parameters for an {@link Asset}.
 * @author Daniel
 */
public class AssetParams {
    private final String filename;
    private final Object[] args;
    
    /**
     * Stores the parameters.
     * @param filename The filename of the asset
     * @param args Other parameters
     */
    public AssetParams(String filename, Object... args) {
        this.filename = filename;
        this.args = args;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public Object[] getAdditionalParams() {
        return args;
    }
}
