package de.ralfhergert.hearthstone.game.model;

/**
 * This reference is used to find the instances of the same minion or player across different states.
 */
public class TargetRef extends ObjectRef {

	public TargetRef() {}

	public TargetRef(long id) {
		super(id);
	}
}
