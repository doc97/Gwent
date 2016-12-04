package fi.riissanen.gwent.engine;

import fi.riissanen.gwent.engine.interfaces.Game;

/**
 * The launcher containing the main()-method.
 * @author Daniel
 */
public class EngineLauncher {
    
    private Game game;
    private final Game emptyGame = new Game() {
        @Override
        public void create() {
        }

        @Override
        public void render(double delta) {
        }

        @Override
        public void dispose() {
        }
    };
    
    /**
     * Start an {@code Engine} instance with a game.
     * @param game The game to start with the engine
     */
    public void start(Game game) {
        this.game = (game == null ? emptyGame : game);
        
        Engine.INSTANCE.initialize();
        this.game.create();
        
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
            
            this.game.render(delta);
            Engine.INSTANCE.display.updateDisplay();
        }
        
        this.game.dispose();
        Engine.INSTANCE.dispose();
    }
}
