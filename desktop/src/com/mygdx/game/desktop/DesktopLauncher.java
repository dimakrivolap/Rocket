package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GameScreen;
import com.mygdx.game.RocketGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name","dima");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new RocketGame(), config);
	}
}
