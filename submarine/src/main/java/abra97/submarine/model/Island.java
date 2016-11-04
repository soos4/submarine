package abra97.submarine.model;

public class Island {

	private final Position center;
	public static int SIZE;

	public Island(Position center) {
		super();
		this.center = center;
	}

	public Position getCenter() {
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

}
