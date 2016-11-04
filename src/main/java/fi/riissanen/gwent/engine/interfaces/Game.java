package fi.riissanen.gwent.engine.interfaces;

/**
 *
 * @author Daniel
 */
public abstract class Game {

    public abstract void create();
    public abstract void render(double delta);
    public abstract void dispose();
}
