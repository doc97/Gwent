package fi.riissanen.gwent.game.combat;

/**
 *
 * @author Daniel
 */
public enum UnitType {
    MELEE(0), RANGED(1), SIEGE(2);

    private final int index;

    UnitType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
