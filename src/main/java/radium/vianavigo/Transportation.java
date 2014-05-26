package radium.vianavigo;

import java.util.EnumSet;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class Transportation {

	public static enum Type {
		TRAIN(0x10000), RER(0x01000), METRO(0x00100), TRAMWAY(0x00010), BUS(0x00001), WALK(0x00000), WAIT(0x00000);
		
		private int mode;
		
		Type(int flag) {
			this.mode = flag;
		}
		
		public int getMode() {
			return mode;
		}
		
		public static EnumSet<Type> all() {
			return EnumSet.<Type>allOf(Type.class);
		}
	}
	
	private Type type;
	private Optional<String> name;
	private Optional<Location> direction;
	
	protected Transportation(Transportation.Type type, Optional<String> name, Optional<Location> direction) {
		super();
		
		this.type = type; 
		this.name = name;
		this.direction = direction;
	}
	
	public Transportation(Transportation.Type type, String name, Location direction) {
		this(type, Optional.<String>of(name), Optional.<Location>of(direction));
	}
	
	public Transportation(Transportation.Type type) {
		this(type, Optional.<String>absent(), Optional.<Location>absent());
	}
	
	public Optional<String> getName() {
		return this.name; 
	}
	
	public Transportation.Type getType() {
		return this.type; 
	}
	
	public Optional<Location> getDirection() {
		return direction;
	}
	
	@Override
	public boolean equals(Object object) {
		boolean equality = false;
		if (object instanceof Transportation) {
			Transportation that = (Transportation) object;
			equality = Objects.equal(this.type, that.type) && Objects.equal(this.name, that.name) && Objects.equal(this.direction, that.direction);
		}
		return equality;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.type, this.name, this.direction);
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("type", this.type)
				.add("name", this.name)
				.add("direction", this.direction)
			.toString();
	}
	
}
