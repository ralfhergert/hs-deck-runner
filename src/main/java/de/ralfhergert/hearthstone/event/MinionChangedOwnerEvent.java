package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.MinionRef;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This event is fired after a minion changed owners on the battlefield.
 */
public class MinionChangedOwnerEvent extends GameEvent {

	private final MinionRef minionRef;
	private final TargetRef oldOwner;
	private final TargetRef newOwner;

	public MinionChangedOwnerEvent(MinionRef minionRef, TargetRef oldOwner, TargetRef newOwner) {
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
