package abra97.submarine.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import abra97.submarine.HttpManager;

public class Submarine extends Entity {

	private final int hp;
	private final int sonarCoolDown;
	private final int torpedoCoolDown;
	private final int sonarExtended;

	public static int COUNT_PER_TEAM;
	public static int MAX_STEERING_PER_ROUND;
	public static int MAX_ACCELERATION_PER_ROUND;
	public static int MAX_SPEED;
	public static int SIZE;

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
		SIZE = root.getInt("submarineSize");
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
		Collection<Island> islands = Island.getIslands();
		return null;
	}

	private double mustAvoidCollision(Collection<Entity> entities, Collection<Island> islands) {
		double chosenAngle = 1000;
		for (Entity entity : entities) {
			if (entity.getType() == ObjectType.TORPEDO) {
				Torpedo t = (Torpedo) entity;
				int dist = Point.getDistanceInRounds(getPosition(), t.getPosition(), Torpedo.SPEED);
				if (dist > Torpedo.RANGE - t.getRoundsMoved())
					continue;
				double angle = t.getAngle().getAngle();
				Point pos = t.getPosition();
				Point newPos = new Point(pos.getX() + (Torpedo.RANGE - t.getRoundsMoved()) * Math.cos(angle),
						pos.getY() + (Torpedo.RANGE - t.getRoundsMoved()) * Math.sin(angle));
				if (Point.getCircleLineIntersectionPoint(pos, newPos, getPosition(), Submarine.SIZE).isEmpty())
					continue;

				if (angle + 90 < getAngle().getAngle() + MAX_STEERING_PER_ROUND
						&& angle + 90 > getAngle().getAngle() - MAX_STEERING_PER_ROUND)
					chosenAngle = angle + 90;
				else if (angle - 90 < getAngle().getAngle() + MAX_STEERING_PER_ROUND
						&& angle - 90 > getAngle().getAngle() - MAX_STEERING_PER_ROUND)
					chosenAngle = angle - 90;
				else {
					double maxSteeringPlus = getAngle().getAngle() + MAX_STEERING_PER_ROUND;
					double maxSteeringMinus = getAngle().getAngle() - MAX_STEERING_PER_ROUND;
					if ((Math.abs(maxSteeringPlus - (angle + 90)) < Math.abs(maxSteeringMinus - (angle + 90))
							&& Math.abs(maxSteeringPlus - (angle + 90)) < Math.abs(maxSteeringMinus - (angle - 90)))
							|| (Math.abs(maxSteeringPlus - (angle - 90)) < Math.abs(maxSteeringMinus - (angle + 90))
									&& Math.abs(maxSteeringPlus - (angle - 90)) < Math
											.abs(maxSteeringMinus - (angle - 90)))) {
						if (maxSteeringPlus > 360)
							maxSteeringPlus -= 360;
						if (!troubleInChoosenAngle(maxSteeringPlus)) {
							chosenAngle = maxSteeringPlus;
							break;
						}
						else {
							if (maxSteeringMinus > 360)
								maxSteeringMinus -= 360;
							chosenAngle = maxSteeringMinus;
							break;
						}
					}
				}
			}
		}
		return chosenAngle;
	}

	private boolean troubleInChoosenAngle(double angle) {
		Point newPos = new Point(getPosition().getX() + getVelocity() * 2 * Math.cos(angle),
				getPosition().getY() + getVelocity() * 2 * Math.sin(angle));
		for (Island island : Island.getIslands()) {
			if (!Point.getCircleLineIntersectionPoint(getPosition(), newPos, island.getCenter(), Island.SIZE).isEmpty())
				return true;
		}
		if (newPos.getX() >= Game.getMapX() || newPos.getX() <= 0 || newPos.getY() >= Game.getMapY() || newPos.getY() <= 0)
			return true;
		return false;
	}

	private Direction getBestTarget(Collection<Entity> entities, Collection<Island> islands) {
		if (torpedoCoolDown > 0 || entities.size() == 0)
			throw new IllegalArgumentException();
		List<Double> angles = new ArrayList<>();
		List<Integer> rounds = new ArrayList<>();
		int round = 0;
		double angle = 0;
		entityFor: for (Entity entity : entities) {
			if (entity.getType() == ObjectType.SUBMARINE && entity.getOwner() != getOwner()) {
				int i;
				for (i = 1; i < Torpedo.RANGE; i++) {
					Point pos = new Point(entity.getPosition().getX() + i * Math.cos(entity.getAngle().getAngle()),
							entity.getPosition().getY() + i * Math.sin(entity.getAngle().getAngle()));
					Point myNextPos = nextPosition();
					double dist = Point.getDistance(myNextPos, pos);
					if (dist <= Torpedo.SPEED * i) {
						angle = Math.acos(Math.abs(pos.getX() - myNextPos.getX()) / dist);
						if (pos.getX() > myNextPos.getX() && pos.getY() < myNextPos.getY())
							angle += 270;
						else if (pos.getX() < myNextPos.getX() && pos.getY() < myNextPos.getY())
							angle += 180;
						else if (pos.getX() < myNextPos.getX() && pos.getY() > myNextPos.getY())
							angle += 90;
						round = i;
						break;
					}
				}
				if (i == 10)
					continue entityFor;
				for (Island island : islands) {
					Point pos = new Point(nextPosition().getX() + round * Math.cos(angle),
							nextPosition().getY() + round * Math.sin(angle));
					if (!Point.getCircleLineIntersectionPoint(pos, nextPosition(), island.getCenter(), Island.SIZE)
							.isEmpty())
						continue entityFor;
				}
				// később esetleg nézni h másik hajónk van e a környéken
				angles.add(angle);
				rounds.add(round);
			}
		}
		if (rounds.isEmpty())
			throw new IllegalArgumentException();
		int best = 0;
		for (Integer i : rounds) {
			if (i < rounds.get(best) && i * Torpedo.SPEED > Torpedo.EXPLOSION_RADIUS)
				best = rounds.indexOf(i);
		}
		return new Direction(angles.get(best));
	}

}
