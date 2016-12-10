package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.assets.AssetManager;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.engine.render.fonts.Text;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.events.CardStageEvent;
import fi.riissanen.gwent.game.events.DrawCardEvent;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.EventListener;
import fi.riissanen.gwent.game.ui.GUI;
import fi.riissanen.gwent.game.ui.GUICard;
import fi.riissanen.gwent.game.ui.GUIComponent;
import fi.riissanen.gwent.game.ui.GUIHand;
import fi.riissanen.gwent.game.ui.GUIRow;

/**
 * The normal state, where the player chooses a card from their hand.
 * @author Daniel
 */
public class NormalState extends GameStateAdapter implements EventListener {

    private final AssetManager assets;
    private GUIComponent board;
    private GUIHand hand;
    private final GUIRow[] friendlyRows;
    private final GUIRow[] enemyRows;
    private final GUI gui;
    
    /**
     * Initializes the normal game state.
     * @param game The instance to the game
     */
    public NormalState(Gwent game) {
        super(game);
        this.gui = game.getGUI();
        assets = game.getAssetManager();
        friendlyRows = new GUIRow[3];
        enemyRows = new GUIRow[3];
    }
    
    @Override
    public void createGUI() {
        Texture boardTex = (Texture) assets.get("assets/textures/board.png");
        board = new GUIComponent(boardTex);
        board.setSize(Engine.INSTANCE.batch.getViewport().getWidth(),
                Engine.INSTANCE.batch.getViewport().getHeight());
        
        Texture handTex = (Texture) assets.get("assets/textures/hand.png");
        hand = new GUIHand(handTex, game.getTextCache());
        hand.setPosition(300, 20);
        hand.setSize(Engine.INSTANCE.batch.getViewport().getWidth() - 300 - 20, 110);
        
        Texture rowTex = (Texture) assets.get("assets/lwjgl.png");
        float half = Engine.INSTANCE.batch.getViewport().getHeight() / 2 + hand.getHeight();
        for (int i = 0; i < 3; i++) {
            friendlyRows[i] = new GUIRow(rowTex, game.getTextCache());
            friendlyRows[i].setPosition(300,
                    half - (i + 1) * 115);
            friendlyRows[i].setSize(
                    Engine.INSTANCE.batch.getViewport().getWidth() - 300 - 20,
                    105);
            enemyRows[i] = new GUIRow(rowTex, game.getTextCache());
            enemyRows[i].setPosition(300,
                    half + i * 115);
            enemyRows[i].setSize(
                    Engine.INSTANCE.batch.getViewport().getWidth() - 300 - 20,
                    105);
        }
    }

    @Override
    public void create() {
        gui.addComponent(board);
        gui.addComponent(hand);
        for (int i = 0; i < 3; i++) {
            gui.addComponent(friendlyRows[i]);
            gui.addComponent(enemyRows[i]);
        }
    }
    
    @Override
    public void destroy() {
        gui.removeComponent(board);
        gui.removeComponent(hand);
        for (int i = 0; i < 3; i++) {
            gui.removeComponent(friendlyRows[i]);
            gui.removeComponent(enemyRows[i]);
        }
    }

    @Override
    public void process(Event event) {
        if (event instanceof DrawCardEvent) {
            Card card = ((DrawCardEvent) event).getCard();
            boolean friendly = ((DrawCardEvent) event).wasFriendly();
            if (friendly) {
                hand.addCard(card, createGUICard(card));
            }
        } else if (event instanceof CardStageEvent) {
            Card card = ((CardStageEvent) event).getCard();
            boolean staged = ((CardStageEvent) event).cardIsStaged();
            if (staged) {
                hand.removeCard(card);
            } else {
                hand.addCard(card, createGUICard(card));
            }
        } else if (event instanceof CardPlayedEvent) {
            Card card = ((CardPlayedEvent) event).getCard();
            int row = ((CardPlayedEvent) event).getRowIndex();
            boolean friendly = card.getOwner().isFriendly();
            if (friendly) {
                friendlyRows[row].addCard(card, createGUICard(card));
            } else {
                enemyRows[row].addCard(card, createGUICard(card));
            }
        }
    }
    
    private GUICard createGUICard(Card card) {
        Texture texture = (Texture) assets.get("assets/lwjgl.png");
        Text text = null;
        if (card instanceof UnitCard) {
            int strength = ((UnitCard) card).getUnit().getStrength();
            text = new Text(strength + "", game.getFont(), 1/6f, -1);
        }
        GUICard guiCard = new GUICard(text, texture);
        guiCard.setSize(60, 100);
        return guiCard;
    }
}
