package de.ralfhergert.hearthstone.game.model;

/**
 * This reference is used to find the instances of minions across different states.
 */
public class MinionRef extends TargetRef {

	public MinionRef() {}

	public MinionRef(long id) {
		super(id);
	}
}
