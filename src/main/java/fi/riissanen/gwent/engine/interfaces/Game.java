package fi.riissanen.gwent.engine.interfaces;

/**
 * Methods need for a game.
 * @author Daniel
 */
public interface Game {

    /**
     * Called when the game is created or initialized.
     */
    public abstract void create();
    
    /**
     * Called in the render loop, once every frame.
     * @param delta The delta since last frame, in seconds
     */
    public abstract void render(double delta);
    
    /**
     * Called when the game is unloaded.
     */
    public abstract void dispose();
}
