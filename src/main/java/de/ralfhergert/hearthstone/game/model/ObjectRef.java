package de.ralfhergert.hearthstone.game.model;

import java.util.Objects;

/**
 * This reference is used to find the instances of the same minion or player across different states.
 */
public class ObjectRef {

	private static long nextReferenceId = 0;

	private final long id;

	public ObjectRef() {
		this(nextReferenceId++);
	}

	public ObjectRef(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof ObjectRef)) {
			return false;
		}
		ObjectRef targetRef = (ObjectRef)o;
		return id == targetRef.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "ObjectRef{" + id + "}";
	}
}
