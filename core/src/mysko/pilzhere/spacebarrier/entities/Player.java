package mysko.pilzhere.spacebarrier.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import mysko.pilzhere.spacebarrier.GameState;
import mysko.pilzhere.spacebarrier.screens.GameScreen;

public class Player extends Entity {

	private TextureRegion texRegPlayerFlying;

	private Animation<TextureRegion> playerRunningAnimation;

	private TextureRegion[] playerRunningAnimFrames;
	private TextureRegion playerRunningCurrentFrame;
	float animStateTime = 0;

	public Sprite playerSprite;

	public Body body;

	public int hp = 1000;

	public float energy = 1000;
	private boolean isRunning = false;

	public Player(GameScreen screen) {
		super(screen);

		texRegPlayerFlying = new TextureRegion(screen.game.atlas01.findRegion("player2"));
		playerSprite = new Sprite(texRegPlayerFlying);

		playerRunningAnimFrames = new TextureRegion[4];

		playerRunningAnimFrames[0] = new TextureRegion(screen.game.atlas01.findRegion("playerRunning01"));
		playerRunningAnimFrames[1] = new TextureRegion(screen.game.atlas01.findRegion("playerRunning02"));
		playerRunningAnimFrames[2] = new TextureRegion(screen.game.atlas01.findRegion("playerRunning03"));
		playerRunningAnimFrames[3] = new TextureRegion(screen.game.atlas01.findRegion("playerRunning04"));
		playerRunningAnimation = new Animation<TextureRegion>(0.075f, playerRunningAnimFrames);

		body = screen.gameUtils.createBoxBody(BodyType.DynamicBody, false, true, 32 / screen.game.PPM,
				32 / screen.game.PPM, (Gdx.graphics.getWidth() / 2) / screen.game.PPM, screen.playerMinY);

		body.getFixtureList().first().setUserData(this);
		body.getFixtureList().first().getFilterData().categoryBits = screen.contactListener.catPlayer;
		body.getFixtureList().first().getFilterData().maskBits = screen.contactListener.maskPlayer;

		playerSprite.setSize((playerSprite.getWidth() / screen.game.PPM) * 4,
				(playerSprite.getHeight() / screen.game.PPM) * 4);
	}

	@Override
	public void tick(float delta) {
		super.tick(delta);

		runIfOnGround(screen.playerMinY);
		chargeEnergy(delta);

		animStateTime += delta;
		playerRunningCurrentFrame = playerRunningAnimation.getKeyFrame(animStateTime, true);

		if (isRunning) {
			playerSprite.setRegion(playerRunningCurrentFrame);
		} else {
			playerSprite.setRegion(texRegPlayerFlying);
			playerSprite.setFlip(screen.flipPlayerX, false);

		}

		if (hp <= 0) {
			hp = 0;
			screen.gameState = GameState.GAME_OVER;
		} else if (hp > 1000) {
			hp = 1000;
		}

		spriteFollowBody(playerSprite, body);
	}

	private void runIfOnGround(float groundY) {
		if (body.getPosition().y <= groundY) {
			isRunning = true;
		} else {
			isRunning = false;
		}
	}

	private void chargeEnergy(float delta) {
		if (isRunning) {
			energy = energy + delta * 200;

//			System.out.println("Energy = " + energy);
		}

		if (energy > 1000) {
			energy = 1000;
		} else if (energy < 0) {
			energy = 0;
		}
	}

	private void spriteFollowBody(Sprite sprite, Body body) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

	}

	public void damage(int damage) {
		hp = hp - damage;
	}

	@Override
	public void onCollisionBegin(Object object) {
		super.onCollisionBegin(object);

//		System.out.println(this.getClass().getSimpleName() + " BEGUN COLLISION with " + object.getClass().getSimpleName());
	}

	@Override
	public void onCollisionEnd(Object object) {
		super.onCollisionEnd(object);

//		System.out.println(this.getClass().getSimpleName() + " ENDED COLLISION with " + object.getClass().getSimpleName());
	}

	@Override
	public void onPreSolve(Object object) {
		super.onPreSolve(object);

//		System.out.println(this.getClass().getSimpleName() + " *PRE SOLVE with " + object.getClass().getSimpleName());
	}

	@Override
	public void onPostSolve(Object object) {
		super.onPostSolve(object);

//		System.out.println(this.getClass().getSimpleName() + " *POST SOLVE with " + object.getClass().getSimpleName());
	}

	@Override
	public void remove(float delta) {
		super.remove(delta);
		
		screen.world.destroyBody(body);
	}
}
