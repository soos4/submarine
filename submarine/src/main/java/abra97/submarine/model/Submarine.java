package abra97.submarine.model;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Submarine extends Entity {

	private final int hp;
	private final int sonarCoolDown;
	private final int torpedoCoolDown;
	private final int sonarExtended;

	public static int COUNT_PER_TEAM;
	public static int MAX_STEERING_PER_ROUND;
	public static int MAX_ACCELERATION_PER_ROUND;
	public static int MAX_SPEED;

	public Submarine(int id, Point position, Team owner, double velocity, Direction angle, int hp, int sonarCoolDown,
			int torpedoCoolDown, int sonarExtended) {
		super(ObjectType.SUBMARINE, id, position, owner, velocity, angle);
		this.hp = hp;
		this.sonarCoolDown = sonarCoolDown;
		this.torpedoCoolDown = torpedoCoolDown;
		this.sonarExtended = sonarExtended;
	}

	public Submarine(int id, Point position, Team owner, double velocity, Direction angle) {
		this(id, position, owner, velocity, angle, 0, 0, 0, 0);
	}

	private Submarine(JSONObject root) {
		super(ObjectType.SUBMARINE, root.getInt("id"),
				new Point(root.getJSONObject("position").getDouble("x"), root.getJSONObject("position").getDouble("y")),
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
					new Point(root.getJSONObject("position").getDouble("x"),
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

	@Override
	public String toString() {
		return "Submarine [hp=" + hp + ", sonarCoolDown=" + sonarCoolDown + ", torpedoCoolDown=" + torpedoCoolDown
				+ ", sonarExtended=" + sonarExtended + ", base=" + super.toString() + "]";
	}
	
	public int getHp() {
		return hp;
	}

	public int getSonarCoolDown() {
		return sonarCoolDown;
	}

	public int getTorpedoCoolDown() {
		return torpedoCoolDown;
	}

	public int getSonarExtended() {
		return sonarExtended;
	}
	
	public Collection<Action> react(String json) {
		Collection<Entity> entities = Sonar.getEntities(json);
		return null;
	}
	
	private static class AI {
		
		
		
	}

}
