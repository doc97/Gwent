package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.assets.AssetManager;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.engine.render.fonts.Font;
import fi.riissanen.gwent.engine.render.fonts.Text;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.CombatRow;
import fi.riissanen.gwent.game.combat.GameBoard;
import fi.riissanen.gwent.game.combat.UnitType;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.events.CardStageEvent;
import fi.riissanen.gwent.game.events.DrawCardEvent;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.EventListener;
import fi.riissanen.gwent.game.input.GUIHandInput;
import fi.riissanen.gwent.game.input.GUIRowInput;
import fi.riissanen.gwent.game.ui.GUI;
import fi.riissanen.gwent.game.ui.GUICard;
import fi.riissanen.gwent.game.ui.GUIComponent;
import fi.riissanen.gwent.game.ui.GUIHand;
import fi.riissanen.gwent.game.ui.GUIRow;
import fi.riissanen.gwent.game.ui.TextCache;

/**
 * The normal state, where the player chooses a card from their hand.
 * @author Daniel
 */
public class NormalState extends GameStateAdapter implements EventListener {

    private final GUIHandInput handInput;
    private final GUIRowInput rowInput;
    private final AssetManager assets;
    private final TextCache cache;
    private final GUIRow[] friendlyRows;
    private final GUIRow[] enemyRows;
    private final GUI gui;
    private GUIComponent guiBoard;
    private GUIHand guiHand;
    
    /**
     * Initializes the normal game state.
     * @param game The instance to the game
     */
    public NormalState(Gwent game) {
        super(game);
        gui = game.getGUI();
        cache = game.getTextCache();
        assets = game.getAssetManager();
        handInput = new GUIHandInput(game);
        rowInput = new GUIRowInput(game);
        friendlyRows = new GUIRow[3];
        enemyRows = new GUIRow[3];
    }
    
    @Override
    public void createGUI() {
        Texture boardTex = (Texture) assets.get("assets/textures/board.png");
        guiBoard = new GUIComponent(boardTex);
        guiBoard.setSize(Engine.INSTANCE.batch.getViewport().getSrcWidth(),
                Engine.INSTANCE.batch.getViewport().getSrcHeight());
        
        Texture handTex = (Texture) assets.get("assets/textures/row.png");
        guiHand = new GUIHand(handTex, game.getTextCache());
        guiHand.setPosition(300, 20);
        guiHand.setSize(Engine.INSTANCE.batch.getViewport().getSrcWidth() - 2 * 300 - 20,
                GUICard.HEIGHT + 10);
        
        Font font = (Font) assets.get("assets/fonts/sansserif.fnt");
        Texture rowTex = (Texture) assets.get("assets/textures/row.png");
        float half = (Engine.INSTANCE.batch.getViewport().getSrcHeight() + guiHand.getHeight() + 20) / 2;
        GameBoard board = game.getGameSystem().getBoard();
        for (int i = 0; i < 3; i++) {
            Text zero = new Text("0", font, 1 / 4f, -1);
            zero.setColor(1, 1, 1);
            CombatRow fRow = board.getRow(true, UnitType.values()[i]);
            friendlyRows[i] = new GUIRow(fRow, rowTex, zero, game.getTextCache());
            friendlyRows[i].setSize(
                    Engine.INSTANCE.batch.getViewport().getSrcWidth() - 2 * 300 - 20,
                    GUICard.HEIGHT + 10);
            friendlyRows[i].setPosition(300,
                    half - (i + 1) * (friendlyRows[i].getHeight() + 10));
            
            zero = new Text("0", font, 1 / 4f, -1);
            zero.setColor(1, 1, 1);
            CombatRow eRow = board.getRow(false, UnitType.values()[i]);
            enemyRows[i] = new GUIRow(eRow, rowTex, zero, game.getTextCache());
            enemyRows[i].setSize(
                    Engine.INSTANCE.batch.getViewport().getSrcWidth() - 2 * 300 - 20,
                    GUICard.HEIGHT + 10);
            enemyRows[i].setPosition(300,
                    half + i * (enemyRows[i].getHeight() + 10));
        }
    }

    @Override
    public void create() {
        gui.addComponent(guiBoard);
        gui.addComponent(guiHand);
        for (int i = 0; i < 3; i++) {
            gui.addComponent(friendlyRows[i]);
            gui.addComponent(enemyRows[i]);
        }
    }
    
    @Override
    public void destroy() {
        gui.removeComponent(guiBoard);
        gui.removeComponent(guiHand);
        for (int i = 0; i < 3; i++) {
            gui.removeComponent(friendlyRows[i]);
            gui.removeComponent(enemyRows[i]);
        }
    }
    
    @Override
    public void enter() {
        handInput.enable();
        rowInput.disable();
    }
    
    @Override
    public void exit() {
        handInput.disable();
    }

    @Override
    public void process(Event event) {
        if (event instanceof DrawCardEvent) {
            processDrawCard((DrawCardEvent) event);
        } else if (event instanceof CardStageEvent) {
            processCardStage((CardStageEvent) event);
        } else if (event instanceof CardPlayedEvent) {
            processCardPlayed((CardPlayedEvent) event);
        }
    }
    
    private void processDrawCard(DrawCardEvent event) {
        Card card = event.getCard();
        boolean friendly = event.isFriendly();
        if (friendly) {
            guiHand.addCard(card, GUI.createGUICard(card, assets, cache));
            gui.getInput().addListener(guiHand.getGUICard(card), handInput);
        }
    }
    
    private void processCardStage(CardStageEvent event) {
        Card card = event.getCard();
        boolean staged = event.isStaged();
        if (staged) {
            rowInput.load(gui.getInput(), friendlyRows, card);
            rowInput.enable();
            gui.getInput().removeListener(guiHand.getGUICard(card), handInput);
            guiHand.removeCard(card);
        } else {
            guiHand.addCard(card, GUI.createGUICard(card, assets, cache));
            gui.getInput().addListener(guiHand.getGUICard(card), handInput);
            gui.getInput().removeListenerFromAll(rowInput);
        }
    }
    
    private void processCardPlayed(CardPlayedEvent event) {
        Card card = event.getCard();
        if (card instanceof UnitCard) {
            int row = event.getRowIndex();
            boolean friendly = card.getOwner().isFriendly();
            GUICard guiCard = GUI.createGUICard(card, assets, cache);
            if (friendly) {
                friendlyRows[row].addCard((UnitCard) card, guiCard);
            } else {
                enemyRows[row].addCard((UnitCard) card, guiCard);
            }
        }
    }
}
