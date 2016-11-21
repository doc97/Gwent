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
    private int baseStrength;
    private int effectStrength;

    public Unit(List<UnitType> types, int strength) {
        this.types = EnumSet.copyOf(types);
        this.baseStrength = strength;
    }

    public void setBaseStrength(int strength) {
        this.baseStrength = strength;
    }
    
    public void setEffectStrength(int effectStrength) {
        this.effectStrength = effectStrength;
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
    
    public int getBaseStrength() {
        return baseStrength;
    }
    
    public int getEffectStrength() {
        return effectStrength;
    }
    
    public int getStrength() {
        return baseStrength + effectStrength;
    }
}
