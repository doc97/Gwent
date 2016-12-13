package fi.riissanen.gwent.engine.input;

/**
 * Interface with input related methods.
 * @author Daniel
 */
public interface InputListener {
    
    /**
     * General update.
     */
    public void update();
    
    /**
     * Called when a key is pressed.
     * @param key The key code
     * @return True if the input was captured
     */
    public boolean keyPressed(int key);
    
    /**
     * Called when a key is repeated.
     * @param key The key code
     * @return True if the input was captured
     */
    public boolean keyRepeat(int key);
    
    /**
     * Called when a key is released.
     * @param key The key code
     * @return True if the input was captured
     */
    public boolean keyReleased(int key);
    
    /**
     * Called when the a mouse button is pressed.
     * @param key The button code
     * @return True if the input was captured
     */
    public boolean mousePressed(int key);
    
    /**
     * Called when a mouse button is released.
     * @param key The button code
     * @return True if the input was captured
     */
    public boolean mouseReleased(int key);
    
    /**
     * Called when the mouse moves.
     *
     * <p>
     * Origin (0, 0) is in the top left corner
     * @param x The x position on the client area
     * @param y The y position on the client area
     * @return True if the input was captured
     */
    public boolean mouseMoved(double x, double y);
}
