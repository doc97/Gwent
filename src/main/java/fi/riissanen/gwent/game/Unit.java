package fi.riissanen.gwent.game;

import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class Unit {

    private final EnumSet<UnitType> types;
    private int strength;

    public Unit(List<UnitType> types, int strength) {
        this.types = EnumSet.copyOf(types);
        this.strength = strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public EnumSet<UnitType> getTypes() {
        return types;
    }
    
    public int getStrength() {
        return strength;
    }
}
