package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.MinionDestroyedEvent;
import de.ralfhergert.hearthstone.event.MinionLeavesBattlefieldEvent;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This atomic will bounce a minion from the battlefield to the hand.
 */
public class BounceMinionToHandAtomic implements Action<HearthstoneGameState> {

	private final TargetRef targetRef;

	public BounceMinionToHandAtomic(TargetRef targetRef) {
		if (targetRef == null) {
			throw new IllegalArgumentException("targetRef can not be null");
		}
		this.targetRef = targetRef;
	}

	@Override
	public HearthstoneGameState apply(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		// move the minion from the battlefield into the graveyard.
		Character character = nextState.findTarget(targetRef);
		if (!(character instanceof Minion)) {
			return nextState;
		}
		Minion minion = (Minion)character;
		nextState = nextState.onEvent(new MinionLeavesBattlefieldEvent(minion.getMinionRef()));
		Player owner = nextState.getOwner(minion);
		if (owner.getHand().size() < 10) {
			owner.removeFromBattlefield(minion);
			owner.addToHand(minion.getCardSupplier());
			return nextState.onEvent(new MinionDestroyedEvent(minion));
		} else { // hand is full - destroy the minion.
			return new DestroyMinionAtomic(minion).apply(nextState);
		}
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
