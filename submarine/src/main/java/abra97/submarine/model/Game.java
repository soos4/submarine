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
	
	private final int mapX;
	private final int mapY;
	
	private int round;
	private final int roundLength;
	private final int roundMax;
	
	private final int rateLimitedPenalty;
	
	private GameStatus status;
	
	private List<Submarine> submarines = new ArrayList<>();
	private List<Island> islands = new ArrayList<>();

	public Game() {
		super();
		this.gameID = MyJson.gameIDParser(HttpManager.newGame());
		
		String json = HttpManager.getGameInfo(gameID);
		
		JSONTokener tokener = new JSONTokener(json);
		JSONObject root = new JSONObject(tokener);
		
		JSONObject game = root.getJSONObject("game");
		JSONObject mapConfiguration = game.getJSONObject("mapConfiguration");
		this.submarinesPerTeam = mapConfiguration.getInt("submarinesPerTeam");
		
		this.roundLength = mapConfiguration.getInt("roundLength");
		this.roundMax = mapConfiguration.getInt("rounds");
		
		this.rateLimitedPenalty = mapConfiguration.getInt("rateLimitedPenalty");
		
		this.mapX = mapConfiguration.getInt("width");
		this.mapY = mapConfiguration.getInt("height");
	}

	public Long getGameID() {
		return gameID;
	}

	public void update() {
		
	}

	public String toString() {
		return "Game [gameID=" + gameID + ", teamCount=" + teamCount + ", submarinesPerTeam=" + submarinesPerTeam
				+ ", teamScore=" + teamScore + ", botScore=" + botScore + ", mapX=" + mapX + ", mapY=" + mapY
				+ ", round=" + round + ", roundLength=" + roundLength + ", roundMax=" + roundMax
				+ ", rateLimitedPenalty=" + rateLimitedPenalty + ", status=" + status + "]";
	}
}
