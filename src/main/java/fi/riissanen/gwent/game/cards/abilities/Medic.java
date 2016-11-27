package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.states.GameStates;

/**
 * Medic ability
 * @author Daniel
 */
public class Medic implements Ability, UnitAbility {

    private final UnitCard card;
    
    public Medic(UnitCard card) {
        this.card = card;
    }
    
    @Override
    public void activate(GameSystem system) {
        system.getStateSystem().push(GameStates.DISCARD_PILE_STATE);
    }
    
    
    @Override
    public Unit getUnit() {
        return card.getUnit();
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
