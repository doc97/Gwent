package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.engine.assets.AssetManager;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.events.CardStageEvent;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.EventListener;
import fi.riissanen.gwent.game.ui.GUI;
import fi.riissanen.gwent.game.ui.GUICard;

/**
 * When the player wants to play a card he stages it.
 * @author Daniel
 */
public class StageState extends GameStateAdapter implements EventListener {

    private final AssetManager assets;
    private final GUI gui;
    private GUICard guiCard;
    
    /**
     * Constructor.
     * @param game The game instance
     */
    public StageState(Gwent game) {
        super(game);
        gui = game.getGUI();
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
        guiCard = GUI.createGUICard(card, assets);
        guiCard.setSize(GUICard.WIDTH * 2.5f, GUICard.HEIGHT * 2.5f);
        guiCard.setPosition(
                1620 + (300 - guiCard.getWidth()) / 2,
                225);
        if (guiCard.hasText()) {
            game.getTextCache().addText(guiCard.getText());
        }
        gui.addComponent(guiCard);
    }
    
    private void removeGUICard() {
        if (guiCard.hasText()) {
            game.getTextCache().removeText(guiCard.getText());
        }
        gui.removeComponent(guiCard);
    }
}
