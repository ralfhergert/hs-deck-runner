package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.MinionRef;

/**
 * This event is fired when a minion is about the leave the battlefield.
 */
public class MinionLeavesBattlefieldEvent extends GameEvent {

	private final MinionRef minionRef;

	public MinionLeavesBattlefieldEvent(MinionRef minionRef) {
		this.minionRef = minionRef;
	}

	public MinionRef getMinionRef() {
		return minionRef;
	}
}
