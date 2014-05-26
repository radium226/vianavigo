package radium.vianavigo;

import java.util.List;

import com.google.common.base.Objects;

public class Itinerary {

	private List<Step> steps;
	
	protected Itinerary(List<Step> steps) {
		super();
		
		this.steps = steps;
	}
	
	public List<Step> getSteps() {
		return steps;
	}
	
	public static Itinerary valueOf(List<Step> steps) {
		return new Itinerary(steps);
	}
	
	public boolean equals(Object object) {
		boolean equality = false;
		if (object instanceof Itinerary) {
			Itinerary that = (Itinerary) object;
			equality = Objects.equal(this.steps, that.steps);
		}
		return equality;
	}
	
	public String toString() {
		return Objects.toStringHelper(this)
				.add("steps", this.steps)
			.toString();
	}
	
	public int hashCode() {
		return Objects.hashCode(this.steps);
	}
	
}
