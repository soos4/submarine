package abra97.submarine;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import abra97.submarine.model.Island;
import abra97.submarine.model.Position;

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
		Collection<Island> ret = new ArrayList<>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject tmp = array.getJSONObject(i);
			ret.add(new Island(new Position(tmp.getInt("x"), tmp.getInt("y"))));
		}
		return ret;
	}
	
}
