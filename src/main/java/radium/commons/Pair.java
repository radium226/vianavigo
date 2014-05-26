package radium.commons;


import java.io.Serializable;

import com.google.common.base.Function;
import com.google.common.base.Objects;

public class Pair<I, J> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private I first;
	private J second;
	
	public static class GetFirstFunction<I, J> implements Function<Pair<I, J>, I> {

		@Override
		public I apply(Pair<I, J> pair) {
			I first = pair.getFirst();
			return first;
		}

		
		
	}
	
	public Pair() {
		super();
	}
	
	public Pair(I first, J second) {
		super();
		this.first = first;
		this.second = second;
	}

	public I getFirst() {
		return first;
	}
	
	public J getSecond() {
		return second;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pair))
			return false;
		Pair<?, ?> other = (Pair<?, ?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(Pair.class)
				.add("first", first)
				.add("second", second)
			.toString();
	}
	
	public static <I, J> Pair<I, J> of(I first, J second) {
		return new Pair<I, J>(first, second);
	}
	
}
