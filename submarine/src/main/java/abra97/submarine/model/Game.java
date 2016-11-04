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
		
		Submarine.initialize(json);
		Island.initialize(json);
		Sonar.initialize(json);
		Torpedo.initialize(json);
	}

	public Long getGameID() {
		return gameID;
	}

	public void update() {
		String gameJSON = HttpManager.getGameInfo(gameID);
		
		//System.out.println(gameJSON);
		
		JSONTokener tokener = new JSONTokener(gameJSON);
		JSONObject root = new JSONObject(tokener);
		
		JSONObject game = root.getJSONObject("game");
		
		this.round = game.getInt("round");
		switch (game.getString("status")) {
		case ("WAITING"):
			this.status = GameStatus.WAITING;
		case ("RUNNING"):
			this.status = GameStatus.RUNNING;
		case ("ENDED"):
			this.status = GameStatus.ENDED;
		}
		
		JSONObject scores = game.getJSONObject("scores");
		
		this.teamScore = scores.getJSONObject("scores").getInt("97esÁbra");
		this.botScore = scores.getJSONObject("scores").getInt("BOT");
		
		JSONObject mapConfiguration = game.getJSONObject("mapConfiguration");
		
		this.teamCount = mapConfiguration.getInt("teamCount");
		
		String submarineJSON = HttpManager.getSubmarines(gameID);
		submarines = (List<Submarine>) Submarine.getSubmarines(submarineJSON);
		
		islands = (List<Island>) Island.getIslands(gameJSON);
	}
	
	public List<Submarine> getSubmarines() {
		return submarines;
	}

	public List<Island> getIslands() {
		return islands;
	}

	public String toString() {
		return "Game [gameID=" + gameID + ", teamCount=" + teamCount + ", submarinesPerTeam=" + submarinesPerTeam
				+ ", teamScore=" + teamScore + ", botScore=" + botScore + ", mapX=" + mapX + ", mapY=" + mapY
				+ ", round=" + round + ", roundLength=" + roundLength + ", roundMax=" + roundMax
				+ ", rateLimitedPenalty=" + rateLimitedPenalty + ", status=" + status + "]";
	}
}
