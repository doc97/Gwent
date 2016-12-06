package fi.riissanen.gwent.game;

import de.matthiasmann.twl.utils.PNGDecoder.Format;
import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.Logger.LogLevel;
import fi.riissanen.gwent.engine.assets.AssetManager;
import fi.riissanen.gwent.engine.assets.AssetParams;
import fi.riissanen.gwent.engine.interfaces.Game;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.engine.render.Viewport;
import fi.riissanen.gwent.engine.render.fonts.Font;
import fi.riissanen.gwent.engine.render.fonts.Text;
import fi.riissanen.gwent.engine.render.shaders.FontShader;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.Deck;
import fi.riissanen.gwent.game.cards.factories.CardData;
import fi.riissanen.gwent.game.cards.factories.CardFactory;
import fi.riissanen.gwent.game.cards.factories.CardLoader;
import fi.riissanen.gwent.game.cards.factories.UnitCardFactory;
import fi.riissanen.gwent.game.events.EventListener;
import fi.riissanen.gwent.game.events.EventSystem;
import fi.riissanen.gwent.game.events.RoundEndEvent;
import fi.riissanen.gwent.game.factions.Faction;
import fi.riissanen.gwent.game.factions.Monsters;
import fi.riissanen.gwent.game.factions.NilfgaardianEmpire;
import fi.riissanen.gwent.game.factions.NorthernKingdoms;
import fi.riissanen.gwent.game.events.MatchStartEvent;
import fi.riissanen.gwent.game.ui.Console;
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
    private CardFactory cardFactory;
    private CardLoader cardLoader;
    private EventSystem eventSys;
    private GameSystem gameSys;
    private TextCache texts;
    private Font font;
    private Console console;
    
    @Override
    public void create() {
        assets = new AssetManager();
        cardFactory = new UnitCardFactory();
        cardLoader = new CardLoader();
        eventSys = new EventSystem();
        gameSys = new GameSystem(this);
        texts = new TextCache();
        console = new Console();
        console.start(gameSys);
        
        loadAssets();
        setupGameSystem();
        setupEventListeners();

        int width = Engine.INSTANCE.display.getWidth();
        int height = Engine.INSTANCE.display.getHeight();
        Engine.INSTANCE.display.setBackgroundColor(1, 0.5f, 0.5f, 1);
        Engine.INSTANCE.batch.setViewport(0, 0, width, height);
        
        font = (Font) assets.get("assets/fonts/mono.fnt");
        
        Text text = new Text("...hey you!<3", font, 1/2f, -1);
        text.setColor(1, 0.1f, 0.1f);
        text.setPosition(width / 3, height / 2);
        texts.addText(text);
        
        try {
            Engine.INSTANCE.fontRenderer.setShader(new FontShader(
                    "assets/shaders/font.vert",
                    "assets/shaders/font.frag"));
        } catch (IOException ex) {
            System.err.println("Cannot set font shader");
        }
    }
    
    private void loadAssets() {
        // Trying to load a font
        assets.load(new AssetParams("assets/lwjgl.png", Format.RGBA),
                AssetManager.TEXTURE_LOADER);
        assets.load("assets/fonts/arial.fnt", AssetManager.FONT_LOADER);
        assets.load("assets/fonts/mono.fnt", AssetManager.FONT_LOADER);
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
        
        eventSys.setListeners(RoundEndEvent.class, ree);
        eventSys.setListeners(MatchStartEvent.class, mse);
    }
    
    private void setupGameSystem() {
        Player player = new Player(true);
        player.setFaction(new NorthernKingdoms(player));
        
        Player player2 = new Player(false);
        player2.setFaction(new NorthernKingdoms(player2));
        
        // Create deck (temporary)
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        CardData data = cardLoader.load(new AssetParams("assets/cards/TestCard.card"));
        if (data == null) {
            Engine.INSTANCE.log.write(LogLevel.ERROR, cardLoader.getLog());
            Engine.INSTANCE.exit();
            return;
        }
        for (int i = 0; i < Deck.MIN_CARDS; i++) {
            Card card = cardFactory.createCard(data);
            deck1.addCard(card);
            deck2.addCard(card);
        }

        player.setDeck(deck1);
        player2.setDeck(deck2);
        gameSys.initialize(player, player2);
    }
    
    @Override
    public void render(double delta) {
        Engine.INSTANCE.display.clearDisplay();
        eventSys.update();
        Engine.INSTANCE.fontRenderer.render(Engine.INSTANCE.batch, texts.getCache());
    }

    @Override
    public void dispose() {
        console.stop();
    }
    
    public GameSystem getGameSystem() {
        return gameSys;
    }
    
    public EventSystem getEventSystem() {
        return eventSys;
    }
}
