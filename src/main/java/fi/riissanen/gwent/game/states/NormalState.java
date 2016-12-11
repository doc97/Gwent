package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.assets.AssetManager;
import fi.riissanen.gwent.engine.render.Color;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.engine.render.fonts.Font;
import fi.riissanen.gwent.engine.render.fonts.Text;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.events.CardStageEvent;
import fi.riissanen.gwent.game.events.DrawCardEvent;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.EventListener;
import fi.riissanen.gwent.game.factions.Faction;
import fi.riissanen.gwent.game.factions.Monsters;
import fi.riissanen.gwent.game.factions.NilfgaardianEmpire;
import fi.riissanen.gwent.game.factions.NorthernKingdoms;
import fi.riissanen.gwent.game.factions.Scoiatael;
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
        
        Texture handTex = (Texture) assets.get("assets/textures/row.png");
        hand = new GUIHand(handTex, game.getTextCache());
        hand.setPosition(300, 20);
        hand.setSize(Engine.INSTANCE.batch.getViewport().getWidth() - 2 * 300 - 20,
                GUICard.HEIGHT + 10);
        
        Font font = (Font) assets.get("assets/fonts/sansserif.fnt");
        Texture rowTex = (Texture) assets.get("assets/textures/row.png");
        float half = (Engine.INSTANCE.batch.getViewport().getHeight() + hand.getHeight() + 20) / 2;
        for (int i = 0; i < 3; i++) {
            Text zero = new Text("0", font, 1 / 4f, -1);
            zero.setColor(1, 1, 1);
            friendlyRows[i] = new GUIRow(rowTex, zero, game.getTextCache());
            friendlyRows[i].setSize(
                    Engine.INSTANCE.batch.getViewport().getWidth() - 2 * 300 - 20,
                    GUICard.HEIGHT + 10);
            friendlyRows[i].setPosition(300,
                    half - (i + 1) * (friendlyRows[i].getHeight() + 10));
            
            zero = new Text("0", font, 1 / 4f, -1);
            zero.setColor(1, 1, 1);
            enemyRows[i] = new GUIRow(rowTex, zero, game.getTextCache());
            enemyRows[i].setSize(
                    Engine.INSTANCE.batch.getViewport().getWidth() - 2 * 300 - 20,
                    GUICard.HEIGHT + 10);
            enemyRows[i].setPosition(300,
                    half + i * (enemyRows[i].getHeight() + 10));
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
            boolean friendly = ((DrawCardEvent) event).isFriendly();
            if (friendly) {
                hand.addCard(card, createGUICard(card));
            }
        } else if (event instanceof CardStageEvent) {
            Card card = ((CardStageEvent) event).getCard();
            boolean staged = ((CardStageEvent) event).isStaged();
            if (staged) {
                hand.removeCard(card);
            } else {
                hand.addCard(card, createGUICard(card));
            }
        } else if (event instanceof CardPlayedEvent) {
            Card card = ((CardPlayedEvent) event).getCard();
            if (card instanceof UnitCard) {
                int row = ((CardPlayedEvent) event).getRowIndex();
                boolean friendly = card.getOwner().isFriendly();
                if (friendly) {
                    friendlyRows[row].addCard((UnitCard) card, createGUICard(card));
                } else {
                    enemyRows[row].addCard((UnitCard) card, createGUICard((UnitCard) card));
                }
            }
        }
    }
    
    private GUICard createGUICard(Card card) {
        if (card instanceof UnitCard) {
            return createGUIUnitCard((UnitCard) card);
        }
        return null;
    }
    
    private GUICard createGUIUnitCard(UnitCard card) {
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
        
        GUICard guiCard = new GUICard(text, cardBase, cardFaction, color);
        guiCard.setSize(GUICard.WIDTH, GUICard.HEIGHT);
        return guiCard;
    }
}
