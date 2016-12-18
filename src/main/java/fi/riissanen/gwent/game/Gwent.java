package fi.riissanen.gwent.game;

import de.matthiasmann.twl.utils.PNGDecoder.Format;
import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.Logger.LogLevel;
import fi.riissanen.gwent.engine.assets.AssetManager;
import fi.riissanen.gwent.engine.assets.AssetParams;
import fi.riissanen.gwent.engine.input.InputMultiplexer;
import fi.riissanen.gwent.engine.interfaces.Game;
import fi.riissanen.gwent.engine.render.shaders.FontShader;
import fi.riissanen.gwent.game.cards.Deck;
import fi.riissanen.gwent.game.cards.factories.CardCreator;
import fi.riissanen.gwent.game.cards.factories.CardData;
import fi.riissanen.gwent.game.events.AfterEvent;
import fi.riissanen.gwent.game.events.EventListener;
import fi.riissanen.gwent.game.events.EventSystem;
import fi.riissanen.gwent.game.events.RoundEndEvent;
import fi.riissanen.gwent.game.factions.Faction;
import fi.riissanen.gwent.game.factions.Monsters;
import fi.riissanen.gwent.game.factions.NilfgaardianEmpire;
import fi.riissanen.gwent.game.factions.NorthernKingdoms;
import fi.riissanen.gwent.game.events.MatchStartEvent;
import fi.riissanen.gwent.game.ui.Console;
import fi.riissanen.gwent.game.ui.GUI;
import fi.riissanen.gwent.game.ui.TextCache;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the engine's {@link Game}.
 * @author Daniel
 */
public class Gwent implements Game {

    private AssetManager assets;
    private CardCreator cardCreator;
    private EventSystem eventSys;
    private GameSystem gameSys;
    private GUI gui;
    private InputMultiplexer input;
    private TextCache texts;
    private Console console;
    
    @Override
    public void create() {
        initialize();
        setup();
    }
    
    /**
     * Initializes all internal members.
     * 
     * <p>
     * Useful to use when running unit tests
     */
    public void initialize() {
        assets = new AssetManager();
        cardCreator = new CardCreator();
        eventSys = new EventSystem();
        gameSys = new GameSystem(this);
        gui = new GUI();
        input = new InputMultiplexer();
        texts = new TextCache();
        console = new Console();
    }
    
    /**
     * Setups the actual game.
     */
    public void setup() {
        console.start(gameSys);
        
        Engine.INSTANCE.display.setBackgroundColor(1, 0.5f, 0.5f, 1);
        Engine.INSTANCE.batch.setViewport(0, 0, 1920, 1080);
        
        loadAssets();
        setupGameSystem();
        setupEventListeners();

        try {
            Engine.INSTANCE.fontRenderer.setShader(new FontShader(
                    "assets/shaders/font.vert",
                    "assets/shaders/font.frag"));
        } catch (IOException ex) {
            System.err.println("Cannot set font shader");
        }
        
        gui.initialize();
        input.addListener(gui.getInput());
        Engine.INSTANCE.input.setInputListener(input);
    }
    
    private void loadAssets() {
        // Textures
        assets.load(new AssetParams("assets/textures/board.png", Format.RGBA),
                AssetManager.TEXTURE_LOADER);
        assets.load(new AssetParams("assets/textures/cardbase.png", Format.RGBA),
                AssetManager.TEXTURE_LOADER);
        assets.load(new AssetParams("assets/textures/cardfaction.png", Format.RGBA),
                AssetManager.TEXTURE_LOADER);
        assets.load(new AssetParams("assets/textures/row.png", Format.RGBA),
                AssetManager.TEXTURE_LOADER);
        assets.load(new AssetParams("assets/textures/crystal.png", Format.RGBA),
                AssetManager.TEXTURE_LOADER);
        assets.load(new AssetParams("assets/textures/discardpile.png", Format.RGBA),
                AssetManager.TEXTURE_LOADER);
        assets.load(new AssetParams("assets/textures/dark.png", Format.RGBA),
                AssetManager.TEXTURE_LOADER);

        // Fonts
        assets.load("assets/fonts/arial.fnt", AssetManager.FONT_LOADER);
        assets.load("assets/fonts/mono.fnt", AssetManager.FONT_LOADER);
        assets.load("assets/fonts/sansserif.fnt", AssetManager.FONT_LOADER);
        
        // Cards
        assets.load("assets/cards/TestCard.card", AssetManager.CARD_LOADER);
        assets.load("assets/cards/Biting Frost.card", AssetManager.CARD_LOADER);
        assets.processQueue();
    }
    
    private void setupEventListeners() {
        List<EventListener> ree = new ArrayList<>();
        List<EventListener> mse = new ArrayList<>();
        Faction fFaction = gameSys.getFriendlyPlayer().getFaction();
        Faction eFaction = gameSys.getEnemyPlayer().getFaction();
        if (fFaction instanceof NilfgaardianEmpire ||
                fFaction instanceof NorthernKingdoms ||
                fFaction instanceof Monsters) {
            ree.add(fFaction);
        } else if (fFaction instanceof Monsters) {
            mse.add(fFaction);
        }
        
        if (eFaction instanceof NilfgaardianEmpire ||
                eFaction instanceof NorthernKingdoms ||
                eFaction instanceof Monsters) {
            ree.add(eFaction);
        } else if (eFaction instanceof Monsters) {
            mse.add(eFaction);
        }
        
        eventSys.addListeners(RoundEndEvent.class, ree);
        eventSys.addListeners(MatchStartEvent.class, mse);
        eventSys.addListener(AfterEvent.class, gameSys.getMatchManager());
    }
    
    private void setupGameSystem() {
        Player player = new Player(this, true);
        player.setFaction(new NorthernKingdoms(player));
        
        Player player2 = new Player(this, false);
        player2.setFaction(new Monsters(player2));
        
        // Create deck (temporary)
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        CardData data = (CardData) assets.get("assets/cards/TestCard.card");
        CardData frost = (CardData) assets.get("assets/cards/Biting Frost.card");
        if (data == null || frost == null) {
            Engine.INSTANCE.log.write(LogLevel.ERROR, "Failed to load cards, terminating...");
            Engine.INSTANCE.exit();
            return;
        }
        
        for (int i = 0; i < Deck.MIN_CARDS; i++) {
            deck1.addCard(cardCreator.createCard(data));
            deck2.addCard(cardCreator.createCard(data));
        }
        
        for (int i = 0; i < 10; i++) {
            deck1.addCard(cardCreator.createCard(frost));
            deck2.addCard(cardCreator.createCard(frost));
        }

        player.setDeck(deck1);
        player2.setDeck(deck2);
        gameSys.initialize(player, player2);
    }
    
    @Override
    public void render(double delta) {
        eventSys.update();
        gameSys.update();
        gui.update();
        
        Engine.INSTANCE.display.clearDisplay();
        gui.render(Engine.INSTANCE.batch);
        Engine.INSTANCE.fontRenderer.render(Engine.INSTANCE.batch, texts.getCache());
    }

    @Override
    public void dispose() {
        console.stop();
    }

    public AssetManager getAssetManager() {
        return assets;
    }

    public GUI getGUI() {
        return gui;
    }
    
    public InputMultiplexer getInput() {
        return input;
    }
    
    public GameSystem getGameSystem() {
        return gameSys;
    }
    
    public EventSystem getEventSystem() {
        return eventSys;
    }
    
    public TextCache getTextCache() {
        return texts;
    }
}
