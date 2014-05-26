package radium.commons;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Optional;

public class Either<L, R> {

	private Optional<L> left;
	private Optional<R> right;
	
	private Either(Optional<L> left, Optional<R> right) {
		super();
		
		this.right = right;
		this.left = left;
	}
	
	public static <L, R> Either<L, R> left(L object) {
		Either<L, R> either = new Either<L, R>(Optional.<L>of(object), Optional.<R>absent());
		return either;
	}
	
	public static <L, R> Either<L, R> right(R object) {
		Either<L, R> either = new Either<L, R>(Optional.<L>absent(), Optional.<R>of(object));
		return either;
	}
	
	
	public <T> T transform(Function<L, T> leftFunction, Function<R, T> rightFunction) {
		T result = null; 
		if (isLeft()) {
			result = leftFunction.apply(left.get());
		} else if (isRight()) {
			result = rightFunction.apply(right.get());
		}
		
		if (result == null) {
			throw new NullPointerException();
		}
		
		return result;
	}
	
	public R transformToRight(Function<L, R> function) {
		return transform(function, Functions.<R>identity());
	}
	
	public L transformToLeft(Function<R, L> function) {
		return transform(Functions.<L>identity(), function);
	}
	
	public boolean isLeft() {
		return left.isPresent();
	}
	
	public boolean isRight() {
		return right.isPresent();
	}
	
}
