package abra97.submarine.model;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Torpedo {

	public static int RANGE;
	public static int SPEED;
	public static int HIT_PENALTY;
	public static int DESTROY_SCORE;
	public static int HIT_SCORE;
	public static int DAMAGE;
	public static int EXPLOSION_RADIUS;
	
	public static void initialize(String json) {
		JSONTokener tokener = new JSONTokener(json);
		JSONObject root = new JSONObject(tokener).getJSONObject("game").getJSONObject("mapConfiguration");
		DAMAGE = root.getInt("torpedoDamage");
		DESTROY_SCORE = root.getInt("torpedoDestroyScore");
		EXPLOSION_RADIUS = root.getInt("torpedoExplosionRadius");
		HIT_PENALTY = root.getInt("torpedoHitPenalty");
		HIT_SCORE = root.getInt("torpedoHitScore");
		RANGE = root.getInt("torpedoRange");
		SPEED = root.getInt("torpedoSpeed");
	}
	
}
