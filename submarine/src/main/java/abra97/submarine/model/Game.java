package abra97.submarine.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;

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
		
		String json = HttpManager.getGameInfo(gameID);
		
		JSONTokener tokener = new JSONTokener(json);
		JSONObject root = new JSONObject(tokener);
		
		this.
	}

	public Long getGameID() {
		return gameID;
	}

	public void update() {
		
	}
}
