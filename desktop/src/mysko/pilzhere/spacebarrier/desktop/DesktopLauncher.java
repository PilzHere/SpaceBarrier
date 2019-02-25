package mysko.pilzhere.spacebarrier.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import mysko.pilzhere.spacebarrier.SpaceBarrier;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "SpaceBarrier";
		
		new LwjglApplication(new SpaceBarrier(), config);
	}
}
