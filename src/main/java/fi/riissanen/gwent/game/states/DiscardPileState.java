package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.cards.abilities.Medic;

/**
 * State in which player chooses a card from the discard pile
 * 
 * <p>
 * An example of this is when a player has played a card with the {@link Medic}
 * ability.
 * @author Daniel
 */
public class DiscardPileState extends GameStateAdapter {

    public DiscardPileState(Gwent game) {
        super(game);
    }
}
