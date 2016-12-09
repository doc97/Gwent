package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.assets.AssetManager;
import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.EventListener;
import fi.riissanen.gwent.game.events.MatchStartEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Master of the GUI.
 * @author Daniel
 */
public class GUI {

    private final AssetManager assets;
    private final GUIRenderer renderer;
    private final List<GUIComponent> components;
    private GUIComponent board;
    private GUIHand hand; 
    
    public GUI(AssetManager assets) {
        this.assets = assets;
        components = new ArrayList<>();
        renderer = new GUIRenderer();
    }
    
    public void render(SpriteBatch batch) {
        batch.begin();
        renderer.render(batch, components);
        batch.end();
    }
    
    public void addComponent(GUIComponent component) {
        components.add(component);
    }
    
    public void removeComponent(GUIComponent component) {
        components.remove(component);
    }
}
