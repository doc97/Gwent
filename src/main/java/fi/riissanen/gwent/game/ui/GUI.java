package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.assets.AssetManager;
import fi.riissanen.gwent.engine.render.Color;
import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.engine.render.fonts.Font;
import fi.riissanen.gwent.engine.render.fonts.Text;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.factions.Faction;
import fi.riissanen.gwent.game.factions.Monsters;
import fi.riissanen.gwent.game.factions.NilfgaardianEmpire;
import fi.riissanen.gwent.game.factions.NorthernKingdoms;
import fi.riissanen.gwent.game.factions.Scoiatael;
import fi.riissanen.gwent.game.input.GUIInput;
import fi.riissanen.gwent.game.input.GUIInputListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Master of the GUI.
 * @author Daniel
 */
public class GUI {

    private final GUIInput input;
    private final GUIRenderer renderer;
    private final List<GUIComponent> components;
    
    /**
     * Constructor.
     */
    public GUI() {
        components = new ArrayList<>();
        renderer = new GUIRenderer();
        input = new GUIInput();
    }
    
    /**
     * Initialization code for GUI.
     */
    public void initialize() {
        input.initialize();
    }
    
    /**
     * Renders the GUI components.
     * @param batch The sprite batch to render with
     */
    public void render(SpriteBatch batch) {
        batch.begin();
        renderer.render(batch, components);
        batch.end();
    }
    
    /**
     * Updates the GUI components.
     */
    public void update() {
        for (GUIComponent component : components) {
            component.update();
        }
    }
    
    /**
     * Add a GUI component.
     * @param component The component to add
     */
    public void addComponent(GUIComponent component) {
        if (component != null) {
            components.add(component);
        }
    }
    
    /**
     * Remove a GUI component.
     * @param component The component to remove
     */
    public void removeComponent(GUIComponent component) {
        components.remove(component);
    }
    
    /**
     * Adds a listener to listen for input for a component.
     * @param component The component to listen to
     * @param listener The listener
     */
    public void addListener(GUIComponent component, GUIInputListener listener) {
        input.addListener(component, listener);
    }
    
    /**
     * Removes a listener from listening for input for a component.
     * @param component The component being listened to
     * @param listener The listener to remove
     */
    public void removeListener(GUIComponent component, GUIInputListener listener) {
        input.removeListener(component, listener);
    }
    
    public GUIInput getInput() {
        return input;
    }
    
    /**
     * Creates a GUI card.
     *
     * <p>
     * It decides what kind of card is being created by comparing with the
     * "instance of"-operator.
     * @param card The card for card data like strength
     * @param assets AssetManager to get textures
     * @param cache Text cache where GUI texts are added
     * @return The created GUICard or null card type cannot be recognized
     */
    public static GUICard createGUICard(Card card, AssetManager assets,
            TextCache cache) {
        if (card instanceof UnitCard) {
            return createGUIUnitCard((UnitCard) card, assets, cache);
        }
        return null;
    }
    
    private static GUICard createGUIUnitCard(UnitCard card, AssetManager assets,
            TextCache cache) {
        Texture cardBase = (Texture) assets.get("assets/textures/cardbase.png");
        Texture cardFaction = (Texture) assets.get("assets/textures/cardfaction.png");

        Font font = (Font) assets.get("assets/fonts/sansserif.fnt");
        int strength = ((UnitCard) card).getUnit().getStrength();
        Text text = new Text(strength + "", font, 1 / 6f, -1);
        text.setColor(0, 0, 0);

        Color color = Color.WHITE;
        Faction faction = card.getOwner().getFaction();
        if (faction instanceof NilfgaardianEmpire) {
            color = new Color(1, 0.71f, 0, 1); // Yellow
        } else if (faction instanceof NorthernKingdoms) {
            color = new Color(0, 0.47f, 1, 1); // Blue
        } else if (faction instanceof Monsters) {
            color = new Color(0.88f, 0, 0, 1); // Red
        } else if (faction instanceof Scoiatael) {
            color = new Color(0.1f, 0.55f, 0, 1); // Green
        }
        
        GUICard guiCard = new GUICard(card, text, cache, cardBase, cardFaction, color);
        guiCard.setSize(GUICard.WIDTH, GUICard.HEIGHT);
        return guiCard;
    }
}
