package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.engine.render.Color;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.MatchManager;

/**
 * Graphical representation of health points.
 * @author Daniel
 */
public class GUIHealth extends GUIComponent {

    private final GUICrystal[] friendlyCrystals;
    private final GUICrystal[] enemyCrystals;
    private final Color red = new Color(1, 0.3f, 0.3f, 1);
    private final Color white = new Color(1, 1, 1, 1);
    private final Gwent game;
    
    /**
     * Creates a {@link GUIComponent}.
     * @param game The game instance
     * @param crystalTex The texture
     */
    public GUIHealth(Gwent game, Texture crystalTex) {
        this.game = game;
        friendlyCrystals = new GUICrystal[MatchManager.MAX_LIVES];
        enemyCrystals = new GUICrystal[MatchManager.MAX_LIVES];
        
        int size = 32;
        for (int i = 0; i < friendlyCrystals.length; i++) {
            friendlyCrystals[i] = new GUICrystal(crystalTex);
            friendlyCrystals[i].setSize(size, size);
            friendlyCrystals[i].setPosition(300 - (friendlyCrystals.length - i) * size,
                    300);
            enemyCrystals[i] = new GUICrystal(crystalTex);
            enemyCrystals[i].setSize(size, size);
            enemyCrystals[i].setPosition(300 - (friendlyCrystals.length - i) * size,
                    Engine.INSTANCE.batch.getViewport().getSrcHeight() - 300);
        }
    }
    
    @Override
    public void update() {
        for (int i = 0; i < friendlyCrystals.length; i++) {
            if (i + 1 <= game.getGameSystem().getMatchManager().getFriendlyLives()) {
                friendlyCrystals[i].setColor(red);
            } else {
                friendlyCrystals[i].setColor(white);
            }
                
            if (i + 1 <= game.getGameSystem().getMatchManager().getEnemyLives()) {
                enemyCrystals[i].setColor(red);
            } else {
                enemyCrystals[i].setColor(white);
            }
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        for (int i = 0; i < friendlyCrystals.length; i++) {
            friendlyCrystals[i].render(batch);
            enemyCrystals[i].render(batch);
        }
    }
}
