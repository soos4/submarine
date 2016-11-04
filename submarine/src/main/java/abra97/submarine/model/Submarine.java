package abra97.submarine.model;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Submarine {

	private final int id;
	private Position position;
	private Team owner;
	private double velocity;
	private Direction angle;
	private int hp;
	private int sonarCoolDown;
	private int torpedoCoolDown;
	private int sonarExtended;

	public static int COUNT_PER_TEAM;
	public static int MAX_STEERING_PER_ROUND;
	public static int MAX_ACCELERATION_PER_ROUND;
	public static int MAX_SPEED;

	private Submarine(JSONObject root) {
		id = root.getInt("id");
		JSONObject pos = root.getJSONObject("position");
		position = new Position(pos.getDouble("x"), pos.getDouble("y"));
		owner = new Team(root.getJSONObject("owner").getString("name"));
		velocity = root.getDouble("velocity");
		angle = new Direction(root.getDouble("angle"));
		hp = root.getInt("hp");
		sonarCoolDown = root.getInt("sonarCooldown");
		torpedoCoolDown = root.getInt("torpedoCooldown");
		sonarExtended = root.getInt("sonarExtended");
	}

	public static Collection<Submarine> getSubmarines(String json) {
		JSONTokener tokener = new JSONTokener(json);
		JSONObject root = new JSONObject(tokener);
		JSONArray array = root.getJSONArray("submarines");
		Collection<Submarine> ret = new ArrayList<>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject tmp = array.getJSONObject(i);
			ret.add(new Submarine(tmp));
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Team getOwner() {
		return owner;
	}

	public void setOwner(Team owner) {
		this.owner = owner;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public Direction getAngle() {
		return angle;
	}

	public void setAngle(Direction angle) {
		this.angle = angle;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getSonarCoolDown() {
		return sonarCoolDown;
	}

	public void setSonarCoolDown(int sonarCoolDown) {
		this.sonarCoolDown = sonarCoolDown;
	}

	public int getTorpedoCoolDown() {
		return torpedoCoolDown;
	}

	public void setTorpedoCoolDown(int torpedoCoolDown) {
		this.torpedoCoolDown = torpedoCoolDown;
	}

	public int getSonarExtended() {
		return sonarExtended;
	}

	public void setSonarExtended(int sonarExtended) {
		this.sonarExtended = sonarExtended;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Submarine other = (Submarine) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Submarine [id=" + id + ", position=" + position + ", owner=" + owner + ", velocity=" + velocity
				+ ", angle=" + angle + ", hp=" + hp + ", sonarCoolDown=" + sonarCoolDown + ", torpedoCoolDown="
				+ torpedoCoolDown + ", sonarExtended=" + sonarExtended + "]";
	}

}
