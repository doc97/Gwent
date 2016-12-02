package fi.riissanen.gwent.engine;

import fi.riissanen.gwent.engine.Logger.LogLevel;
import static java.sql.Types.NULL;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

/**
 * Manages the everything display related, such as GLFW window creation,
 * resizing and updating.
 * @author Daniel
 */
public class DisplayManager {
    
    private static final String TITLE = "Gwent";
    
    private long window;
    private int width, height;
    private boolean shouldResize = true;
    private boolean shouldClose = false;
    
    /**
     * Creates a GLFW window.
     * @param width The width of the window
     * @param height The height of the window
     * @return The success of the window creation
     */
    public boolean createDisplay(int width, int height) {
        if (window != 0) {
            Engine.INSTANCE.log.write(LogLevel.INFO,
                    "DisplayManager: Window has already been created");
            return false;
        }

        this.width = width;
        this.height = height;

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        window = glfwCreateWindow(width, height, TITLE, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }
        
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();

        glEnable(GL_ALPHA_TEST);

        return true;
    }
    
    private void resize() {
        if (shouldResize) {
            GL11.glViewport(0, 0, width, height);
            shouldResize = false;
        }
    }
    
    /**
     * Updates the GLFW window.
     * 
     * <p>
     * It resizes the window if necessary and swaps the frame buffers
     * and polls events
     */
    public void updateDisplay() {
        resize();
        glfwSwapBuffers(window);
        glfwPollEvents();
    }
    
    /**
     * Signals the engine that the window should close.
     */
    public void close() {
        shouldClose = true;
    }
    
    /**
     * Checks the close status.
     * @return Returns true if the window should close due to the user closing
     * it or the it was signaled to close from somewhere else
     * @see close()
     */
    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window) || shouldClose;
    }
    
    /**
     * Sets the clear color of the OpenGL context.
     * @param r Red color ranging from [0-1]
     * @param g Green color ranging from [0-1]
     * @param b Blue color ranging from [0-1]
     * @param a Alpha value ranging from [0-1], 0 being transparent and 1 opaque
     */
    public void setBackgroundColor(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }
    
    /**
     * Clears the color buffer of the currently bound frame buffer.
     * 
     * <p>
     * The clear color can be set with
     * {@code setBackgroundColor(float, float, float, float)}
     */
    public void clearDisplay() {
        glClear(GL_COLOR_BUFFER_BIT);
    }
    
    /**
     * Destroys the GLFW window and OpenGL context.
     */
    public void dispose() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}
