package base;

import base.utilities.*;
import base.framework.*;

public class Pilfer {
	
	private static Block _block;
	
	private static int _randomBlockID;
	private static int _gameState;

	public static void main(String[] args) {
		_gameState = 1;
		
		while (_gameState == GameState.Running.State()) {
			_block = new Block();
			_randomBlockID = RandomBlock.Block(0, 3);
			
			_block.SetBlockID(Blocks.Select(_randomBlockID).BlockID());
			_block.SetBlockSprite(Blocks.Select(_randomBlockID).BlockSprite());
			
			System.out.println("Block ID: " + _block.GetBlockID() + "\r\n" + "Block Sprite: " + _block.GetBlockSprite());
		}
	}

}
