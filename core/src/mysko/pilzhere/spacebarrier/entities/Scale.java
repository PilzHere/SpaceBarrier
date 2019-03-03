package mysko.pilzhere.spacebarrier.entities;

public enum Scale {
	TINIEST(-2),
	TINY(-1),
	SMALLEST(0),
	SMALL(1),
	MEDIUM(2),
	LARGE(3),
	LARGEST(4);
	
	private final int value;
	
	private Scale(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}

// bullets should start 2 sizes bigger than enemies. Create 2 smaller sizes for bullets.

// TO INT! enemies use -1 than player/bullet
