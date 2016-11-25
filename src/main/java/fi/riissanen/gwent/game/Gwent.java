package fi.riissanen.gwent.game;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.Logger.LogLevel;
import fi.riissanen.gwent.engine.assets.AssetParams;
import fi.riissanen.gwent.engine.interfaces.Game;
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
import fi.riissanen.gwent.game.states.MatchStartEvent;
import fi.riissanen.gwent.game.ui.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Daniel
 */
public class Gwent implements Game {

    private CardFactory cardFactory;
    private CardLoader cardLoader;
    private EventSystem eventSys;
    private GameSystem gameSys;
    private Console console;
    
    @Override
    public void create() {
        cardFactory = new UnitCardFactory();
        cardLoader = new CardLoader();
        eventSys = new EventSystem();
        gameSys = new GameSystem(this);
        console = new Console();
        console.start(gameSys);
        
        setupGameSystem();
        setupEventListeners();
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
        // Create deck (temporary)
        Deck deck = new Deck();
        CardData data = cardLoader.load(new AssetParams("assets/cards/TestCard.card"));
        if (data == null) {
            Engine.INSTANCE.log.write(LogLevel.ERROR, cardLoader.getLog());
            Engine.INSTANCE.exit();
            return;
        }
        for (int i = 0; i < Deck.MIN_CARDS; i++) {
            Card card = cardFactory.createCard(data);
            deck.addCard(card);
        }

        Player player = new Player(deck, true);
        player.setFaction(new NorthernKingdoms(player));
        
        Player player2 = new Player(deck, false);
        player2.setFaction(new NorthernKingdoms(player2));
        gameSys.initialize(player, player2);
    }
    
    @Override
    public void render(double delta) {
        eventSys.update();
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
