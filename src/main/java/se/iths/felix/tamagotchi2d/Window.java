package se.iths.felix.tamagotchi2d;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private static Window window = null;
    private int width, height;
    private String title;
    private long glfwWindow;


    private Window() {
        this.width = 1080;
        this.height = 570;
        this.title = "Tamagotchi";
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        IO.println("Hello person " + Version.getVersion() + "!");

        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init() {
        // setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //init GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("unable to init GLFW.");
        }

        // Config GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        // OpenGL 3.3
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);


        //  Create window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make openGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable vsync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }

    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            // poll events
            glfwPollEvents();

            glClearColor(0.0f, 0.5f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);


            glfwSwapBuffers(glfwWindow);
        }
    }
}
