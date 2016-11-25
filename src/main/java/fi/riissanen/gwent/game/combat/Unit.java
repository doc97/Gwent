package fi.riissanen.gwent.game.combat;

import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.cards.attributes.Attribute;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

/**
 * A class representing a unit
 *
 * @author Daniel
 */
public class Unit {

    private Card card;
    private final List<Ability> abilities = new ArrayList<>();
    private final List<Attribute> attributes = new ArrayList<>();
    private EnumSet<UnitType> types;
    private int baseStrength;
    private int effectStrength;
    private boolean immune;
    private boolean friendly;
    private final String name;
    private final String description;

    public Unit(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void reloadAttributes() {
        for (Attribute a : attributes) {
            a.activate(this);
        }
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setImmuneStatus(boolean immune) {
        this.immune = immune;
        if (immune) {
            effectStrength = 0;
        }
    }

    public void setFriendlyStatus(boolean friendly) {
        this.friendly = friendly;
    }

    public void setUnitType(UnitType type) {
        types = EnumSet.of(type);
    }

    public void setUnitTypes(List<UnitType> types) {
        this.types = EnumSet.copyOf(types);
    }

    public void addAttributes(List<Attribute> attributes) {
        this.attributes.addAll(attributes);
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public void addAbilities(List<Ability> abilities) {
        this.abilities.addAll(abilities);
    }

    public void setBaseStrength(int strength) {
        this.baseStrength = strength;
    }

    public void setEffectStrength(int effectStrength) {
        if (!immune) {
            this.effectStrength = effectStrength;
        }
    }

    public EnumSet<UnitType> getTypes() {
        return types;
    }

    public boolean hasAttribute(Class<? extends Attribute> clazz) {
        for (Attribute attribute : attributes) {
            if (attribute.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAbility(Class<? extends Ability> clazz) {
        for (Ability ability : abilities) {
            if (ability.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public int[] getTypeIndices() {
        int[] indices = new int[types.size()];
        int i = 0;
        for (Iterator<UnitType> it = types.iterator(); it.hasNext(); i++) {
            indices[i] = it.next().getIndex();
        }
        return indices;
    }

    public int getBaseStrength() {
        return baseStrength;
    }

    public int getEffectStrength() {
        return effectStrength;
    }

    public int getStrength() {
        return baseStrength + effectStrength;
    }

    public boolean isImmune() {
        return immune;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public Card getCard() {
        return card;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " <Strength: " + getStrength() + ", Hero: " + immune
                + ", Spy: " + !friendly + ">";
    }
}
