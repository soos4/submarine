package abra97.submarine;

import org.json.JSONObject;
import org.json.JSONTokener;

public class MyJson {
	
	public static Long gameIDParser(String json) {
		JSONTokener tokener = new JSONTokener(json);
		JSONObject root = new JSONObject(tokener);
		return root.getLong("id");
	}
	
}
