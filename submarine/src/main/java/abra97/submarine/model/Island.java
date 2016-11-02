package abra97.submarine.model;

public class Island {

	private final Position center;
	private final int radius;

	public Island(Position center, int radius) {
		super();
		this.center = center;
		this.radius = radius;
	}

	public Position getCenter() {
		return center;
	}

	public int getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Island [center=" + center + ", radius=" + radius + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((center == null) ? 0 : center.hashCode());
		result = prime * result + radius;
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
		if (radius != other.radius)
			return false;
		return true;
	}

}
