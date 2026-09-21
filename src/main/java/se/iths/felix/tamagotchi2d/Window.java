package se.iths.felix.tamagotchi2d;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL;

import java.sql.Time;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private static Window window = null;
    private final float a;
    private float r;
    private float g;
    private float b;
    private int width, height;
    private String title;
    private long glfwWindow;
    private long vg;
    private double lastTime = System.currentTimeMillis();
    private int fps = 0;
    private int frames = 0;
    private String actionText;

    TamagotchiMethods tamagotchi = new TamagotchiMethods();


    private Window() {
        this.width = 1920;
        this.height = 1200;
        this.title = "Best Tamogotchi 2D";
        r = 0.53f;
        g = 0.0f;
        b = 0.65f;
        a = 1;
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
        GLFWErrorCallback.createPrint(System.err).set();

        // Let GLFW automatically select the correct platform
        // (Windows on Windows, X11/Wayland on Linux).
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW!");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        glfwWindowHint(GLFW_STENCIL_BITS, 8);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL);

        if (glfwWindow == NULL)
            throw new IllegalStateException("Failed to create the GLFW window.");

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(glfwWindow);



        GL.createCapabilities();

        vg = NanoVGGL3.nvgCreate(
                NanoVGGL3.NVG_ANTIALIAS |
                        NanoVGGL3.NVG_STENCIL_STROKES
        );

        if (vg == NULL)
            throw new RuntimeException("Failed to create NanoVG context");

        // Load font
        try (var inputStream = getClass().getResourceAsStream("/fonts/Comic Sans MS.ttf")) {

            if (inputStream == null) {
                throw new RuntimeException("Font not found: /fonts/Comic Sans MS.ttf");
            }

            var tempFont = java.nio.file.Files.createTempFile("font-", ".ttf");

            java.nio.file.Files.copy(
                    inputStream,
                    tempFont,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            int font = NanoVG.nvgCreateFont(
                    vg,
                    "mono",
                    tempFont.toAbsolutePath().toString()
            );

            if (font == -1) {
                throw new RuntimeException("Failed to load font");
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        nvgEndFrame(vg);

        glfwSwapBuffers(glfwWindow);
    }



    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            // poll events
            glfwPollEvents();
            String text = "";

            //rgbScreen();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            double currentTime = System.currentTimeMillis();
            frames++;

            if (currentTime - lastTime >= 1000) {
                fps = frames;
                frames = 0;
                lastTime = currentTime;

            }

            nvgBeginFrame(vg, width, height, 1);

            nvgFontSize(vg, 48f);
            nvgFontFace(vg, "mono");

            NVGColor color = NVGColor.create();
            color.r(1f).g(1f).b(1f).a(1f);

            nvgFillColor(vg, color);

            nvgText(vg, 0, 100, "FPS: " + fps);

            int key = KeyListener.getKeyPressed();

            if(key != -1){
                text = String.valueOf((char) key);
                IO.println(text);
            }
            tamagotchi.checkIfAlive();
            if(tamagotchi.getAlive()){
                switch (text){
                    case "1":
                        tamagotchi.feed();
                        tamagotchi.latestAction("you feed "+tamagotchi.name);
                        break;
                    case"2":
                        tamagotchi.play();
                        tamagotchi.latestAction("you play with " + tamagotchi.name);
                        break;
                    case"3":
                        tamagotchi.work();
                        tamagotchi.latestAction("you go to work");
                        break;
                    case"4":
                        tamagotchi.gamble();
                        tamagotchi.latestAction("You gamble");
                        break;
                    case"5":
                        tamagotchi.setAliveFalse();
                    default:
                }

                nvgText(vg, 200, 120, "Latest Action: " + tamagotchi.latestAction);
                nvgText(vg,200, 75, "What do you want to do? 1. Feed | 2. Play | 3. Work | 4. Gamble | 5. Quit.");
                nvgText(vg, 150, 1100, tamagotchi.toString());
            }else nvgText(vg,500,750,tamagotchi.name + " is dead ):");
            drawTamagotchi(500,500);
            if(KeyListener.isKeyPressed(GLFW_KEY_R))
                tamagotchi = new TamagotchiMethods();

            nvgEndFrame(vg);

            glfwSwapBuffers(glfwWindow);
            KeyListener.endFrame();
        }
    }
    private void drawTamagotchi(float x, float y) {
        nvgBeginPath(vg);

        nvgRect(vg, x, y, 200, 200);

        if(tamagotchi.getAlive()){
            nvgTextBox(vg, 500, 500, width -2 * 20, """
                          ,-~~-.___.
                         / |  '     \\
                        (  )         0             \s
                         \\_/-, ,----'           \s
                            ====           //                    \s
                           /  \\-'~;    /~~~(O)
                          /  __/~|   /       |    \s
                        =(  _____| (_________|   W<
                        """);
        }
    }
}
