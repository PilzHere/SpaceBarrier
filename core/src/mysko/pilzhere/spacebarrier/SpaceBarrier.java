package mysko.pilzhere.spacebarrier;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

import mysko.pilzhere.spacebarrier.screens.GameScreen;

public class SpaceBarrier extends Game {
	public final AssetManager assMan = new AssetManager();
	private final Settings packerSettings = new Settings();

	public SpriteBatch spriteBatch;
	public FrameBuffer fbo01;
	public FrameBuffer fbo02;
	public ModelBatch modelBatch;

	private int fboRealWidth = 1280 / 2; // 854
	private int fboRealHeight = 720 / 2; // 480
	private int fboScale = 2;
	public int fboVirtualWidth = 1280;
	public int fboVirtualHeight = 720;

	public final int PPM = 16;

	@Override
	public void create() {
		setTexturePackerSettings(packerSettings);
		packTexturesToAtlases(packerSettings);

		loadAssets();

		fbo01 = new FrameBuffer(Format.RGBA8888, fboRealWidth / fboScale, fboRealHeight / fboScale, true);
		fbo01.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		fbo02 = new FrameBuffer(Format.RGBA8888, fboRealWidth / fboScale, fboRealHeight / fboScale, true);
		fbo02.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		spriteBatch = new SpriteBatch();
		modelBatch = new ModelBatch();

		setScreen(new GameScreen(this));
	}

	private void setTexturePackerSettings(Settings settings) {
		settings.outputFormat = "png";
		settings.format = Format.RGBA8888;

		settings.maxWidth = 2048;
		settings.maxHeight = 2048;
		settings.filterMag = TextureFilter.Nearest;
		settings.filterMin = TextureFilter.Nearest;
		settings.paddingX = 0;
		settings.paddingY = 0;
		settings.wrapX = TextureWrap.ClampToEdge;
		settings.wrapY = TextureWrap.ClampToEdge;

		settings.fast = true;
		settings.duplicatePadding = true;
		settings.edgePadding = true;
		settings.pot = true;
		settings.alias = true;
		settings.ignoreBlankImages = true;
		settings.bleed = true;
		settings.square = true;
		settings.useIndexes = true;
		settings.limitMemory = true;
	}

	private void packTexturesToAtlases(Settings settings) {
		if (TexturePacker.isModified("textures/atlas01", "textures/atlas01/atlas", "atlas01", settings)) {
			TexturePacker.process(settings, "textures/atlas01", "textures/atlas01/atlas", "atlas01", null);
		}
	}

	public TextureAtlas atlas01;

	private void loadAssets() {
		loadFonts(assMan);
		loadTextures(assMan);
//		loadShaders(assMan);
		loadModels(assMan);
		loadSounds(assMan);

		assMan.finishLoading();

		atlas01 = assMan.get("textures/atlas01/atlas/atlas01.atlas", TextureAtlas.class);
	}

	private void loadFonts(AssetManager assMan) {

	}

	private void loadTextures(AssetManager assMan) {
		assMan.load("textures/atlas01/atlas/atlas01.atlas", TextureAtlas.class);
	}

//	private void loadShaders(AssetManager assMan) {
//
//	}

	private void loadModels(AssetManager assMan) {

	}

	private void loadSounds(AssetManager assMan) {

	}

	@Override
	public void render() {
//		Clear void
		Gdx.gl.glClearColor(0.25f, 0.45f, 0.55f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();

		spriteBatch.dispose();
		fbo01.dispose();
		fbo02.dispose();

		atlas01.dispose();
	}
}
