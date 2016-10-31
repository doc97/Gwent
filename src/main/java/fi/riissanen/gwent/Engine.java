package fi.riissanen.gwent;

import fi.riissanen.gwent.Logger.LogLevel;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

/**
 * Core engine using LWJGL 3
 * @author Daniel
 */
public enum Engine {
    INSTANCE;

    public final DisplayManager display = new DisplayManager();
    public final Logger logger = new Logger();

    public void initialize() {
        if(!display.createDisplay(1280, 720)) {
            Engine.INSTANCE.logger.write(LogLevel.ERROR,
                    "GameLauncher: Failed to initialize render engine");
            return;
        }

        display.setBackgroundColor(0, 0, 0, 1);

        // We are not using depth for 2D
        glDisable(GL_DEPTH_TEST);

        // Enable blending and therefore transparency
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void dispose() {
        display.dispose();
    }
}
