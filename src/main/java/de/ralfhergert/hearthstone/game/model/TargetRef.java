package de.ralfhergert.hearthstone.game.model;

import java.util.Objects;

/**
 * This reference is used to find the instances of the same minion or player across different states.
 */
public class TargetRef {

	private static long nextTargetRef = 0;

	private final long id;

	public TargetRef() {
		this.id = nextTargetRef++;
	}

	public long getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TargetRef targetRef = (TargetRef)o;
		return id == targetRef.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "TargetRef{" + id + "}";
	}
}
