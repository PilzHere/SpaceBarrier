package mysko.pilzhere.spacebarrier.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import mysko.pilzhere.spacebarrier.SpaceBarrier;
import mysko.pilzhere.spacebarrier.entities.Player;
import mysko.pilzhere.spacebarrier.entities.PlayfieldWall;

public class GameScreen implements Screen {
	public SpaceBarrier game;
	private PerspectiveCamera camPersp;
	private OrthographicCamera camOrtho;

	ModelBuilder modelBuilder = new ModelBuilder();
	Model model;
	Model model2;

	ModelInstance modelInstFloor01;
	ModelInstance modelInstFloor02;

	int darkerGreen = 0x73c373ff;
	int darkGreen = 0x84d384ff;
	int lightGreen = 0x94e394ff;
	int lighterGreen = 0xa5f3a5ff;

	int darkerBrown = 0xc6a284ff;
	int darkBrown = 0xd6b294ff;
	int lightBrown = 0xe7c3a5ff;
	int lighterBrown = 0xf7d3b5ff;

	Pixmap pix;
	Pixmap pix2;

	Texture tex;
	Texture tex2;

	TextureRegion texRegBg01;
	Sprite spriteBg01;

	Player player;

	Box2DDebugRenderer b2dDebugRenderer;
	public World world;

	public GameScreen(SpaceBarrier game) {
		this.game = game;

		Box2D.init();
		world = new World(Vector2.Zero, true);

		b2dDebugRenderer = new Box2DDebugRenderer();

		player = new Player(this);

		camPersp = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camPersp.position.set(new Vector3(0, 0.5f, 0));
		camPersp.lookAt(new Vector3(0, 0.75f, -1));
		camPersp.near = 0.001f;
		camPersp.far = 100;
		camPersp.update();

		camOrtho = new OrthographicCamera();
		camOrtho = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camOrtho.position.set(new Vector3(0, 0, 0));
		camOrtho.lookAt(new Vector3(0, 0, -1));
		camOrtho.near = 0.001f;
		camOrtho.far = 100;
		camOrtho.update();

		texRegBg01 = new TextureRegion(game.atlas01.findRegion("bg01"));
		spriteBg01 = new Sprite(texRegBg01);
		spriteBg01.setSize(spriteBg01.getWidth() / 16, spriteBg01.getHeight() / 16);
		spriteBg01.setPosition(((Gdx.graphics.getWidth() / 2) / 16) - (spriteBg01.getWidth() / 2) , 222f / 16);

		int pixX = 512;
		int pixY = 1024;

		pix = new Pixmap(pixX, pixY, Format.RGB888);

//		Paint pix
		for (int i = 0; i < pixX; i++) {
			for (int j = 0; j < pixY; j++) {
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

		tex = new Texture(pix);
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		pix2 = new Pixmap(pixX, pixY, Format.RGB888);

//		Paint pix2
		for (int i = 0; i < pixX; i++) {
			for (int j = 0; j < pixY; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0)
						pix2.drawPixel(i, j, darkerBrown);
					else
						pix2.drawPixel(i, j, lightBrown);
				} else {
					if (j % 2 == 0)
						pix2.drawPixel(i, j, darkBrown);
					else
						pix2.drawPixel(i, j, lighterBrown);
				}
			}
		}

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
	
	private float spriteBgOffset = 0.025f; // 0.025f

	private void handleInput(float delta) {
//		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//			camPersp.position.y += floorSpeedY / 3 * delta;
//		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//			camPersp.position.y -= floorSpeedY / 3 * delta;
//		}

		modelInstFloor01.transform.translate(new Vector3(0, 0, floorSpeedY * delta));
		modelInstFloor02.transform.translate(new Vector3(0, 0, floorSpeedY * delta));

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//			modelInstFloor01.transform.translate(new Vector3(0, 0, floorSpeedY * delta));
//			modelInstFloor02.transform.translate(new Vector3(0, 0, floorSpeedY * delta));

			
			
			if (player.body.getPosition().y <= playerMaxY) {
				player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, playerSpeedY));
				camPersp.position.y += floorSpeedY / 3 * delta;
				
				camPersp.direction.y = camPersp.direction.y - 0.4f * delta;
				
				spriteBgOffset += 10 * delta;
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
				
				spriteBgOffset -= 10 * delta;
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

				spriteBg01.setPosition(spriteBg01.getX() + bgSpeedX * delta, spriteBg01.getY());
			} else {
				player.body.setLinearVelocity(new Vector2(0, player.body.getLinearVelocity().y));
//				player.body.getPosition().set(new Vector2(playerMinX, player.body.getPosition().y));
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (player.body.getPosition().x < playerMaxX) { // 1180
				player.body.setLinearVelocity(new Vector2(playerSpeedX, player.body.getLinearVelocity().y));

				modelInstFloor01.transform.translate(new Vector3(-floorSpeedX * delta, 0, 0));
				modelInstFloor02.transform.translate(new Vector3(-floorSpeedX * delta, 0, 0));

				spriteBg01.setPosition(spriteBg01.getX() - bgSpeedX * delta, spriteBg01.getY());
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
		
		System.out.println(spriteBg01.getY());

//		System.out.println(Gdx.app.getJavaHeap() / (1024L * 1024L) + " MiB");
	}

	private void tick(float delta) {
		limitCameraPosY(0.5f, 2.75f);
		limitCameraDirY(-0.2f, 0.24253564f);
		loopFloor(100);
		keepFloorInView(14);

//		max 13.875f
//		min 13.3125f
		if (spriteBg01.getY() > 13.875f) {
			spriteBg01.setPosition(spriteBg01.getX(), 13.875f);
		} else if (spriteBg01.getY() < 13.3125f) {
			spriteBg01.setPosition(spriteBg01.getX(), 13.3125f);
		}
		
		player.tick(delta);
		
//		spriteBg01.setPosition(spriteBg01.getX(), ((225/16)  - (camPersp.position.y / 4)));
		spriteBg01.setPosition(spriteBg01.getX(), ((350/16)  - (camPersp.direction.y * 30)));
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

		camPersp.update();
		camOrtho.update();

		game.fbo.begin();
		clearFbo(0.5f, 0.5f, 0.5f, 1);

		game.modelBatch.begin(camPersp);

		game.modelBatch.render(modelInstFloor01);
		game.modelBatch.render(modelInstFloor02);

		game.modelBatch.end();

		game.fbo.end();

		game.spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth() / 16,
				Gdx.graphics.getHeight() / 16);
		game.spriteBatch.begin();

		game.spriteBatch.draw(game.fbo.getColorBufferTexture(), Gdx.graphics.getWidth() / 2 - game.fboVirtualWidth / 2,
				(Gdx.graphics.getHeight() / 2 - game.fboVirtualHeight / 2), game.fboVirtualWidth / 16,
				game.fboVirtualHeight / 16, 0, 0, 1, 1);

		spriteBg01.draw(game.spriteBatch);

		player.playerSprite.draw(game.spriteBatch);

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

	@Override
	public void resize(int width, int height) {
		camPersp.update();
		camOrtho.update();
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
