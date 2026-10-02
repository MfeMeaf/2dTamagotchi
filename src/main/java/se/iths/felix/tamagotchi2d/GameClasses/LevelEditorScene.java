package se.iths.felix.tamagotchi2d.GameClasses;

import java.awt.event.KeyEvent;


public class LevelEditorScene extends Scene {
    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;

    public LevelEditorScene() {
        IO.println("Inside level editor scene");
    }

    @Override
    public void update(float dt) {


        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }

        if (changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= dt;
            Window.get().r -= dt * 0.5;
            Window.get().g -= dt * 0.5;
            Window.get().b -= dt * 0.5;

        } else if (changingScene) {
            Window.changeScene(1);
        }
    }
}
