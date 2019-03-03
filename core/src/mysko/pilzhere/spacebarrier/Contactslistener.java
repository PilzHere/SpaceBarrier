package mysko.pilzhere.spacebarrier;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import mysko.pilzhere.spacebarrier.entities.Entity;

public class Contactslistener implements ContactListener {

	public final short catPlayer = 0x0001;
	public final short catBullet = 0x0002;
	public final short catEnemy = 0x0003;
	public final short catEnemyBullet = 0x0004;

	public final short maskPlayer = catEnemy;
	public final short maskBullet = catEnemy;
	public final short maskEnemy = catPlayer | catBullet;
	public final short maskEnemyBullet = catPlayer;

	@Override
	public void beginContact(Contact contact) {
		Entity obj01 = (Entity) contact.getFixtureA().getUserData();
		obj01.onCollisionBegin(contact.getFixtureB().getUserData());

		Entity obj02 = (Entity) contact.getFixtureB().getUserData();
		obj02.onCollisionBegin(contact.getFixtureA().getUserData());
	}

	@Override
	public void endContact(Contact contact) {
		Entity obj01 = (Entity) contact.getFixtureA().getUserData();
		obj01.onCollisionEnd(contact.getFixtureB().getUserData());

		Entity obj02 = (Entity) contact.getFixtureB().getUserData();
		obj02.onCollisionEnd(contact.getFixtureA().getUserData());

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Entity obj01 = (Entity) contact.getFixtureA().getUserData();
		obj01.onPreSolve(contact.getFixtureB().getUserData());

		Entity obj02 = (Entity) contact.getFixtureB().getUserData();
		obj02.onPreSolve(contact.getFixtureA().getUserData());

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Entity obj01 = (Entity) contact.getFixtureA().getUserData();
		obj01.onPostSolve(contact.getFixtureB().getUserData());

		Entity obj02 = (Entity) contact.getFixtureB().getUserData();
		obj02.onPostSolve(contact.getFixtureA().getUserData());

	}

}
