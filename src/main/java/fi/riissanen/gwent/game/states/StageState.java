package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.assets.AssetManager;
import fi.riissanen.gwent.engine.render.Viewport;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.events.CardStageEvent;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.EventListener;
import fi.riissanen.gwent.game.ui.GUI;
import fi.riissanen.gwent.game.ui.GUICard;
import fi.riissanen.gwent.game.ui.TextCache;

/**
 * When the player wants to play a card he stages it.
 * @author Daniel
 */
public class StageState extends GameStateAdapter implements EventListener {

    private final AssetManager assets;
    private final TextCache cache;
    private final GUI gui;
    private GUICard guiCard;
    
    /**
     * Constructor.
     * @param game The game instance
     */
    public StageState(Gwent game) {
        super(game);
        gui = game.getGUI();
        cache = game.getTextCache();
        assets = game.getAssetManager();
    }
    
    @Override
    public void process(Event event) {
        if (event instanceof CardStageEvent) {
            boolean staged = ((CardStageEvent) event).isStaged();
            if (staged) {
                Card card = ((CardStageEvent) event).getCard();
                addGUICard(card);
            } else {
                removeGUICard();
            }
        } else if (event instanceof CardPlayedEvent) {
            removeGUICard();
        }
    }
    
    private void addGUICard(Card card) {
        guiCard = GUI.createGUICard(card, assets, cache);
        guiCard.setSize(GUICard.WIDTH * 3.5f, GUICard.HEIGHT * 3.5f);
        Viewport viewport = Engine.INSTANCE.batch.getViewport();
        guiCard.setPosition(
                viewport.getSrcWidth() - 400 + (400 - guiCard.getWidth()) / 2,
                (viewport.getSrcHeight() - guiCard.getHeight()) / 2);
        gui.addComponent(guiCard);
    }
    
    private void removeGUICard() {
        guiCard.destroy();
        gui.removeComponent(guiCard);
    }
}
