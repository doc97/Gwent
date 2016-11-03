package fi.riissanen.gwent.engine.assets;

/**
 *
 * @author Daniel
 */
public abstract class AssetLoader {
    protected String log;
    protected AssetParams params;
    
    public abstract Asset load(AssetParams params);
    public AssetParams getParameters() { return params; }
    public String getLog() { return log; }
}
