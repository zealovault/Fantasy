package base.framework;

public enum Blocks {
	Dirt(1, "\\Pilfer\\BlockSprites\\Dirt.bmp"),
	DirtGrass(2, "\\Pilfer\\BlockSprites\\DirtGrass.bmp"),
	Grass(3, "\\Pilfer\\BlockSprites\\Grass.bmp"),
	Rock(4, "\\Pilfer\\BlockSprites\\Rock.bmp");
	
	private final int blockID;
	private final String blockSprite;
	
	private Blocks(int blockID, String blockSprite) {
		this.blockID = blockID;
		this.blockSprite = blockSprite;
	}
	
	public int BlockID() {
		return blockID;
	}
	
	public String BlockSprite() {
		return blockSprite;
	}
	
	public static Blocks Select(int blockID) {
		return Blocks.values()[blockID];
	}
	
}
