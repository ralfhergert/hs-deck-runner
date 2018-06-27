package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.MinionEntersBattlefieldEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * This abstract effect is the template for "After you summon a minion"-effects.
 */
public class AfterYouSummonAMinion implements GeneralEffect, GameEventListener<HearthstoneGameState> {

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
		if (event instanceof MinionEntersBattlefieldEvent) {
			MinionEntersBattlefieldEvent entersBattlefieldEvent = (MinionEntersBattlefieldEvent)event;
			if (state.getPlayerOrdinal(state.getOwner(this)) == state.findOwnerOrdinal(entersBattlefieldEvent.getMinionRef())) {
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
