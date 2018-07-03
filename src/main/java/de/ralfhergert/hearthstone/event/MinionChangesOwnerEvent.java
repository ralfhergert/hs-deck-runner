package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.MinionRef;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This event is fired before a minion is about to change owners on the battlefield.
 */
public class MinionChangesOwnerEvent extends GameEvent {

	private final MinionRef minionRef;
	private final TargetRef oldOwner;
	private final TargetRef newOwner;

	public MinionChangesOwnerEvent(MinionRef minionRef, TargetRef oldOwner, TargetRef newOwner) {
		this.minionRef = minionRef;
		this.oldOwner = oldOwner;
		this.newOwner = newOwner;
	}

	public MinionRef getMinionRef() {
		return minionRef;
	}

	public TargetRef getOldOwner() {
		return oldOwner;
	}

	public TargetRef getNewOwner() {
		return newOwner;
	}
}
