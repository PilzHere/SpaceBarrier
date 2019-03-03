package mysko.pilzhere.spacebarrier.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;

import mysko.pilzhere.spacebarrier.Contactslistener;
import mysko.pilzhere.spacebarrier.GameState;
import mysko.pilzhere.spacebarrier.GameUtils;
import mysko.pilzhere.spacebarrier.Level01;
import mysko.pilzhere.spacebarrier.SpaceBarrier;
import mysko.pilzhere.spacebarrier.entities.Bullet;
import mysko.pilzhere.spacebarrier.entities.Enemy;
import mysko.pilzhere.spacebarrier.entities.EnemyBullet;
import mysko.pilzhere.spacebarrier.entities.Player;
import mysko.pilzhere.spacebarrier.entities.Scale;

public class GameScreen implements Screen {

	public GameState gameState;

	public SpaceBarrier game;

	private TextureRegion texRegTitle;
	private Sprite spriteTitle;

	private TextureRegion texRegWin;
	private Sprite spriteWin;

	private TextureRegion texRegGameOver;
	private Sprite spriteGameOver;

	public GameUtils gameUtils = new GameUtils(this);

	private final ModelBuilder modelBuilder = new ModelBuilder();
	private Model model;
	private Model model2;

	private ModelInstance modelInstFloor01;
	private ModelInstance modelInstFloor02;

//	Colors
//	int clearColor01 = 0xba8af3ff; // old
	private final Color clearColor01 = new Color(186f / 255, 138f / 255, 243f / 255, 1);

//	Greens
	private final int darkerGreen = 0x73c373ff;
	private final int darkGreen = 0x84d384ff;
	private final int lightGreen = 0x94e394ff;
	private final int lighterGreen = 0xa5f3a5ff;

//	Browns
	private final int darkerBrown = 0xc6a284ff;
	private final int darkBrown = 0xd6b294ff;
	private final int lightBrown = 0xe7c3a5ff;
	private final int lighterBrown = 0xf7d3b5ff;

	private Pixmap pix;
	private Pixmap pix2;
	private Texture tex;
	private Texture tex2;

	private TextureRegion texRegBgMoving01;
	private TextureRegion texRegBgStatic01;
	private Sprite spriteBgMoving01;
	private Sprite spriteBgStatic01;

	public Player player;

	private Pixmap pixEnergyBar;
	private Texture texEnergyBar;
	private Sprite barEnergySprite;

	private Pixmap pixHpBar;
	private Texture texHpBar;
	private Sprite barHpSprite;

	public Level01 level01;

//	private Box2DDebugRenderer b2dDebugRenderer;
	public World world;
	public Contactslistener contactListener;

	public PerspectiveCamera camPersp;
//	private OrthographicCamera camOrtho;

	public List<Enemy> enemies = new ArrayList<Enemy>();
	public List<Bullet> bullets = new ArrayList<Bullet>();
	public List<EnemyBullet> enemyBullets = new ArrayList<EnemyBullet>();

	final int floorWidth = 400;
	final int floorHeight = 0; // Floor is flat!
	final int floorDepth = 200;

	final Vector3 floorPos = new Vector3(0, 0, -15);
	final Vector3 floorPosOffset = new Vector3(0, 0, floorDepth);

	public GameScreen(SpaceBarrier game) {
		this.game = game;

		gameState = GameState.TITLE_SCREEN;

		initPhysicsEngine();
		contactListener = new Contactslistener();
		world = setupPhysicsWorld(world, contactListener);

//		b2dDebugRenderer = initPhysicsDebugRenderer(b2dDebugRenderer);
		camPersp = initPerspectiveCam(camPersp);

//		camOrtho = new OrthographicCamera();
//		camOrtho = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		camOrtho.position.set(new Vector3(0, 0, 0));
//		camOrtho.lookAt(new Vector3(0, 0, -1));
//		camOrtho.near = 0.001f;
//		camOrtho.far = 100;
//		camOrtho.update();

		texRegBgMoving01 = new TextureRegion(game.atlas01.findRegion("bg02"));
		spriteBgMoving01 = new Sprite(texRegBgMoving01);
		spriteBgMoving01.setSize((spriteBgMoving01.getWidth() / game.PPM) * 4,
				(spriteBgMoving01.getHeight() / game.PPM) * 4);
		spriteBgMoving01.setPosition(((Gdx.graphics.getWidth() / 2) / game.PPM) - (spriteBgMoving01.getWidth() / 2),
				222f / game.PPM);

		texRegBgStatic01 = new TextureRegion(game.atlas01.findRegion("bg01"));
		spriteBgStatic01 = new Sprite(texRegBgStatic01);
		spriteBgStatic01.setSize((spriteBgStatic01.getWidth() / game.PPM) * 4,
				(spriteBgStatic01.getHeight() / game.PPM) * 4);
		spriteBgStatic01.setPosition(((Gdx.graphics.getWidth() / 2) / game.PPM) - (spriteBgStatic01.getWidth() / 2),
				/* 222f */ 0 / game.PPM);

//		Setup textures
		final int pixWidth = 512;
		final int pixHeight = 1024;

		pix = paintPixMap(pix, pixWidth, pixHeight, darkerGreen, lightGreen, darkGreen, lighterGreen);
		tex = pixToTextureWithNearestFilter(tex, pix);

		pix2 = paintPixMap(pix2, pixWidth, pixHeight, darkerBrown, lightBrown, darkBrown, lighterBrown);
		tex2 = pixToTextureWithNearestFilter(tex2, pix2);

//		Create models
		model = buildFloor(floorWidth, floorHeight, floorDepth, tex);
		modelInstFloor01 = new ModelInstance(model);
		modelInstFloor01.transform.setToTranslation(floorPos);

		model2 = buildFloor(floorWidth, floorHeight, floorDepth, tex2);
		modelInstFloor02 = new ModelInstance(model2);
		modelInstFloor02.transform.setToTranslation(floorPos.cpy().add(floorPosOffset));

		level01 = new Level01(this);

		player = new Player(this);

		pixEnergyBar = new Pixmap(1, 1, Format.RGB888);
		pixEnergyBar.setColor(Color.YELLOW);
		pixEnergyBar.fill();

		texEnergyBar = new Texture(pixEnergyBar);
		texEnergyBar.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		barEnergySprite = new Sprite(texEnergyBar);
		barEnergySprite.setSize(((barEnergySprite.getWidth() * 100) / game.PPM) * 4,
				((barEnergySprite.getHeight() * 4) / game.PPM) * 4);

		barEnergySprite.setPosition(((Gdx.graphics.getWidth() / 2) / 16) - barEnergySprite.getWidth() / 2, 24 / 16);

		pixHpBar = new Pixmap(1, 1, Format.RGB888);
		pixHpBar.setColor(Color.RED);
		pixHpBar.fill();

		texHpBar = new Texture(pixHpBar);
		texHpBar.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		barHpSprite = new Sprite(texHpBar);
		barHpSprite.setSize(((barHpSprite.getWidth() * 100) / game.PPM) * 4,
				((barHpSprite.getHeight() * 4) / game.PPM) * 4);

		barHpSprite.setPosition(((Gdx.graphics.getWidth() / 2) / 16) - barHpSprite.getWidth() / 2, 10 / 16);

		texRegTitle = new TextureRegion(game.atlas01.findRegion("title"));
		spriteTitle = new Sprite(texRegTitle);
		spriteTitle.setSize(((spriteTitle.getWidth() * 2) / game.PPM) * 4,
				((spriteTitle.getHeight() * 2) / game.PPM) * 4);
		spriteTitle.setPosition(((Gdx.graphics.getWidth() / 2) / 16) - spriteTitle.getWidth() / 2,
				((Gdx.graphics.getHeight() / 2) / 16) - spriteTitle.getHeight() / 2);

		texRegWin = new TextureRegion(game.atlas01.findRegion("win"));
		spriteWin = new Sprite(texRegWin);
		spriteWin.setSize(((spriteWin.getWidth() * 2) / game.PPM) * 4, ((spriteWin.getHeight() * 2) / game.PPM) * 4);
		spriteWin.setPosition(((Gdx.graphics.getWidth() / 2) / 16) - spriteWin.getWidth() / 2,
				((Gdx.graphics.getHeight() / 2) / 16) - spriteWin.getHeight() / 2);

		texRegGameOver = new TextureRegion(game.atlas01.findRegion("gameOver"));
		spriteGameOver = new Sprite(texRegGameOver);
		spriteGameOver.setSize(((spriteGameOver.getWidth() * 2) / game.PPM) * 4,
				((spriteGameOver.getHeight() * 2) / game.PPM) * 4);
		spriteGameOver.setPosition(((Gdx.graphics.getWidth() / 2) / 16) - spriteGameOver.getWidth() / 2,
				((Gdx.graphics.getHeight() / 2) / 16) - spriteGameOver.getHeight() / 2);

	}

	private Model buildFloor(int width, int height, int depth, Texture tex) {
		Model model = modelBuilder.createBox(width, height, depth, new Material(TextureAttribute.createDiffuse(tex)),
				Usage.Position | Usage.TextureCoordinates);

		return model;
	}

	private Pixmap paintPixMap(Pixmap pix, int width, int height, int color1, int color2, int color3, int color4) {
		pix = new Pixmap(width, height, Format.RGB888);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i % 2 == 0) { // Let's paint pixels!
					if (j % 2 == 0)
						pix.drawPixel(i, j, color1);
					else
						pix.drawPixel(i, j, color2);
				} else {
					if (j % 2 == 0)
						pix.drawPixel(i, j, color3);
					else
						pix.drawPixel(i, j, color4);
				}
			}
		}

		return pix;
	}

	private Texture pixToTextureWithNearestFilter(Texture tex, Pixmap pix) {
		tex = new Texture(pix);
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		return tex;
	}

	private void initPhysicsEngine() {
		Box2D.init();
	}

	private World setupPhysicsWorld(World world, ContactListener listener) {
		final Vector2 gravity = Vector2.Zero;
		final boolean simulateInactiveBodies = true;

		world = new World(gravity, simulateInactiveBodies);

		world.setContactListener(listener);

		return world;
	}

	private Box2DDebugRenderer initPhysicsDebugRenderer(Box2DDebugRenderer renderer) {
		renderer = new Box2DDebugRenderer();
		return renderer;
	}

	private PerspectiveCamera initPerspectiveCam(PerspectiveCamera cam) {
		final int camFov = 70;
		final Vector3 camPerspSpawnPos = new Vector3(0, 0.5f, 0);
		final Vector3 camPerspLookPos = new Vector3(0, 0.75f, -1);
		final float camPerspNear = 0.001f;
		final float camPerspFar = 100;

		cam = new PerspectiveCamera(camFov, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(camPerspSpawnPos);
		cam.lookAt(camPerspLookPos);
		cam.near = camPerspNear;
		cam.far = camPerspFar;
		cam.update();

		return cam;
	}

	@Override
	public void show() {

	}

	private float floorSpeedY = 13f / 3;
	private float floorSpeedX = 5.25f / 2;

	private final int camPosSpeed = 3;
	private final float camDirSpeed = 0.4f;

	public final int playerMinX = 5;
	public final int playerMaxX = 75;
	public final int playerMinY = 8;
	public final int playerMaxY = 40;
	private final int playerSpeedX = 30;
	private final int playerSpeedY = 20;

	private final int bgSpeedX = 12;

	private final float spriteBgMovingMinX = -0.41286284f;
	private final float spriteBgMovingMaxX = -28.254248f;

	public boolean flipPlayerX = false;

	private void reset() {
		for (Bullet bullet : bullets) {
			bullet.remove = true;
		}

		for (Enemy enemy : enemies) {
			enemy.remove = true;
		}

		for (EnemyBullet bullet : enemyBullets) {
			bullet.remove = true;
		}

//		remove bullets		
		for (int i = 0; i < bullets.size();) {
			Bullet bullet = bullets.get(i);
			if (bullet.remove) {
				bullet.remove(0);

				int lastBullet = bullets.size() - 1;

				bullets.set(i, bullets.get(lastBullet));
				bullets.remove(lastBullet);
//				totalBullets--;
			} else {
				i++;
			}
		}

//		remove Enemy bullets
		for (int i = 0; i < enemyBullets.size();) {
			EnemyBullet bullet = enemyBullets.get(i);
			if (bullet.remove) {
				bullet.remove(0);

				int lastBullet = enemyBullets.size() - 1;

				enemyBullets.set(i, enemyBullets.get(lastBullet));
				enemyBullets.remove(lastBullet);
//				totalBullets--;
			} else {
				i++;
			}
		}

//		remove enemies
		for (int i = 0; i < enemies.size();) {
			Enemy enemy = enemies.get(i);
			if (enemy.remove) {
				enemy.remove(0);

				int lastEnemy = enemies.size() - 1;

				enemies.set(i, enemies.get(lastEnemy));
				enemies.remove(lastEnemy);
//				totalEnemies--;
			} else {
				i++;
			}
		}

		spriteBgMoving01.setPosition(((Gdx.graphics.getWidth() / 2) / game.PPM) - (spriteBgMoving01.getWidth() / 2),
				222f / game.PPM);
		spriteBgStatic01.setPosition(((Gdx.graphics.getWidth() / 2) / game.PPM) - (spriteBgStatic01.getWidth() / 2),
				/* 222f */ 0 / game.PPM);

		modelInstFloor01.transform.setToTranslation(floorPos);
		modelInstFloor02.transform.setToTranslation(floorPos.cpy().add(floorPosOffset));

		level01 = new Level01(this);

		player = new Player(this);

		gameState = GameState.TITLE_SCREEN;
	}

	private void handleInput(float delta) {
		if (gameState == GameState.GAME_OVER) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				reset();
			}
		} else if (gameState == GameState.WIN) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				reset();
			}
		} else if (gameState == GameState.TITLE_SCREEN) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.W)
					|| Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.S)
					|| Gdx.input.isKeyJustPressed(Input.Keys.D)) {
				gameState = GameState.PLAYING;
			}
		} else if (gameState == GameState.PLAYING) {
//			TEST
			if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
				enemyBullets.add(new EnemyBullet(this, enemies.get(0).body.getPosition().x,
						enemies.get(0).body.getPosition().y, getCurrentTime));
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				if (player.energy > 0) {
					bullets.add(new Bullet(this, player.body.getTransform().getPosition().x,
							player.body.getTransform().getPosition().y, getCurrentTime));
					player.energy -= 20;
				}
			}

			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				if (player.body.getPosition().y <= playerMaxY) {
					player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, playerSpeedY));

//				enemy
					for (Enemy enemy : enemies)
						enemy.body.setLinearVelocity(new Vector2(enemy.body.getLinearVelocity().x, -playerSpeedY));

					camPersp.position.y += floorSpeedY / camPosSpeed * delta;
					camPersp.direction.y = camPersp.direction.y - camDirSpeed * delta;
				} else {
					player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, 0));

//				enemy
					for (Enemy enemy : enemies)
						enemy.body.setLinearVelocity(new Vector2(enemy.body.getLinearVelocity().x, 0));
				}
			} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				if (player.body.getPosition().y >= playerMinY) {
					player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, -playerSpeedY));

//				enemy
					for (Enemy enemy : enemies)
						enemy.body.setLinearVelocity(new Vector2(enemy.body.getLinearVelocity().x, playerSpeedY));

					camPersp.position.y -= floorSpeedY / camPosSpeed * delta;
					camPersp.direction.y = camPersp.direction.y + camDirSpeed * delta;
				} else {
					player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, 0));

//				enemy
					for (Enemy enemy : enemies)
						enemy.body.setLinearVelocity(new Vector2(enemy.body.getLinearVelocity().x, 0));
				}
			} else {
				player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, 0));

//			enemy
				for (Enemy enemy : enemies)
					enemy.body.setLinearVelocity(new Vector2(enemy.body.getLinearVelocity().x, 0));
			}

			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				flipPlayerX = false;

				if (player.body.getPosition().x > playerMinX) {
					player.body.setLinearVelocity(new Vector2(-playerSpeedX, player.body.getLinearVelocity().y));

//				enemy
					for (Enemy enemy : enemies)
						enemy.body.setLinearVelocity(new Vector2(playerSpeedX, enemy.body.getLinearVelocity().y));

					modelInstFloor01.transform.translate(new Vector3(floorSpeedX * delta, 0, 0));
					modelInstFloor02.transform.translate(new Vector3(floorSpeedX * delta, 0, 0));

					spriteBgMoving01.setPosition(spriteBgMoving01.getX() + bgSpeedX * delta, spriteBgMoving01.getY());
				} else {
					player.body.setLinearVelocity(new Vector2(0, player.body.getLinearVelocity().y));

//				enemy
					for (Enemy enemy : enemies)
						enemy.body.setLinearVelocity(new Vector2(0, enemy.body.getLinearVelocity().y));
				}
			} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				flipPlayerX = true;

				if (player.body.getPosition().x < playerMaxX) {
					player.body.setLinearVelocity(new Vector2(playerSpeedX, player.body.getLinearVelocity().y));

//				enemy
					for (Enemy enemy : enemies)
						enemy.body.setLinearVelocity(new Vector2(-playerSpeedX, enemy.body.getLinearVelocity().y));

					modelInstFloor01.transform.translate(new Vector3(-floorSpeedX * delta, 0, 0));
					modelInstFloor02.transform.translate(new Vector3(-floorSpeedX * delta, 0, 0));

					spriteBgMoving01.setPosition(spriteBgMoving01.getX() - bgSpeedX * delta, spriteBgMoving01.getY());
				} else {
					player.body.setLinearVelocity(new Vector2(0, player.body.getLinearVelocity().y));

//				enemy
					for (Enemy enemy : enemies)
						enemy.body.setLinearVelocity(new Vector2(0, enemy.body.getLinearVelocity().y));
				}
			} else {
				player.body.setLinearVelocity(new Vector2(0, player.body.getLinearVelocity().y));

//			enemy
				for (Enemy enemy : enemies)
					enemy.body.setLinearVelocity(new Vector2(0, enemy.body.getLinearVelocity().y));
			}

//		System.out.println(Gdx.app.getJavaHeap() / (1024L * 1024L) + " MiB");
		} else {
			player.body.setLinearVelocity(Vector2.Zero);
		}
	}

//	private final float spriteBgMoving01MaxY = 13.875f;
//	private final float spriteBgMoving01MinY = 13.3125f;

	private final float camPerspPosMinY = 0.5f;
	private final float camPerspPosMaxY = 2.75f;
	private final float camPerspDirMinY = -0.2f;
	private final float camPerspDirMaxY = 0.24253564f;

	private final float floorResetPosZ = 100;
	private final float floorMaxX = 14;

	private final int spriteBgMoving01PosY = 350;
	private final int spriteBgMoving01HolyNumber = 30;

	public long getCurrentTime;

	private void tick(float delta) {
		updateTime();

		moveFloorTowardsCam(delta);
		limitCameraPosY(camPerspPosMinY, camPerspPosMaxY);
		limitCameraDirY(camPerspDirMinY, camPerspDirMaxY);
		loopFloor(floorResetPosZ);
		keepFloorInView(floorMaxX);

		level01.tick(delta);

		player.tick(delta);

		barEnergySprite.setSize(((player.energy / 10f) / game.PPM) * 4,
				((barEnergySprite.getHeight() * 4) / game.PPM) * 4);
		barHpSprite.setSize(((player.hp / 10f) / game.PPM) * 4, ((barHpSprite.getHeight() * 4) / game.PPM) * 4);

		for (Bullet bullet : bullets) {
			bullet.tick(delta);
		}

		for (EnemyBullet bullet : enemyBullets) {
			bullet.tick(delta);
		}

		for (Enemy enemy : enemies)
			enemy.tick(delta);

//		remove bullets
		for (int i = 0; i < bullets.size();) {
			Bullet bullet = bullets.get(i);
			if (bullet.remove) {
				bullet.remove(delta);

				int lastBullet = bullets.size() - 1;

				bullets.set(i, bullets.get(lastBullet));
				bullets.remove(lastBullet);
//				totalBullets--;
			} else {
				i++;
			}
		}

//		remove Enemy bullets
		for (int i = 0; i < enemyBullets.size();) {
			EnemyBullet bullet = enemyBullets.get(i);
			if (bullet.remove) {
				bullet.remove(delta);

				int lastBullet = enemyBullets.size() - 1;

				enemyBullets.set(i, enemyBullets.get(lastBullet));
				enemyBullets.remove(lastBullet);
//				totalBullets--;
			} else {
				i++;
			}
		}

//		remove enemies
		for (int i = 0; i < enemies.size();) {
			Enemy enemy = enemies.get(i);
			if (enemy.remove) {
				enemy.remove(delta);

				int lastEnemy = enemies.size() - 1;

				enemies.set(i, enemies.get(lastEnemy));
				enemies.remove(lastEnemy);
//				totalEnemies--;
			} else {
				i++;
			}
		}

//		spriteBgMoving01.setY(MathUtils.clamp(spriteBgMoving01.getY(), spriteBgMoving01MinY, spriteBgMoving01MaxY)); // huh?
		spriteBgMoving01.setPosition(spriteBgMoving01.getX(),
				((spriteBgMoving01PosY / game.PPM) - (camPersp.direction.y * spriteBgMoving01HolyNumber)));

//		System.out.println(spriteBgMoving01.getX());

		MathUtils.clamp(spriteBgMoving01.getX(), spriteBgMovingMinX, spriteBgMovingMaxX);

		camPersp.update();
//		camOrtho.update();
	}

	private void updateTime() {
		getCurrentTime = gameUtils.getCurrentTime();
	}

	private void moveFloorTowardsCam(float delta) {
		modelInstFloor01.transform.translate(new Vector3(0, 0, floorSpeedY * delta));
		modelInstFloor02.transform.translate(new Vector3(0, 0, floorSpeedY * delta));
	}

	private void limitCameraDirY(float minY, float maxY) {
		camPersp.direction.y = MathUtils.clamp(camPersp.direction.y, minY, maxY);
	}

	private void limitCameraPosY(float minY, float maxY) {
		camPersp.position.y = MathUtils.clamp(camPersp.position.y, minY, maxY);
	}

	private void loopFloor(float zResetPos) {
		if (modelInstFloor01.transform.getTranslation(new Vector3()).z >= zResetPos) {
			modelInstFloor01.transform.setTranslation(modelInstFloor02.transform.getTranslation(new Vector3()).cpy()
					.add(new Vector3(0, 0, -zResetPos * 2)));
			Gdx.app.log("Floor", "Floor 1 repositioned.");
		}

		if (modelInstFloor02.transform.getTranslation(new Vector3()).z >= zResetPos) {
			modelInstFloor02.transform.setTranslation(modelInstFloor01.transform.getTranslation(new Vector3()).cpy()
					.add(new Vector3(0, 0, -zResetPos * 2)));
			Gdx.app.log("Floor", "Floor 2 repositioned.");
		}
	}

	private void keepFloorInView(float maxX) {
		Vector3 translation = modelInstFloor01.transform.getTranslation(new Vector3());
		if (translation.x <= -maxX)
			modelInstFloor01.transform.setTranslation(new Vector3(-maxX, translation.y, translation.z));
		else if (translation.x >= maxX)
			modelInstFloor01.transform.setTranslation(new Vector3(maxX, translation.y, translation.z));

		translation.set(modelInstFloor02.transform.getTranslation(new Vector3()));
		if (translation.x <= -maxX)
			modelInstFloor02.transform.setTranslation(new Vector3(-maxX, translation.y, translation.z));
		else if (translation.x >= maxX)
			modelInstFloor02.transform.setTranslation(new Vector3(maxX, translation.y, translation.z));
	}

	@Override
	public void render(float delta) {
		handleInput(delta);
		tick(delta);

		game.spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth() / game.PPM,
				Gdx.graphics.getHeight() / game.PPM);

		game.fbo01.begin(); // fbo1
		clearFbo(clearColor01);

		game.spriteBatch.begin();
		spriteBgStatic01.draw(game.spriteBatch); // render static background
		game.spriteBatch.end();

		game.modelBatch.begin(camPersp); // render models
		game.modelBatch.render(modelInstFloor01);
		game.modelBatch.render(modelInstFloor02);
		game.modelBatch.end();

		game.fbo01.end(); // fbo1 end

		game.spriteBatch.begin();
		game.spriteBatch.draw(game.fbo01.getColorBufferTexture(), // render fbo2
				Gdx.graphics.getWidth() / 2 - game.fboVirtualWidth / 2,
				(Gdx.graphics.getHeight() / 2 - game.fboVirtualHeight / 2), game.fboVirtualWidth / game.PPM,
				game.fboVirtualHeight / game.PPM, 0, 0, 1, 1);
		game.spriteBatch.end();

		game.fbo02.begin(); // fbo2
		clearFbo(0, 0, 0, 0);

		game.spriteBatch.begin();
		spriteBgMoving01.draw(game.spriteBatch); // render moving background
		game.spriteBatch.end();

		game.fbo02.end(); // fbo2 end

		game.spriteBatch.begin();
		game.spriteBatch.draw(game.fbo02.getColorBufferTexture(), // render fbo1
				Gdx.graphics.getWidth() / 2 - game.fboVirtualWidth / 2,
				(Gdx.graphics.getHeight() / 2 - game.fboVirtualHeight / 2), game.fboVirtualWidth / game.PPM,
				game.fboVirtualHeight / game.PPM, 0, 0, 1, 1);

//		Ugly but works!
		for (Enemy enemy : enemies)
			if (enemy.scale == Scale.TINIEST)
				enemy.enemySprite.draw(game.spriteBatch);

		for (Bullet bullet : bullets) {
			if (bullet.scale == Scale.TINIEST)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (EnemyBullet bullet : enemyBullets) {
			if (bullet.scale == Scale.TINIEST)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (Enemy enemy : enemies)
			if (enemy.scale == Scale.TINY)
				enemy.enemySprite.draw(game.spriteBatch);

		for (Bullet bullet : bullets) {
			if (bullet.scale == Scale.TINY)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (EnemyBullet bullet : enemyBullets) {
			if (bullet.scale == Scale.TINY)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (Enemy enemy : enemies)
			if (enemy.scale == Scale.SMALLEST)
				enemy.enemySprite.draw(game.spriteBatch);

		for (Bullet bullet : bullets) {
			if (bullet.scale == Scale.SMALLEST)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (EnemyBullet bullet : enemyBullets) {
			if (bullet.scale == Scale.SMALLEST)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (Enemy enemy : enemies)
			if (enemy.scale == Scale.SMALL)
				enemy.enemySprite.draw(game.spriteBatch);

		for (Bullet bullet : bullets) {
			if (bullet.scale == Scale.SMALL)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (EnemyBullet bullet : enemyBullets) {
			if (bullet.scale == Scale.SMALL)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (Enemy enemy : enemies)
			if (enemy.scale == Scale.MEDIUM)
				enemy.enemySprite.draw(game.spriteBatch);

		for (Bullet bullet : bullets) {
			if (bullet.scale == Scale.MEDIUM)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (EnemyBullet bullet : enemyBullets) {
			if (bullet.scale == Scale.MEDIUM)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (Enemy enemy : enemies)
			if (enemy.scale == Scale.LARGE)
				enemy.enemySprite.draw(game.spriteBatch);

		for (Bullet bullet : bullets) {
			if (bullet.scale == Scale.LARGE)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (EnemyBullet bullet : enemyBullets) {
			if (bullet.scale == Scale.LARGE)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (Enemy enemy : enemies)
			if (enemy.scale == Scale.LARGEST)
				enemy.enemySprite.draw(game.spriteBatch);

		for (Bullet bullet : bullets) {
			if (bullet.scale == Scale.LARGEST)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

		for (EnemyBullet bullet : enemyBullets) {
			if (bullet.scale == Scale.LARGEST)
				bullet.bulletSprite.draw(game.spriteBatch);
		}

//		for (int i = bullets.size() - 1; i >= 0; i--) {
//			bullets.get(bullets.size() - 1 - i).bulletSprite.draw(game.spriteBatch); // render bullets in correct order
//		}

		player.playerSprite.draw(game.spriteBatch); // render player

//		gui
		barEnergySprite.draw(game.spriteBatch);
		barHpSprite.draw(game.spriteBatch);

		switch (gameState) {
		case GAME_OVER:
			spriteGameOver.draw(game.spriteBatch);
			break;
		case TITLE_SCREEN:
			spriteTitle.draw(game.spriteBatch);
			break;
		case WIN:
			spriteWin.draw(game.spriteBatch);
			break;
		}

		game.spriteBatch.end();

//		b2dDebugRenderer.render(world, game.spriteBatch.getProjectionMatrix());

		doPhysicsStep(delta);
	}

	private float accumulator = 0;
	private float physicsTimeStep = 1 / 60f;

	private void doPhysicsStep(float deltaTime) {
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= physicsTimeStep) {
			world.step(physicsTimeStep, 6, 2);
			accumulator -= physicsTimeStep;
		}
	}

	private void clearFbo(float r, float g, float b, float a) {
		Gdx.gl.glClearColor(r, g, b, a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}

	private void clearFbo(Color color) {
		clearFbo(color.r, color.g, color.b, color.a);
	}

	@Override
	public void resize(int width, int height) {
		camPersp.update();
//		camOrtho.update();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		pix.dispose();
		pix2.dispose();

		tex.dispose();
		tex2.dispose();

		pixEnergyBar.dispose();
		pixHpBar.dispose();

		texEnergyBar.dispose();
		texHpBar.dispose();

//		b2dDebugRenderer.dispose();
		world.dispose();
	}

}
