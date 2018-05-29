package com.golf2k18.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.golf2k18.StateManager;

/**
 * Class which provides the main method for running the libGDX application on desktops. It also specifies some functionality of the program.
 */
public class DesktopLauncher {
    public static final String TITLE = "Golf2k18";

	/**
	 * Main method for running the libGDX application on desktop pc's.
	 * @param args String[] of arguments a user can pass when starting the program for a commandline for example.
	 */
    public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = TITLE;
		config.height = StateManager.HEIGHT;
		config.width = StateManager.WIDTH;
		config.resizable = false;
		new LwjglApplication(new StateManager(), config);
	}
}
