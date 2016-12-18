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
import fi.riissanen.gwent.game.events.CardDiscardedEvent;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.events.CardStageEvent;
import fi.riissanen.gwent.game.events.DrawCardEvent;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.EventListener;
import fi.riissanen.gwent.game.input.GUIHandInput;
import fi.riissanen.gwent.game.input.GUIRowInput;
import fi.riissanen.gwent.game.input.NormalStateInput;
import fi.riissanen.gwent.game.ui.GUI;
import fi.riissanen.gwent.game.ui.GUICard;
import fi.riissanen.gwent.game.ui.GUIComponent;
import fi.riissanen.gwent.game.ui.GUIDiscardPile;
import fi.riissanen.gwent.game.ui.GUIHealth;
import fi.riissanen.gwent.game.ui.GUIHand;
import fi.riissanen.gwent.game.ui.GUIRow;
import fi.riissanen.gwent.game.ui.TextCache;

/**
 * The normal state, where the player chooses a card from their hand.
 * @author Daniel
 */
public class NormalState extends GameStateAdapter implements EventListener {

    private final NormalStateInput input;
    private final GUIHandInput handInput;
    private final GUIRowInput rowInput;
    private final AssetManager assets;
    private final TextCache cache;
    private final GUIRow[] friendlyRows;
    private final GUIRow[] enemyRows;
    private final GUI gui;
    private GUIComponent guiOverlay;
    private GUIComponent guiBoard;
    private GUIDiscardPile guiDiscard;
    private GUIHealth guiHealth;
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
        input = new NormalStateInput(game);
        handInput = new GUIHandInput(game);
        rowInput = new GUIRowInput(game);
        friendlyRows = new GUIRow[3];
        enemyRows = new GUIRow[3];
    }
    
    @Override
    public void createGUI() {
        Texture darkTex = (Texture) assets.get("assets/textures/dark.png");
        guiOverlay = new GUIComponent(darkTex);
        guiOverlay.setSize(Engine.INSTANCE.batch.getViewport().getSrcWidth(),
                Engine.INSTANCE.batch.getViewport().getSrcHeight());
        
        Texture boardTex = (Texture) assets.get("assets/textures/board.png");
        guiBoard = new GUIComponent(boardTex);
        guiBoard.setSize(Engine.INSTANCE.batch.getViewport().getSrcWidth(),
                Engine.INSTANCE.batch.getViewport().getSrcHeight());
        
        Texture handTex = (Texture) assets.get("assets/textures/row.png");
        guiHand = new GUIHand(handTex);
        guiHand.setPosition(400, 40);
        guiHand.setSize(Engine.INSTANCE.batch.getViewport().getSrcWidth() - 2 * 400 - 20,
                GUICard.HEIGHT + 10);
        
        Texture discardTex = (Texture) assets.get("assets/textures/discardpile.png");
        guiDiscard = new GUIDiscardPile(discardTex, cache);
        guiDiscard.setPosition(guiHand.getX() + guiHand.getWidth() + 20, guiHand.getY());
        guiDiscard.setSize(GUICard.WIDTH + 10, GUICard.HEIGHT + 20);
        
        Font font = (Font) assets.get("assets/fonts/sansserif.fnt");
        Texture rowTex = (Texture) assets.get("assets/textures/row.png");
        float half = (Engine.INSTANCE.batch.getViewport().getSrcHeight() +
                guiHand.getHeight() + guiHand.getY()) / 2;
        GameBoard board = game.getGameSystem().getBoard();
        for (int i = 0; i < friendlyRows.length; i++) {
            Text zero = new Text("0", font, 1 / 4f, -1);
            zero.setColor(1, 1, 1);
            CombatRow fRow = board.getRow(true, UnitType.values()[i]);
            friendlyRows[i] = new GUIRow(fRow, rowTex, zero, game.getTextCache());
            friendlyRows[i].setSize(
                    Engine.INSTANCE.batch.getViewport().getSrcWidth() - 2 * 400 - 20,
                    GUICard.HEIGHT + 10);
            friendlyRows[i].setPosition(400,
                    half - (i + 1) * (friendlyRows[i].getHeight() + 10) - 25);
            
            zero = new Text("0", font, 1 / 4f, -1);
            zero.setColor(1, 1, 1);
            CombatRow eRow = board.getRow(false, UnitType.values()[i]);
            enemyRows[i] = new GUIRow(eRow, rowTex, zero, game.getTextCache());
            enemyRows[i].setSize(
                    Engine.INSTANCE.batch.getViewport().getSrcWidth() - 2 * 400 - 20,
                    GUICard.HEIGHT + 10);
            enemyRows[i].setPosition(400,
                    half + i * (enemyRows[i].getHeight() + 10) + 25);
        }
        
        Texture crystalTex = (Texture) assets.get("assets/textures/crystal.png");
        guiHealth = new GUIHealth(game, crystalTex);
    }

    @Override
    public void create() {
        gui.addComponent(guiBoard);
        gui.addComponent(guiDiscard);
        gui.addComponent(guiHand);
        gui.addComponent(guiHealth);
        for (int i = 0; i < friendlyRows.length; i++) {
            gui.addComponent(friendlyRows[i]);
            gui.addComponent(enemyRows[i]);
        }
        gui.addComponent(guiOverlay);
    }
    
    @Override
    public void destroy() {
        gui.removeComponent(guiBoard);
        gui.removeComponent(guiDiscard);
        gui.removeComponent(guiHand);
        gui.removeComponent(guiHealth);
        for (int i = 0; i < friendlyRows.length; i++) {
            gui.removeComponent(friendlyRows[i]);
            gui.removeComponent(enemyRows[i]);
        }
        gui.removeComponent(guiOverlay);
    }
    
    @Override
    public void enter() {
        game.getInput().addListener(input);
        handInput.enable();
        rowInput.disable();
    }
    
    @Override
    public void exit() {
        game.getInput().removeListener(input);
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
        } else if (event instanceof CardDiscardedEvent) {
            processCardDiscarded((CardDiscardedEvent) event);
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
    
    private void processCardDiscarded(CardDiscardedEvent event) {
        Card card = event.getCard();
        GUICard guiCard = GUI.createGUICard(card, assets, cache);
        guiDiscard.addCard(card, guiCard);
    }
}
