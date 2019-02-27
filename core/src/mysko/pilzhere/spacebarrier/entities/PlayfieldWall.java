package mysko.pilzhere.spacebarrier.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import mysko.pilzhere.spacebarrier.screens.GameScreen;

public class PlayfieldWall extends Entity {

	public Body body;
	
	public Vector2 wallPos = new Vector2();
	
	public PlayfieldWall(GameScreen screen) {
		super(screen);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(wallPos);

		body = screen.world.createBody(bodyDef);

		PolygonShape polyShape = new PolygonShape();
		polyShape.setAsBox(50, 400);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polyShape;
		fixtureDef.density = 0; 
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;

		Fixture fixture = body.createFixture(fixtureDef);

		polyShape.dispose();
	}
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		
		body.getPosition().set(wallPos);
	}

}
