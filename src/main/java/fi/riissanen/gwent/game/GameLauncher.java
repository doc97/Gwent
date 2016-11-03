package fi.riissanen.gwent.game;

import fi.riissanen.gwent.engine.EngineLauncher;

/**
 * The launcher containing the main()-method
 * @author Daniel
 */
public class GameLauncher {
    public static void main(String[] args) {
        EngineLauncher launcher = new EngineLauncher();
        launcher.start();
    }
}
