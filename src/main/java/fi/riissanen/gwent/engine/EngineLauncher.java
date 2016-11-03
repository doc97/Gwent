package fi.riissanen.gwent.engine;

/**
 * The launcher containing the main()-method
 * @author Daniel
 */
public class EngineLauncher implements Runnable {
    
    private Thread thread;
    
    public void start() {
        thread = new Thread(this, "Gwent");
        thread.start();
    }
    
    @Override
    public void run() {
        Engine.INSTANCE.initialize();
        while(!Engine.INSTANCE.display.windowShouldClose()) {
            Engine.INSTANCE.display.updateDisplay();
        }
    }
}
