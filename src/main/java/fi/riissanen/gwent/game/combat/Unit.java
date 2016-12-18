package fi.riissanen.gwent.game.combat;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.cards.attributes.Attribute;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

/**
 * A class representing a unit.
 *
 * @author Daniel
 */
public class Unit {

    private Player owner;
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

    /**
     * Creates a unit with a name and description.
     * @param name The name of the unit
     * @param description The description of the unit
     */
    public Unit(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Activates all the unit's attributes.
     * 
     * <p>
     * Should be called right after unit creation
     */
    public void reloadAttributes() {
        for (Attribute a : attributes) {
            a.activate(this);
        }
    }

    public void setCard(Card card) {
        this.card = card;
    }
    
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Sets the immune status and zeroes the effect strength if true.
     * @param immune The status
     */
    public void setImmuneStatus(boolean immune) {
        this.immune = immune;
        if (immune) {
            effectStrength = 0;
        }
    }

    public void setFriendlyStatus(boolean friendly) {
        this.friendly = friendly;
    }

    /**
     * Sets the unit type.
     * @param type The type
     */
    public void setUnitType(UnitType type) {
        types = EnumSet.of(type);
    }

    /**
     * Sets a list of types for the unit.
     * 
     * <p>
     * Only agile units can have more than one unit type
     * @param types The list of unit types
     */
    public void setUnitTypes(List<UnitType> types) {
        this.types = EnumSet.copyOf(types);
    }

    /**
     * Add an attribute.
     * @param attribute The attribute to add
     */
    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }
    
    /**
     * Add a list of attributes.
     * @param attributes The list to add
     */
    public void addAttributes(List<Attribute> attributes) {
        this.attributes.addAll(attributes);
    }

    /**
     * Add an ability.
     * @param ability The ability to add
     */
    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    /**
     * Add a list of abilities.
     * @param abilities The list to add
     */
    public void addAbilities(List<Ability> abilities) {
        this.abilities.addAll(abilities);
    }

    public void setBaseStrength(int strength) {
        this.baseStrength = strength;
    }

    /**
     * Tries to set the effect strength.
     * 
     * <p>
     * But if the unit is immune it will not work
     * @param effectStrength The effect strength
     */
    public void setEffectStrength(int effectStrength) {
        if (!immune) {
            this.effectStrength = effectStrength;
        }
    }

    public EnumSet<UnitType> getTypes() {
        return types;
    }

    /**
     * Checks whether the unit has a specific attribute.
     * @param clazz The class of an attribute
     * @return True if the unit has such an attribute
     */
    public boolean hasAttribute(Class<? extends Attribute> clazz) {
        for (Attribute attribute : attributes) {
            if (attribute.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    /**
    * Checks whether the unit has a specific ability.
    * @param clazz The class of an ability
    * @return True if the unit has such an ability
    */
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

    /**
     * Returns an array of the indices of the unit's unit types.
     * @return The index array
     */
    public int[] getTypeIndices() {
        if (types == null) {
            return new int[0];
        }
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

    public Player getOwner() {
        return owner;
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
