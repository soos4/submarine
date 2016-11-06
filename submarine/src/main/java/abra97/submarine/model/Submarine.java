package abra97.submarine.model;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import abra97.submarine.HttpManager;

public class Submarine extends Entity {

	private int hp;
	private int sonarCoolDown;
	private int torpedoCoolDown;
	private int sonarExtended;

	public static int COUNT_PER_TEAM;
	public static int MAX_STEERING_PER_ROUND;
	public static int MAX_ACCELERATION_PER_ROUND;
	public static int MAX_SPEED;

	public Submarine(int id, Position position, Team owner, double velocity, Direction angle, int hp, int sonarCoolDown,
			int torpedoCoolDown, int sonarExtended) {
		super(ObjectType.SUBMARINE, id, position, owner, velocity, angle);
		this.hp = hp;
		this.sonarCoolDown = sonarCoolDown;
		this.torpedoCoolDown = torpedoCoolDown;
		this.sonarExtended = sonarExtended;
	}
	
	public Submarine(int id, Position position, Team owner, double velocity, Direction angle) {
		this(id, position, owner, velocity, angle, 0, 0, 0, 0);
	}

	private Submarine(JSONObject root) {
		super(ObjectType.SUBMARINE, root.getInt("id"),
				new Position(root.getJSONObject("position").getDouble("x"),
						root.getJSONObject("position").getDouble("y")),
				new Team(root.getJSONObject("owner").getString("name")), root.getDouble("velocity"),
				new Direction(root.getDouble("angle")));
		hp = root.getInt("hp");
		sonarCoolDown = root.getInt("sonarCooldown");
		torpedoCoolDown = root.getInt("torpedoCooldown");
		sonarExtended = root.getInt("sonarExtended");
	}

	public static Collection<Submarine> getSubmarines(String json) {
		JSONTokener tokener = new JSONTokener(json);
		JSONObject r = new JSONObject(tokener);
		JSONArray array = r.getJSONArray("submarines");
		Collection<Submarine> ret = new ArrayList<>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject root = array.getJSONObject(i);
			ret.add(new Submarine(root.getInt("id"),
					new Position(root.getJSONObject("position").getDouble("x"),
							root.getJSONObject("position").getDouble("y")),
					new Team(root.getJSONObject("owner").getString("name")), root.getDouble("velocity"),
					new Direction(root.getDouble("angle")), root.getInt("hp"), root.getInt("sonarCooldown"),
					root.getInt("torpedoCooldown"), root.getInt("sonarExtended")));
		}
		return ret;
	}

	public static void initialize(String json) {
		JSONTokener tokener = new JSONTokener(json);
		JSONObject root = new JSONObject(tokener).getJSONObject("game").getJSONObject("mapConfiguration");
		COUNT_PER_TEAM = root.getInt("submarinesPerTeam");
		MAX_ACCELERATION_PER_ROUND = root.getInt("maxAccelerationPerRound");
		MAX_SPEED = root.getInt("maxSpeed");
		MAX_STEERING_PER_ROUND = root.getInt("maxSteeringPerRound");
	}
	
	public boolean move(Long gameId, double speed, double turn) {
		String request = "{ \"speed\" " + speed + "\"turn\" " + turn + " }";
		
		String response = HttpManager.move(gameId, id, request);
		JSONTokener tokener = new JSONTokener(response);
		JSONObject r = new JSONObject(tokener);
		
		if (r.getString("message").equals("OK"))
			return true;
		return false;
	}
	
	public boolean shoot(Long gameId, double angle) {
		String request = "{ \"angle\" " + angle + " }";
		
		String response = HttpManager.shoot(gameId, id, request);
		JSONTokener tokener = new JSONTokener(response);
		JSONObject r = new JSONObject(tokener);
		
		if (r.getString("message").equals("OK"))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "Submarine [hp=" + hp + ", sonarCoolDown=" + sonarCoolDown + ", torpedoCoolDown=" + torpedoCoolDown
				+ ", sonarExtended=" + sonarExtended + ", base=" + super.toString() + "]";
	}

}
