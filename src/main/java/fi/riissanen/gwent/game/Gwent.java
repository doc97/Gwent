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
import fi.riissanen.gwent.game.ui.Console;

/**
 * 
 * @author Daniel
 */
public class Gwent implements Game {

    private CardFactory cardFactory;
    private CardLoader cardLoader;
    private GameSystem gameSys;
    private Console console;
    
    @Override
    public void create() {
        cardFactory = new UnitCardFactory();
        cardLoader = new CardLoader();
        gameSys = new GameSystem();
        console = new Console();

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

        gameSys.initialize(new Player(deck));
        console.start(gameSys);
    }

    @Override
    public void render(double delta) {

    }

    @Override
    public void dispose() {
        console.stop();
    }
}
