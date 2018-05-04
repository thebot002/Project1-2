package com.golf2k18.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.golf2k18.states.StateManager;

public class DesktopLauncher {
    public static final String TITLE = "Golf2k18";

    public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = TITLE;
		config.height = StateManager.HEIGHT;
		config.width = StateManager.WIDTH;
		config.resizable = false;
		new LwjglApplication(new StateManager(), config);
	}
}
