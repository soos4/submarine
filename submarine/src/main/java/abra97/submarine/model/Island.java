package abra97.submarine.model;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Island {

	private final Point center;
	public static int SIZE;
	private static Collection<Island> islands;

	public Island(Point center) {
		super();
		this.center = center;
		islands = new ArrayList<>();
	}

	public Point getCenter() {
		return center;
	}

	@Override
	public String toString() {
		return "Island [center=" + center + "]";
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((center == null) ? 0 : center.hashCode());
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
		Island other = (Island) obj;
		if (center == null) {
			if (other.center != null)
				return false;
		} else if (!center.equals(other.center))
			return false;
		return true;
	}
	
	public static void initialize(String json) {
		JSONTokener tokener = new JSONTokener(json);
		JSONObject root = new JSONObject(tokener).getJSONObject("game").getJSONObject("mapConfiguration");
		SIZE = root.getInt("islandSize");
		JSONArray array = root.getJSONArray("islandPositions");
		for (int i = 0; i < array.length(); i++) {
			JSONObject tmp = array.getJSONObject(i);
			islands.add(new Island(new Point(tmp.getDouble("x"), tmp.getDouble("y"))));
		}
	}
	
	public static Collection<Island> getIslands() {
		return islands;
	}

}
