package abra97.submarine;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import abra97.submarine.model.Island;

public class MyJson {
	
	public static Long gameIDParser(String json) {
		JSONTokener tokener = new JSONTokener(json);
		JSONObject root = new JSONObject(tokener);
		return root.getLong("id");
	}
	
	public static Collection<Island> islandParser(String json) {
		JSONTokener tokener = new JSONTokener(json);
		JSONObject root = new JSONObject(tokener);
		
		Island.SIZE = root.getJSONObject("game").getJSONObject("mapConfiguration").getInt("islandSize");
		JSONArray array = root.getJSONObject("game").getJSONObject("mapConfiguration").getJSONArray("islandPositions");
		array.optJSONObject(0);
		return null;
	}
	
}
