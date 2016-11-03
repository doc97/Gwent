package fi.riissanen.gwent.engine.assets;

/**
 * Provides parameters for an {@link Asset}
 * @author Daniel
 */
public class AssetParams {
    private String filename;
    private Object[] args;
    
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
