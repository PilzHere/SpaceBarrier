package mysko.pilzhere.spacebarrier.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import mysko.pilzhere.spacebarrier.screens.GameScreen;

public class EnemyBullet extends Entity {

	public Scale scale = Scale.SMALLEST;

	private TextureRegion texRegBullet32;
	private TextureRegion texRegBullet24;
	private TextureRegion texRegBullet16;
	private TextureRegion texRegBullet12;
	private TextureRegion texRegBullet08;
	private TextureRegion texRegBullet04;
	private TextureRegion texRegBullet02;

	public Sprite bulletSprite;

	public Body body;

	public EnemyBullet(GameScreen screen) {
		super(screen);

		texRegBullet32 = new TextureRegion(screen.game.atlas01.findRegion("shot01Enemy"));
		texRegBullet24 = new TextureRegion(screen.game.atlas01.findRegion("shot02Enemy"));
		texRegBullet16 = new TextureRegion(screen.game.atlas01.findRegion("shot03Enemy"));
		texRegBullet12 = new TextureRegion(screen.game.atlas01.findRegion("shot04Enemy"));
		texRegBullet08 = new TextureRegion(screen.game.atlas01.findRegion("shot05Enemy"));
		texRegBullet04 = new TextureRegion(screen.game.atlas01.findRegion("shot06Enemy"));
		texRegBullet02 = new TextureRegion(screen.game.atlas01.findRegion("shot07Enemy"));

		bulletSprite = new Sprite(texRegBullet02);

		bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
				(bulletSprite.getHeight() / screen.game.PPM) * 4);
	}

	public EnemyBullet(GameScreen screen, float posX, float posY, long spawnTime) {
		this(screen);

		body = screen.gameUtils.createBoxBody(BodyType.DynamicBody, true, true, 32 / screen.game.PPM,
				32 / screen.game.PPM, posX, posY);

		body.getFixtureList().first().setUserData(this);
		body.getFixtureList().first().getFilterData().categoryBits = screen.contactListener.catEnemyBullet;
		body.getFixtureList().first().getFilterData().maskBits = screen.contactListener.maskEnemyBullet;

		setSpawnTime(spawnTime);
	}

	private long spawnTime;
	private long lifeTime = 1500L;
	private boolean lifeTimeSet = false;
	private int updateSpriteTextureInterval = 300;

	float playerX;
	float playerY;
	boolean targetSet = false;

	float bulletSpeed;
	float bulletSpeedMax = 2.5f;
	float bulletSpeedMin = 1.5f;

	@Override
	public void tick(float delta) {
		super.tick(delta);

		checkForDespawn();

		if (!targetSet) {
			playerX = screen.player.body.getPosition().x - body.getPosition().x;
			playerY = screen.player.body.getPosition().y - body.getPosition().y;
			targetSet = true;
		}

		if (targetSet) {
			bulletSpeed = MathUtils.random(bulletSpeedMin, bulletSpeedMax);
			bulletSpeed = bulletSpeed / screen.level01.waveCounter;

			body.setLinearVelocity(playerX / bulletSpeed, playerY / bulletSpeed);
		}

		updateSpriteTextureWithTime(screen.gameUtils.getCurrentTime() - lifeTime, updateSpriteTextureInterval);

		spriteFollowBody(bulletSprite, body);

		if (isColliding) {
			player.damage(1);
//			System.out.println("player hit!");
		}
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
			if (scale != Scale.LARGEST) {
				bulletSprite = new Sprite(texRegBullet32);
				bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
						(bulletSprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.LARGEST;
			}
		} else if (timeLeft >= -timeInterval * 2) {
			if (scale != Scale.LARGE) {
				bulletSprite = new Sprite(texRegBullet24);
				bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
						(bulletSprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.LARGE;
			}
		} else if (timeLeft >= -timeInterval * 3) {
			if (scale != Scale.MEDIUM) {
				bulletSprite = new Sprite(texRegBullet16);
				bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
						(bulletSprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.MEDIUM;
			}
		} else if (timeLeft >= -timeInterval * 4) {
			if (scale != Scale.SMALL) {
				bulletSprite = new Sprite(texRegBullet12);
				bulletSprite.setSize((bulletSprite.getWidth() / screen.game.PPM) * 4,
						(bulletSprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.SMALL;
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

	private Player player;
	private boolean isColliding = false;

	@Override
	public void onCollisionBegin(Object object) {
		super.onCollisionBegin(object);

//		System.out.println(this.getClass().getSimpleName() + " BEGUN COLLISION with " + object.getClass().getSimpleName());

		if (object instanceof Player) {
			isColliding = true;
			player = (Player) object;
		}
	}

	@Override
	public void onCollisionEnd(Object object) {
		super.onCollisionEnd(object);

		if (object == player) {
			isColliding = false;
			player = null;
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