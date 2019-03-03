package mysko.pilzhere.spacebarrier.entities;

import mysko.pilzhere.spacebarrier.screens.GameScreen;

public class Entity {
	protected GameScreen screen;
	
	public boolean remove = false;

	public Entity(GameScreen screen) {
		this.screen = screen;
	}

	public void tick(float delta) {

	}

	public void render(float delta) {

	}

	public void onCollisionBegin(Object object) {
		
	}
	
	public void onCollisionEnd(Object object) {
		
	}
	
	public void onPreSolve(Object object) {
		
	}
	
	public void onPostSolve(Object object) {
		
	}
	
	public void remove(float delta) {

	}
}
