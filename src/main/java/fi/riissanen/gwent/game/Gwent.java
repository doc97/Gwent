package fi.riissanen.gwent.game;

import fi.riissanen.gwent.engine.interfaces.Game;

/**
 * 
 * @author Daniel
 */
public class Gwent extends Game {

    private GameSystem gameSys;
    
    @Override
    public void create() {
        gameSys = new GameSystem();
        gameSys.initialize();
    }

    @Override
    public void render(double delta) {

    }

    @Override
    public void dispose() {

    }
}
