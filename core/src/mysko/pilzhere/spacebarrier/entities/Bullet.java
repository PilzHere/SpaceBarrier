package mysko.pilzhere.spacebarrier.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import mysko.pilzhere.spacebarrier.screens.GameScreen;

public class Bullet extends Entity {

	public Scale scale = Scale.LARGEST;

	private TextureRegion texRegBullet32;
	private TextureRegion texRegBullet24;
	private TextureRegion texRegBullet16;
	private TextureRegion texRegBullet12;
	private TextureRegion texRegBullet08;
	private TextureRegion texRegBullet04;
	private TextureRegion texRegBullet02;

	public Sprite bulletSprite;

	public Body body;

	public Bullet(GameScreen screen) {
		super(screen);

		texRegBullet32 = new TextureRegion(screen.game.atlas01.findRegion("shot01"));
		texRegBullet24 = new TextureRegion(screen.game.atlas01.findRegion("shot02"));
		texRegBullet16 = new TextureRegion(screen.game.atlas01.findRegion("shot03"));
		texRegBullet12 = new TextureRegion(screen.game.atlas01.findRegion("shot04"));
		texRegBullet08 = new TextureRegion(screen.game.atlas01.findRegion("shot05"));
		texRegBullet04 = new TextureRegion(screen.game.atlas01.findRegion("shot06"));
		texRegBullet02 = new TextureRegion(screen.game.atlas01.findRegion("shot07"));

		bulletSprite = new Sprite(texRegBullet32);

		bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
				(bulletSprite.getHeight() / screen.game.PPM) * 4);
	}

	public Bullet(GameScreen screen, float posX, float posY, long spawnTime) {
		this(screen);

		if (!screen.player.playerSprite.isFlipX())
			body = screen.gameUtils.createBoxBody(BodyType.DynamicBody, true, true, 32 / screen.game.PPM,
					32 / screen.game.PPM, posX - 1.8f, posY + 3);
		else
			body = screen.gameUtils.createBoxBody(BodyType.DynamicBody, true, true, 32 / screen.game.PPM,
					32 / screen.game.PPM, posX + 1.8f, posY + 3);

		body.getFixtureList().first().setUserData(this);
		body.getFixtureList().first().getFilterData().categoryBits = screen.contactListener.catBullet;
		body.getFixtureList().first().getFilterData().maskBits = screen.contactListener.maskBullet;

		setSpawnTime(spawnTime);
	}

	private long spawnTime;
	private long lifeTime = 1500L;
	private boolean lifeTimeSet = false;
	private int updateSpriteTextureInterval = 214; // 300

	@Override
	public void tick(float delta) {
		super.tick(delta);

		checkForDespawn();

		body.setLinearVelocity((screen.game.fboRealWidth / 16) - body.getPosition().x,
				(screen.game.fboRealHeight / 16) - body.getPosition().y);

		updateSpriteTextureWithTime(screen.gameUtils.getCurrentTime() - lifeTime, updateSpriteTextureInterval);

		spriteFollowBody(bulletSprite, body);

//		System.out.println("bullet Scale: " + scale);

		if (isColliding) {
			// if (((Enemy) object).canHurt) {
			// if (((Enemy) object).scale == scale) {
			if (enemy.enemyScale == scale.getValue() - 2 /*|| enemy.enemyScale == scale.getValue() - 2*/) { // 3, 2
				enemy.damage(1);
				System.out.println("Enemy hit!");
				remove = true;
			}
		}
//	}
	}

	private void checkForDespawn() {
		if (!lifeTimeSet) {
			spawnTime = screen.getCurrentTime;
			lifeTime = spawnTime + lifeTime;
			lifeTimeSet = true;
		} else {
			if (screen.getCurrentTime >= lifeTime) {
				remove = true;
			}
		}
	}

	private void updateSpriteTextureWithTime(long timeLeft, int timeInterval) {
		if (timeLeft >= -timeInterval) {
			if (scale != Scale.TINIEST) {
				bulletSprite = new Sprite(texRegBullet02);
				bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
						(bulletSprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.TINIEST;
			}
		} else if (timeLeft >= -timeInterval * 2) {
			if (scale != Scale.TINY) {
				bulletSprite = new Sprite(texRegBullet04);
				bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
						(bulletSprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.TINY;
			}
		} else if (timeLeft >= -timeInterval * 3) {
			if (scale != Scale.SMALLEST) {
				bulletSprite = new Sprite(texRegBullet08);
				bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
						(bulletSprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.SMALLEST;
			}
		} else if (timeLeft >= -timeInterval * 4) {
			if (scale != Scale.SMALL) {
				bulletSprite = new Sprite(texRegBullet12);
				bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
						(bulletSprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.SMALL;
			}
		} else if (timeLeft >= -timeInterval * 5) {
			if (scale != Scale.MEDIUM) {
				bulletSprite = new Sprite(texRegBullet16);
				bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
						(bulletSprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.MEDIUM;
			}
		} else if (timeLeft >= -timeInterval * 6) {
			if (scale != Scale.LARGE) {
				bulletSprite = new Sprite(texRegBullet24);
				bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
						(bulletSprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.LARGE;
			}
		}
	}

	private void setSpawnTime(long spawnTime) {
		this.spawnTime = spawnTime;
	}

	private void spriteFollowBody(Sprite sprite, Body body) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

	}

	private Enemy enemy;
	private boolean isColliding = false;

	@Override
	public void onCollisionBegin(Object object) {
		super.onCollisionBegin(object);

//		System.out.println(this.getClass().getSimpleName() + " BEGUN COLLISION with " + object.getClass().getSimpleName());

		if (object instanceof Enemy) {
			isColliding = true;
			enemy = (Enemy) object;
		}
	}

	@Override
	public void onCollisionEnd(Object object) {
		super.onCollisionEnd(object);

		if (object == enemy) {
			isColliding = false;
			enemy = null;
		}

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

//		System.out.println("Bullet ended!");
	}

}
