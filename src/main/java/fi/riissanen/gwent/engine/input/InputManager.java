package fi.riissanen.gwent.engine.input;

import fi.riissanen.gwent.engine.Engine;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class InputManager {

    private static GLFWErrorCallback errCallback;
    private static GLFWKeyCallback keyCallback;
    private static GLFWMouseButtonCallback mouseCallback;

    private final DoubleBuffer xpos = BufferUtils.createDoubleBuffer(8);
    private final DoubleBuffer ypos = BufferUtils.createDoubleBuffer(8);
    private final InputListener emptyListener = new InputAdapter();
    private InputListener listener = emptyListener;
    private float mouseX, mouseY;
    private long window;

    public void init(long window) {
        this.window = window;
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
    }

    /**
     * Updates mouse position.
     */
    public void update() {
        glfwGetCursorPos(window, xpos, ypos);
        mouseX = (float) xpos.get();
        mouseY = (float) ypos.get();
        xpos.clear();
        ypos.clear();
        
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

    public float getRawMouseX() {
        return mouseX;
    }

    public float getRawMouseY() {
        return mouseY;
    }

    public float getTranslatedMouseX() {
        return (mouseX / Engine.INSTANCE.display.getWidth()) *
                Engine.INSTANCE.batch.getViewport().getWidth();
    }

    public float getTranslatedMouseY() {
        return ((Engine.INSTANCE.display.getHeight() - mouseY) /
                Engine.INSTANCE.display.getHeight()) *
                Engine.INSTANCE.batch.getViewport().getHeight();
    }

    public void cleanup() {
        keyCallback.free();
        errCallback.free();
        mouseCallback.free();
    }
}
