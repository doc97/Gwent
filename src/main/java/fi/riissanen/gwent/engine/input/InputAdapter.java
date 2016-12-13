package fi.riissanen.gwent.engine.input;

/**
 * Implements an {@link InputListener}.
 * @author Daniel
 */
public class InputAdapter implements InputListener {

    @Override
    public void update() {
    }
    
    @Override
    public boolean keyPressed(int key) {
        return false;
    }

    @Override
    public boolean keyRepeat(int key) {
        return false;
    }

    @Override
    public boolean keyReleased(int key) {
        return false;
    }

    @Override
    public boolean mousePressed(int key) {
        return false;
    }

    @Override
    public boolean mouseReleased(int key) {
        return false;
    }
    
    @Override
    public boolean mouseMoved(double x, double y) {
        return false;
    }
}
