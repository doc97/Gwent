/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.riissanen.gwent.engine.assets;

/**
 *
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
