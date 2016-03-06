package base.framework;

public enum GameState {
	Running(1),
	Paused(2),
	Closed(3);
	
	private final int gameState;
	
	private GameState(int gameState) {
		this.gameState = gameState;
	}
	
	public int State() {
		return gameState;
	}

}
