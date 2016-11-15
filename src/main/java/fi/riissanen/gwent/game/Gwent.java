package fi.riissanen.gwent.game;

import fi.riissanen.gwent.engine.interfaces.Game;
import fi.riissanen.gwent.game.ui.Console;

/**
 * 
 * @author Daniel
 */
public class Gwent implements Game {

    private GameSystem gameSys;
    private Console console;
    
    @Override
    public void create() {
        gameSys = new GameSystem();
        gameSys.initialize();
        console = new Console();
        console.start(gameSys);
    }

    @Override
    public void render(double delta) {

    }

    @Override
    public void dispose() {
        console.stop();
    }
}
