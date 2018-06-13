package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.EndTurnEvent;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * This abstract effect is the template for "At the end of your turn"-effects.
 */
public class AtTheEndOfYourTurn implements GeneralEffect, GameEventListener<HearthstoneGameState> {

	/**
	 * Override this method if required.
	 */
	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		return state;
	}

	/**
	 * Override this method if required.
	 */
	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		return state;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		if (event instanceof EndTurnEvent) {
			EndTurnEvent endTurnEvent = (EndTurnEvent)event;
			// if it is the owner of this effect end-of-turn.
			if (state.getPlayerOrdinal(state.getOwner(this)) == endTurnEvent.getPlayerOrdinal()) {
				return applyTo(state);
			}
		}
		return state;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
