package mysko.pilzhere.spacebarrier;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import mysko.pilzhere.spacebarrier.screens.GameScreen;

public class GameUtils {
	private GameScreen screen;
	
	public GameUtils(GameScreen screen) {
		this.screen = screen;
	}
	
	public Body createBoxBody(BodyType type, boolean sleepable, boolean isTrigger, int bodyWidth, int bodyHeight, float spawnPosX,
			float spawnPosY) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		bodyDef.position.set(spawnPosX, spawnPosY);

		Body body = screen.world.createBody(bodyDef);
		body.setSleepingAllowed(sleepable);

		PolygonShape poly = new PolygonShape();
		poly.setAsBox(bodyWidth, bodyHeight);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = poly;
		fixtureDef.density = 0;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		
		fixtureDef.isSensor = isTrigger;

		@SuppressWarnings("unused")
		Fixture fixture = body.createFixture(fixtureDef);

		poly.dispose();

		return body;
	}
	
	public long getCurrentTime() {
		return System.currentTimeMillis();
	}
}
