package fi.riissanen.gwent.game;

import fi.riissanen.gwent.engine.EngineLauncher;
import fi.riissanen.gwent.engine.interfaces.Game;

/**
 * The launcher containing the main()-method
 * @author Daniel
 */
public class GameLauncher {
    public static void main(String[] args) {
        Game game = new Game(){
            @Override
            public void create() {}
            @Override
            public void render(double delta) {}
            @Override
            public void dispose() {}
        };
        new EngineLauncher().start(game);
    }
}
