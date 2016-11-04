package abra97.submarine.model;

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
	
}
