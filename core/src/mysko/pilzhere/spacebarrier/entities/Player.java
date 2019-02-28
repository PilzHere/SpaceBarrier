package mysko.pilzhere.spacebarrier.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import mysko.pilzhere.spacebarrier.screens.GameScreen;

public class Player extends Entity {

	TextureRegion texRegPlayer;

	public Sprite playerSprite;

	public Body body;

	public Player(GameScreen screen) {
		super(screen);

		texRegPlayer = new TextureRegion(screen.game.atlas01.findRegion("player"));
		playerSprite = new Sprite(texRegPlayer);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set((Gdx.graphics.getWidth() / 2) / screen.game.PPM,
				(Gdx.graphics.getHeight() / 2) / screen.game.PPM);

		body = screen.world.createBody(bodyDef);
		body.setSleepingAllowed(false);

		PolygonShape poly = new PolygonShape();
//		CircleShape circle = new CircleShape();
//		circle.setRadius(60f);
		poly.setAsBox(32 / screen.game.PPM, 32 / screen.game.PPM);

		FixtureDef fixtureDef = new FixtureDef();
//		fixtureDef.shape = circle;
		fixtureDef.shape = poly;
		fixtureDef.density = 0;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;

		Fixture fixture = body.createFixture(fixtureDef);

		poly.dispose();

		playerSprite.setSize((playerSprite.getWidth() / screen.game.PPM) * 4, (playerSprite.getHeight() / screen.game.PPM) * 4);
	}

	@Override
	public void tick(float delta) {
		super.tick(delta);

		playerSprite.setPosition(body.getPosition().x - playerSprite.getWidth() / 2,
				body.getPosition().y - playerSprite.getHeight() / 2);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

	}

	@Override
	public void remove(float delta) {
		super.remove(delta);

	}
}
