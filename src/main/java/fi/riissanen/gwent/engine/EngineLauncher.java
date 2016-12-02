package fi.riissanen.gwent.engine;

import fi.riissanen.gwent.engine.interfaces.Game;

/**
 * The launcher containing the main()-method.
 * @author Daniel
 */
public class EngineLauncher implements Runnable {
    
    private Thread thread;
    private Game game;
    
    /**
     * Start an {@code Engine} instance with a game.
     * @param game The game to start with the engine
     */
    public void start(Game game) {
        this.game = game;
        thread = new Thread(this, "Gwent");
        thread.start();
    }
    
    @Override
    public void run() {
        Engine.INSTANCE.initialize();
        game.create();
        
        double lastTime = Engine.INSTANCE.getTime();
        int frames = 0;
        while (!Engine.INSTANCE.display.shouldClose()) {
            double currentTime = Engine.INSTANCE.getTime();
            frames++;
            double delta = currentTime - lastTime;
            if (currentTime - lastTime >= 1.0) {
                Engine.INSTANCE.frameTime = 1000.0 / frames;
                frames = 0;
                lastTime += 1.0;
            }
            
            game.render(delta);
            Engine.INSTANCE.display.updateDisplay();
        }
        
        game.dispose();
        Engine.INSTANCE.dispose();
    }
}
