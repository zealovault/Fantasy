package base.framework;

public class Block {
	
	private int _blockID;
	private String _blockSprite;
	
	public void SetBlockID(int blockID) {
		_blockID = blockID;
	}
	
	public int GetBlockID() {
		return _blockID;
	}
	
	public void SetBlockSprite(String blockSprite) {
		_blockSprite = blockSprite;
	}
	
	public String GetBlockSprite() {
		return _blockSprite;
	}

}
