package abra97.submarine.model;

import java.util.ArrayList;
import java.util.List;

import abra97.submarine.HttpManager;
import abra97.submarine.MyJson;

public class Game {
	
	private final Long gameID;
	
	private int teamCount;
	private final int submarinesPerTeam;
	
	private int teamScore;
	private int botScore;
	
	private int round;
	private final int roundLength;
	private final int roundMax;
	
	private final int rateLimitedPenalty;
	
	private GameStatus status;
	
	private List<Island> islands = new ArrayList<>();

	public Game() {
		super();
		this.gameID = MyJson.gameIDParser(HttpManager.newGame());
		
	}

	public Long getGameID() {
		return gameID;
	}

	public void update() {
		
	}
}
