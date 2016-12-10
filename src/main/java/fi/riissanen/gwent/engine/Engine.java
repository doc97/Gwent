package fi.riissanen.gwent.engine;

import fi.riissanen.gwent.engine.Logger.LogLevel;
import fi.riissanen.gwent.engine.files.FileUtils;
import fi.riissanen.gwent.engine.render.FontRenderer;
import fi.riissanen.gwent.engine.render.SpriteBatch;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

/**
 * Core engine using LWJGL 3.
 * @author Daniel
 */
public enum Engine {
    INSTANCE;

    public final DisplayManager display = new DisplayManager();
    public SpriteBatch batch;
    public FontRenderer fontRenderer;
    public Logger log;
    public FileUtils files;
    public double frameTime;
    private boolean initialized;

    /**
     * Initializes the game engine.
     */
    public void initialize() {
        if (!display.createDisplay(1280, 720)) {
            Engine.INSTANCE.log.write(LogLevel.ERROR,
                    "GameLauncher: Failed to initialize engine");
            return;
        }
        display.setBackgroundColor(0.5f, 0, 0.25f, 1);

        batch = new SpriteBatch();
        fontRenderer = new FontRenderer();
        log = new Logger();
        files = new FileUtils();
        
        // We are not using depth for 2D
        glDisable(GL_DEPTH_TEST);

        // Enable blending and therefore transparency
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        initialized = true;
    }

    /**
     * Tells the engine to shut down.
     */
    public void exit() {
        display.close();
    }
    
    /**
     * Calls glfwGetTime().
     * @return Time since GLFW was initialized, in seconds
     */
    public double getTime() {
        return glfwGetTime();
    }
    
    /**
     * Returns the initialized status.
     * @return True if engine has been initialized
     */
    public boolean isInitialized() {
        return initialized;
    }
    
    /**
     * Disposes of engine components.
     */
    public void dispose() {
        display.dispose();
        batch.dispose();
    }
}
