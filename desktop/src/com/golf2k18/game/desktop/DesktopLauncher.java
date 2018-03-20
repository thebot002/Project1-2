package com.golf2k18.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.golf2k18.game.golf_2k18;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = golf_2k18.title;	
		new LwjglApplication(new golf_2k18(), config);
	}
}
