package mysko.pilzhere.spacebarrier.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

import mysko.pilzhere.spacebarrier.SpaceBarrier;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "SpaceBarrier";
		config.foregroundFPS = 144;
		config.backgroundFPS = 60;
		config.width = 1280;
		config.height = 720;
		config.samples = 0;
		config.vSyncEnabled = false;
		config.fullscreen = false;
		config.resizable = true;
		config.initialBackgroundColor = Color.WHITE;
		
//		config.addIcon(path, fileType);
		
		new LwjglApplication(new SpaceBarrier(), config);
	}
}
