package fi.riissanen.gwent.engine.input;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class InputManager {

    private static GLFWErrorCallback errCallback;
    private static GLFWKeyCallback keyCallback;
    private static GLFWMouseButtonCallback mouseCallback;
    private static GLFWCursorPosCallback cursorCallback;

    private final InputListener emptyListener = new InputAdapter();
    private InputListener listener = emptyListener;

    public void init(long window) {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        glfwSetErrorCallback(errCallback = GLFWErrorCallback.createPrint(System.err));
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW_REPEAT) {
                    listener.keyRepeat(key);
                } else if (action == GLFW_RELEASE) {
                    listener.keyReleased(key);
                } else if (action == GLFW_PRESS) {
                    listener.keyPressed(key);
                }
            }
        });
        glfwSetMouseButtonCallback(window, mouseCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW_PRESS) {
                    listener.mousePressed(button);
                } else if (action == GLFW_RELEASE) {
                    listener.mouseReleased(button);
                }
            }
        });
        glfwSetCursorPosCallback(window, cursorCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double x, double y) {
                listener.mouseMoved(x, y);
            }
        });
    }

    /**
     * Updates input listener.
     */
    public void update() {
        listener.update();
    }
    
    /**
     * Sets the input listener, can be null to remove listener.
     * @param listener The listener
     */
    public void setInputListener(InputListener listener) {
        if (listener == null) {
            this.listener = emptyListener;
        } else {
            this.listener = listener;
        }
    }

    public void dispose() {
        keyCallback.free();
        errCallback.free();
        mouseCallback.free();
        cursorCallback.free();
    }
}
