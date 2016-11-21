package fi.riissanen.gwent.game.combat;

import java.util.EnumSet;
import java.util.Iterator;
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
    
    public int[] getTypeIndices() {
        int[] indices = new int[types.size()];
        int i = 0;
        for (Iterator<UnitType> it = types.iterator(); it.hasNext(); i++) {
            indices[i] = it.next().getIndex();
        }
        return indices;
    }
    
    public int getStrength() {
        return strength;
    }
}
