package mysko.pilzhere.spacebarrier.screens;

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import mysko.pilzhere.spacebarrier.SpaceBarrier;
import mysko.pilzhere.spacebarrier.entities.Player;

public class GameScreen implements Screen {
	public SpaceBarrier game;

	private final ModelBuilder modelBuilder = new ModelBuilder();
	private Model model;
	private Model model2;

	private ModelInstance modelInstFloor01;
	private ModelInstance modelInstFloor02;

//	Colors
//	int clearColor01 = 0xba8af3ff;
	private final Color clearColor01 = new Color(186 / 255f, 138 / 255f, 243 / 255f, 1);

//	Green
	private final int darkerGreen = 0x73c373ff;
	private final int darkGreen = 0x84d384ff;
	private final int lightGreen = 0x94e394ff;
	private final int lighterGreen = 0xa5f3a5ff;

//	Brown
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

	private Player player;

	private Box2DDebugRenderer b2dDebugRenderer;
	public World world;

	private PerspectiveCamera camPersp;
//	private OrthographicCamera camOrtho;

	public GameScreen(SpaceBarrier game) {
		this.game = game;

		initPhysicsEngine();
		world = setupPhysicsWorld(world);
		b2dDebugRenderer = initPhysicsDebugRenderer(b2dDebugRenderer);
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
		spriteBgMoving01.setSize(spriteBgMoving01.getWidth() / game.PPM, spriteBgMoving01.getHeight() / game.PPM);
		spriteBgMoving01.setPosition(((Gdx.graphics.getWidth() / 2) / game.PPM) - (spriteBgMoving01.getWidth() / 2),
				222f / game.PPM);

		texRegBgStatic01 = new TextureRegion(game.atlas01.findRegion("bg01"));
		spriteBgStatic01 = new Sprite(texRegBgStatic01);
		spriteBgStatic01.setSize(spriteBgStatic01.getWidth() / game.PPM, spriteBgStatic01.getHeight() / game.PPM);
		spriteBgStatic01.setPosition(((Gdx.graphics.getWidth() / 2) / game.PPM) - (spriteBgStatic01.getWidth() / 2),
				222f / game.PPM);

//		Setup textures
		int pixWidth = 512;
		int pixHeight = 1024;

		pix = paintPixMap(pix, pixWidth, pixHeight, darkerGreen, lightGreen, darkGreen, lighterGreen);
		tex = new Texture(pix);
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		pix2 = paintPixMap(pix2, pixWidth, pixHeight, darkerBrown, lightBrown, darkBrown, lighterBrown);
		tex2 = new Texture(pix2);
		tex2.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

//		Create models
		model = modelBuilder.createBox(400, 0, 200, new Material(TextureAttribute.createDiffuse(tex)),
				Usage.Position | Usage.TextureCoordinates);
		modelInstFloor01 = new ModelInstance(model);
		modelInstFloor01.transform.setToTranslation(new Vector3(0, 0, -15));

		model2 = modelBuilder.createBox(400, 0, 200, new Material(TextureAttribute.createDiffuse(tex2)),
				Usage.Position | Usage.TextureCoordinates);
		modelInstFloor02 = new ModelInstance(model2);
		modelInstFloor02.transform.setToTranslation(new Vector3(0, 0, -15 - 200));

		player = new Player(this);
	}

	private Pixmap paintPixMap(Pixmap pix, int width, int height, int color1, int color2, int color3, int color4) {
		pix = new Pixmap(width, height, Format.RGB888);

//		Paint pixels
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0)
						pix.drawPixel(i, j, darkerGreen);
					else
						pix.drawPixel(i, j, lightGreen);
				} else {
					if (j % 2 == 0)
						pix.drawPixel(i, j, darkGreen);
					else
						pix.drawPixel(i, j, lighterGreen);
				}
			}
		}

		return pix;
	}

	private void initPhysicsEngine() {
		Box2D.init();
	}

	private World setupPhysicsWorld(World world) {
		final Vector2 gravity = Vector2.Zero;
		final boolean simulateInactiveBodies = true;

		world = new World(gravity, simulateInactiveBodies);
		
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

	private float floorSpeedY = 13 / 3;
	private float floorSpeedX = 5.25f / 2;

	private final int playerMinX = 5;
	private final int playerMaxX = 75;

	private final int playerMinY = 5;
	private final int playerMaxY = 40;

	private final int playerSpeedX = 30;
	private final int playerSpeedY = 20;

	private final int bgSpeedX = 12;

	private void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//			modelInstFloor01.transform.translate(new Vector3(0, 0, floorSpeedY * delta));
//			modelInstFloor02.transform.translate(new Vector3(0, 0, floorSpeedY * delta));

			if (player.body.getPosition().y <= playerMaxY) {
				player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, playerSpeedY));
				camPersp.position.y += floorSpeedY / 3 * delta;

				camPersp.direction.y = camPersp.direction.y - 0.4f * delta;
			} else {
				player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, 0));
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//			modelInstFloor01.transform.translate(new Vector3(0, 0, -floorSpeedY * delta));
//			modelInstFloor02.transform.translate(new Vector3(0, 0, -floorSpeedY * delta));

			if (player.body.getPosition().y >= playerMinY) {
				player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, -playerSpeedY));
				camPersp.position.y -= floorSpeedY / 3 * delta;

				camPersp.direction.y = camPersp.direction.y + 0.4f * delta;
			} else {
				player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, 0));
			}
		} else {
			player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, 0));
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (player.body.getPosition().x > playerMinX) { // 100
				player.body.setLinearVelocity(new Vector2(-playerSpeedX, player.body.getLinearVelocity().y));

				modelInstFloor01.transform.translate(new Vector3(floorSpeedX * delta, 0, 0));
				modelInstFloor02.transform.translate(new Vector3(floorSpeedX * delta, 0, 0));

				spriteBgMoving01.setPosition(spriteBgMoving01.getX() + bgSpeedX * delta, spriteBgMoving01.getY());
			} else {
				player.body.setLinearVelocity(new Vector2(0, player.body.getLinearVelocity().y));
//				player.body.getPosition().set(new Vector2(playerMinX, player.body.getPosition().y));
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (player.body.getPosition().x < playerMaxX) { // 1180
				player.body.setLinearVelocity(new Vector2(playerSpeedX, player.body.getLinearVelocity().y));

				modelInstFloor01.transform.translate(new Vector3(-floorSpeedX * delta, 0, 0));
				modelInstFloor02.transform.translate(new Vector3(-floorSpeedX * delta, 0, 0));

				spriteBgMoving01.setPosition(spriteBgMoving01.getX() - bgSpeedX * delta, spriteBgMoving01.getY());
			} else {
				player.body.setLinearVelocity(new Vector2(0, player.body.getLinearVelocity().y));
//				player.body.getPosition().set(new Vector2(playerMaxX, player.body.getPosition().y));
			}
		} else {
			player.body.setLinearVelocity(new Vector2(0, player.body.getLinearVelocity().y));
		}

//		System.out.println(player.body.getLinearVelocity());
//		System.out.println(player.body.getPosition());
//		System.out.println(camPersp.position.y);

//		System.out.println(camPersp.direction);

//		System.out.println(spriteBgMoving01.getY());

//		System.out.println(Gdx.app.getJavaHeap() / (1024L * 1024L) + " MiB");
	}

	private final float spriteBgMoving01MaxY = 13.875f;
	private final float spriteBgMoving01MinY = 13.3125f;

	private void tick(float delta) {
		moveFloorTowardsCam(delta);
		limitCameraPosY(0.5f, 2.75f);
		limitCameraDirY(-0.2f, 0.24253564f);
		loopFloor(100);
		keepFloorInView(14);

		player.tick(delta);

		if (spriteBgMoving01.getY() > spriteBgMoving01MaxY) {
			spriteBgMoving01.setPosition(spriteBgMoving01.getX(), spriteBgMoving01MaxY);
		} else if (spriteBgMoving01.getY() < spriteBgMoving01MinY) {
			spriteBgMoving01.setPosition(spriteBgMoving01.getX(), spriteBgMoving01MinY);
		}

//		spriteBg01.setPosition(spriteBg01.getX(), ((225/16)  - (camPersp.position.y / 4)));
		spriteBgMoving01.setPosition(spriteBgMoving01.getX(), ((350 / game.PPM) - (camPersp.direction.y * 30)));

		camPersp.update();
//		camOrtho.update();
	}
	
	private void moveFloorTowardsCam(float delta) {
		modelInstFloor01.transform.translate(new Vector3(0, 0, floorSpeedY * delta));
		modelInstFloor02.transform.translate(new Vector3(0, 0, floorSpeedY * delta));
	}

	private void limitCameraDirY(float minY, float maxY) {
		if (camPersp.direction.y <= minY) {
			camPersp.direction.y = minY;
		} else if (camPersp.direction.y >= maxY) {
			camPersp.direction.y = maxY;
		}
	}

	private void limitCameraPosY(float minY, float maxY) {
		if (camPersp.position.y <= minY)
			camPersp.position.y = minY;
		else if (camPersp.position.y >= maxY)
			camPersp.position.y = maxY;
	}

	private void loopFloor(float zResetPos) {
		if (modelInstFloor01.transform.getTranslation(new Vector3()).z >= zResetPos) {
			modelInstFloor01.transform.setTranslation(
					modelInstFloor02.transform.getTranslation(new Vector3()).cpy().add(new Vector3(0, 0, -200)));
			Gdx.app.log("Floor", "Floor 1 repositioned.");
		}

		if (modelInstFloor02.transform.getTranslation(new Vector3()).z >= zResetPos) {
			modelInstFloor02.transform.setTranslation(
					modelInstFloor01.transform.getTranslation(new Vector3()).cpy().add(new Vector3(0, 0, -200)));
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

//		fbo2
		game.fbo01.begin();
		clearFbo(clearColor01);

		game.spriteBatch.begin();
//		render static background
		spriteBgStatic01.draw(game.spriteBatch);
		game.spriteBatch.end();

//		render models
		game.modelBatch.begin(camPersp);
		game.modelBatch.render(modelInstFloor01);
		game.modelBatch.render(modelInstFloor02);
		game.modelBatch.end();

		game.fbo01.end();
//		fbo2 end

		game.spriteBatch.begin();
//		render fbo2
		game.spriteBatch.draw(game.fbo01.getColorBufferTexture(),
				Gdx.graphics.getWidth() / 2 - game.fboVirtualWidth / 2,
				(Gdx.graphics.getHeight() / 2 - game.fboVirtualHeight / 2), game.fboVirtualWidth / game.PPM,
				game.fboVirtualHeight / game.PPM, 0, 0, 1, 1);
		game.spriteBatch.end();

//		fbo1
		game.fbo02.begin();
		clearFbo(0, 0, 0, 0);

		game.spriteBatch.begin();
//		render moving background
		spriteBgMoving01.draw(game.spriteBatch);

//		render sprites
		player.playerSprite.draw(game.spriteBatch);
		game.spriteBatch.end();

		game.fbo02.end();
//		fbo1 end

		game.spriteBatch.begin();
//		render fbo1
		game.spriteBatch.draw(game.fbo02.getColorBufferTexture(),
				Gdx.graphics.getWidth() / 2 - game.fboVirtualWidth / 2,
				(Gdx.graphics.getHeight() / 2 - game.fboVirtualHeight / 2), game.fboVirtualWidth / game.PPM,
				game.fboVirtualHeight / game.PPM, 0, 0, 1, 1);
		game.spriteBatch.end();

		b2dDebugRenderer.render(world, game.spriteBatch.getProjectionMatrix());

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
		Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
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

		b2dDebugRenderer.dispose();
		world.dispose();
	}

}
