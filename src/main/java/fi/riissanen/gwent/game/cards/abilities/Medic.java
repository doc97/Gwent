package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;

/**
 *
 * @author Daniel
 */
public class Medic implements Ability, UnitAbility {

    private final Card card;
    
    public Medic(Card card) {
        this.card = card;
    }
    
    @Override
    public void activate(GameSystem system) {
        // TODO Implement play of discarded unit card
    }
    
    
    @Override
    public Unit getUnit() {
        if (card instanceof UnitCard) {
            return ((UnitCard) card).getUnit();
        }
        return null;
    }

    @Override
    public String getName() {
        return "Medic";
    }

    @Override
    public String getDescription() {
        return "Choose a card from your discard pile and play it instantly. It"
                + "cannot be a hero card or a special card";
    }
}
