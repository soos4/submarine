package abra97.submarine.model;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Sonar {

	public static int SONAR_RANGE;
	public static int EXTENDED_SONAR_RANGE;
	public static int EXTENDED_SONAR_ROUNDS;
	public static int EXTENDED_SONAR_COOLDOWN;

	public static void initialize(String json) {
		JSONTokener tokener = new JSONTokener(json);
		JSONObject root = new JSONObject(tokener).getJSONObject("game").getJSONObject("mapConfiguration");
		SONAR_RANGE = root.getInt("sonarRange");
		EXTENDED_SONAR_RANGE = root.getInt("extendedSonarRange");
		EXTENDED_SONAR_ROUNDS = root.getInt("extendedSonarRounds");
		EXTENDED_SONAR_COOLDOWN = root.getInt("extendedSonarCooldown");
	}

	public static Collection<Entity> getEntities(String json) {
		JSONTokener tokener = new JSONTokener(json);
		JSONObject root = new JSONObject(tokener);
		JSONArray arr = root.getJSONArray("entities");
		Collection<Entity> entities = new ArrayList<>();
		Entity entity = null;
		for (int i = 0; i < arr.length(); i++) {
			JSONObject ent = arr.getJSONObject(i);
			if (ent.getString("type").equals("Submarine")) {
				entity = new Submarine(ent.getInt("id"),
						new Point(ent.getJSONObject("position").getDouble("x"),
								ent.getJSONObject("position").getDouble("y")),
						new Team(ent.getJSONObject("owner").getString("name")), ent.getDouble("velocity"),
						new Direction(ent.getDouble("angle")));
			} else if (ent.getString("type").equals("Torpedo")) {
				entity = new Torpedo(ent.getInt("id"),
						new Point(ent.getJSONObject("position").getDouble("x"),
								ent.getJSONObject("position").getDouble("y")),
						new Team(ent.getJSONObject("owner").getString("name")), ent.getDouble("velocity"),
						new Direction(ent.getDouble("angle")));
			}
			entities.add(entity);
		}
		return entities;
	}
}
