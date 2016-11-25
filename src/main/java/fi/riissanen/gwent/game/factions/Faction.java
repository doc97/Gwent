package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.events.EventListener;

/**
 * Represents an abstract faction
 * @author Daniel
 */
public abstract class Faction implements EventListener {
    public abstract Ability getAbility();
    public abstract boolean isTriggered();
}
