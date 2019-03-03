package mysko.pilzhere.spacebarrier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import mysko.pilzhere.spacebarrier.entities.Enemy;
import mysko.pilzhere.spacebarrier.screens.GameScreen;

public class Level01 {
	private GameScreen screen;

	public Level01(GameScreen screen) {
		this.screen = screen;
	}

	private final int waitInterval = 3;
	private float timer = 0;
	private boolean waiting;

	public int waveCounter = 0;

	public void tick(float delta) {
		if (waveCounter >= 12) {
			screen.gameState = GameState.WIN;
		}
		
		if (screen.gameState == GameState.PLAYING) {
			timer = timer + delta;

			if (timer >= waitInterval) {
				waiting = false;

				timer = 0;
			} else {
				waiting = true;
			}

			if (!waiting) {
//			System.out.println("Enemy Spawner waiting : " + waiting); // new time to wait...

				if (screen.enemies.size() == 0) { // Spawn new enemy wave based on wavecounter.
					spawnWave(waveCounter);
					waveCounter++;
				}
			}
		}
	}

	private final float spawnMaxX = 66;
	private final float spawnMinX = 5;
	private final float spawnMaxY = 30;
	private final float spawnMinY = 12;

	private void spawnWave(int waveCount) {
		switch (waveCount) {
		case 0:
			screen.enemies.add(new Enemy(this.screen, (Gdx.graphics.getWidth() / 2) / screen.game.PPM,
					(Gdx.graphics.getHeight() / 2) / screen.game.PPM));
			break;
		case 1:
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			break;
		case 2:
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			break;
		case 3:
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			break;
		case 4:
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			break;
		case 5:
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			break;
		case 6:
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			break;
		case 7:
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			break;
		case 8:
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			break;
		case 9:
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			break;
		case 10:
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			screen.enemies.add(new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX),
					MathUtils.random(spawnMinY, spawnMaxY)));
			break;
		}

		System.err.println("Enemy wave #" + waveCounter + " spawned!");
	}

//	private void spawnEnemy(Enemy enemy, float posX, float posY) {
//		screen.enemies.add(
//				new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX), MathUtils.random(spawnMinY, spawnMaxY)));
//		screen.enemies.add(
//				new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX), MathUtils.random(spawnMinY, spawnMaxY)));
//		screen.enemies.add(
//				new Enemy(this.screen, MathUtils.random(spawnMinX, spawnMaxX), MathUtils.random(spawnMinY, spawnMaxY)));
//	}
}
