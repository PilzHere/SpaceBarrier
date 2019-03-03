package mysko.pilzhere.spacebarrier.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import mysko.pilzhere.spacebarrier.screens.GameScreen;

public class Enemy extends Entity {

	private EnemyAI ai = EnemyAI.IDLE;

	public Scale scale = Scale.TINIEST;
	public int enemyScale;

	public int hp = 5;

//	private TextureRegion texRegEnemy32;
//	private TextureRegion texRegEnemy24;

	private TextureRegion texRegEnemy16;
	private TextureRegion texRegEnemy12;
	private TextureRegion texRegEnemy08;
	private TextureRegion texRegEnemy04;
	private TextureRegion texRegEnemy02;

	public Sprite enemySprite;

	public Body body;

	public Enemy(GameScreen screen) {
		super(screen);

//		texRegEnemy32 = new TextureRegion(screen.game.atlas01.findRegion("enemy01"));
//		texRegEnemy24 = new TextureRegion(screen.game.atlas01.findRegion("enemy02"));

		texRegEnemy16 = new TextureRegion(screen.game.atlas01.findRegion("enemy03"));
		texRegEnemy12 = new TextureRegion(screen.game.atlas01.findRegion("enemy04"));
		texRegEnemy08 = new TextureRegion(screen.game.atlas01.findRegion("enemy05"));
		texRegEnemy04 = new TextureRegion(screen.game.atlas01.findRegion("enemy06"));
		texRegEnemy02 = new TextureRegion(screen.game.atlas01.findRegion("enemy07"));

		enemySprite = new Sprite(texRegEnemy02);

		enemySprite.setSize((enemySprite.getWidth() / screen.game.PPM) * 4,
				(enemySprite.getHeight() / screen.game.PPM) * 4);
	}

	public Enemy(GameScreen screen, float posX, float posY) {
		this(screen);

		body = screen.gameUtils.createBoxBody(BodyType.DynamicBody, true, true, 32 / screen.game.PPM,
				32 / screen.game.PPM, posX, posY);

		body.getFixtureList().first().setUserData(this);
		body.getFixtureList().first().getFilterData().categoryBits = screen.contactListener.catEnemy;
		body.getFixtureList().first().getFilterData().maskBits = screen.contactListener.maskEnemy;
	}

	private long spawnTime;
	private long lifeTime = 3000L;
	private boolean lifeTimeSet = false;
	private int updateSpriteTextureInterval = 600;

//	private long timeHurt;
//	private long hurtCoolDown = 50;
//	private long newTime;
//	private boolean newTimeSet = false;
//	
//	public boolean canHurt = true;

	@Override
	public void tick(float delta) {
		super.tick(delta);

		enemyScale = scale.getValue() - 3;

		checkForDespawn();

		if (hp <= 0)
			remove = true;

		aiTick(delta);

		updateSpriteTextureWithTime(screen.gameUtils.getCurrentTime() - lifeTime, updateSpriteTextureInterval);

		spriteFollowBody(enemySprite, body, delta);

//		System.err.println("enemyColliding = " + colliding);

//		if (colliding) {
//			canHurt = true;
//			
//		}
//			if (!newTimeSet) {
//				timeHurt = screen.getCurrentTime;
//				newTime = timeHurt + hurtCoolDown;
//				canHurt = false;
//				newTimeSet = true;
//			} else {
//				if (screen.getCurrentTime >= newTime) {
////					able to take damage
//					canHurt = true;
//					newTimeSet = false;
//				}
//			}
//		} else {
//			canHurt = true;
//			newTimeSet = false;
//		}
	}

	private void checkForDespawn() {
		if (!lifeTimeSet) {
			spawnTime = screen.getCurrentTime;
			lifeTime = spawnTime + lifeTime;
			lifeTimeSet = true;
		}
		/*
		 * else { // We don't die by timer... if (screen.getCurrentTime >= lifeTime) {
		 * remove = true; } }
		 */
	}

//	private float oldX, oldY;
	
	long cdMin = 1000;
	long cdMax = 2500;
	long cd;
	boolean timeSet = false;
	long newTime;
	
	private void aiTick(float delta) {
		switch (ai) {
		case IDLE:
			ai = EnemyAI.MOVE_LEFT;
			break;
		case MOVE_LEFT:
			if (body.getPosition().x > screen.playerMinX) {
				body.setLinearVelocity(new Vector2(-15, body.getLinearVelocity().y));
			} else {
				ai = EnemyAI.MOVE_RIGHT;
			}
			break;
		case MOVE_RIGHT:
			if (body.getPosition().x < screen.playerMaxX) {
				body.setLinearVelocity(new Vector2(15, body.getLinearVelocity().y));
			} else {
				ai = EnemyAI.MOVE_LEFT;
			}
			break;
		case MOVE_CIRCLE:
			break;
		case SHOOT:
			break;
		}
		
		if (!timeSet) {
			cd = MathUtils.random(cdMin, cdMax);
			newTime = screen.getCurrentTime + cd;
			timeSet = true;
		} else {
			if (screen.getCurrentTime >= newTime) {
				screen.enemyBullets.add(new EnemyBullet(screen, body.getPosition().x, body.getPosition().y, screen.getCurrentTime));
				timeSet = false;
			}
		}
	}

	private void updateSpriteTextureWithTime(long timeLeft, int timeInterval) {
		if (timeLeft >= -timeInterval) {
			if (scale != Scale.MEDIUM) {
				enemySprite = new Sprite(texRegEnemy16);
				enemySprite.setSize((enemySprite.getWidth() / screen.game.PPM) * 4,
						(enemySprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.MEDIUM;
			}
		} else if (timeLeft >= -timeInterval * 2) {
			if (scale != Scale.SMALL) {
				enemySprite = new Sprite(texRegEnemy12);
				enemySprite.setSize((enemySprite.getWidth() / screen.game.PPM) * 4,
						(enemySprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.SMALL;
			}

		} else if (timeLeft >= -timeInterval * 3) {
			if (scale != Scale.SMALLEST) {
				enemySprite = new Sprite(texRegEnemy08);
				enemySprite.setSize((enemySprite.getWidth() / screen.game.PPM) * 4,
						(enemySprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.SMALLEST;
			}
		} else if (timeLeft >= -timeInterval * 4) {
			if (scale != Scale.TINY) {
				enemySprite = new Sprite(texRegEnemy04);
				enemySprite.setSize((enemySprite.getWidth() / screen.game.PPM) * 4,
						(enemySprite.getHeight() / screen.game.PPM) * 4);

				scale = Scale.TINY;
			}
		}
	}

	private void spriteFollowBody(Sprite sprite, Body body, float delta) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

	}

	private boolean colliding = false;

	@Override
	public void onCollisionBegin(Object object) {
		super.onCollisionBegin(object);

		if (object instanceof Bullet) {
			if (!colliding)
				colliding = true;
		}

//		System.out.println(this.getClass().getSimpleName() + " BEGUN COLLISION with " + object.getClass().getSimpleName());
	}

	@Override
	public void onCollisionEnd(Object object) {
		super.onCollisionEnd(object);

		if (object instanceof Bullet) {
			if (colliding)
				colliding = false;
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

	public void damage(int damage) {
		hp = hp - damage;
	}

	@Override
	public void remove(float delta) {
		super.remove(delta);

		screen.world.destroyBody(body);

//		System.out.println("Enemy killed!");
	}
}
