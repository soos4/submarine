package abra97.submarine.model;

import abra97.submarine.HttpManager;
import abra97.submarine.MyJson;

public class Game {

	private final Long gameID;

	public Game() {
		super();
		this.gameID = MyJson.gameIDParser(HttpManager.newGame());
	}

	public Long getGameID() {
		return gameID;
	}
	
}
