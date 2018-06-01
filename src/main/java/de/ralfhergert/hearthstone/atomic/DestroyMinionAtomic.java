package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.MinionDestroyedEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This atomic will destroy a minion.
 */
public class DestroyMinionAtomic implements Action<HearthstoneGameState> {

	private final Minion minion;

	public DestroyMinionAtomic(Minion minion) {
		if (minion == null) {
			throw new IllegalArgumentException("minion can not be null");
		}
		this.minion = minion;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		// move the minion from the battlefield into the graveyard.
		Player owner = nextState.getOwner(minion);
		owner.removeFromBattlefield(minion);
		owner.addToGraveyard(minion);
		return nextState.onEvent(new MinionDestroyedEvent(minion));
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
