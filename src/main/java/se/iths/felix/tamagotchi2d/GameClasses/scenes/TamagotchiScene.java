package se.iths.felix.tamagotchi2d.GameClasses.scenes;

import org.lwjgl.nanovg.NVGColor;
import se.iths.felix.tamagotchi2d.GameClasses.KeyListener;
import se.iths.felix.tamagotchi2d.GameClasses.Scene;
import se.iths.felix.tamagotchi2d.GameClasses.Window;
import se.iths.felix.tamagotchi2d.Tamagotchi.TamagotchiMethods;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVG.nvgTextBox;

public class TamagotchiScene extends Scene {
    private double lastTime = System.nanoTime();
    private int width, height;
    private float dx = 260.0f;
    private float dy = 260.0f;
    private long vg;
    private TamagotchiMethods tamagotchi;
    private float TamaX, tamaY, dvdX, dvdY;
    private int fps;
    private int frames;
    private NVGColor color;

    public void init() {
        vg = Window.get().getVG();

         tamagotchi = new TamagotchiMethods();

         color = NVGColor.create()
                 .r(0.0f)
                 .g(1.0f)
                 .b(0.0f)
                 .a(1.0f);
    }

    @Override
    public void update(float deltaTime){
        updateFPS();
        updateTamagotchi(deltaTime);
    }

    private void updateFPS(){
        long currentTime = System.nanoTime();
        frames++;

        if(currentTime - lastTime >= 1_000_000_000L){
            fps = frames;
            frames = 0;
            lastTime = currentTime;
        }
    }

    private void updateTamagotchi(float deltaTime){
        String text = "";

        int key = KeyListener.getKeyPressed();

        if (key != -1) {
            text = String.valueOf((char) key);
            IO.println(text);
        }

        tamagotchi.checkIfAlive();
        if (tamagotchi.getAlive()) {
            switch (text) {
                case "1":
                    tamagotchi.feed();
                    break;
                case "2":
                    tamagotchi.play();
                    break;
                case "3":
                    tamagotchi.work();
                    break;
                case "4":
                    tamagotchi.gamble();
                    break;
                case "5":
                    tamagotchi.setAliveFalse();
                default:
            }

            nvgText(vg, 200, 120, "Latest Action: " + tamagotchi.latestAction);
            nvgText(vg, 200, 75, "What do you want to do? 1. Feed | 2. Play | 3. Work | 4. Gamble | 5. Quit.");
            nvgText(vg, 150, 1100, tamagotchi.toString());
        } else nvgText(vg, 500, 750, tamagotchi.name + " is dead ):");

        if (KeyListener.isKeyPressed(GLFW_KEY_R))
            tamagotchi = new TamagotchiMethods();
        if(KeyListener.isKeyJustPressed(GLFW_KEY_ENTER))
            Window.changeScene(0);
        drawTamagotchi(500, 500);



    }
    private void drawTamagotchi(float x, float y) {
        nvgBeginPath(vg);

        nvgRect(vg, x, y, 200, 200);

        if (tamagotchi.getAlive()) {
            nvgTextBox(vg, 500, 500, width - 2 * 20, """
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
