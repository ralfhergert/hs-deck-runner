package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.MinionEntersBattlefieldEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.MinionType;

/**
 * This abstract effect is the template for "When you summon a <MinionType>"-effects.
 */
public class WheneverYouSummonAMinionOfType implements GeneralEffect, GameEventListener<HearthstoneGameState> {

	private final MinionType minionType;

	public WheneverYouSummonAMinionOfType(MinionType minionType) {
		this.minionType = minionType;
	}

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
			final Minion minion = state.findTarget(entersBattlefieldEvent.getMinionRef());
			if (state.getPlayerOrdinal(state.getOwner(this)) == state.findOwnerOrdinal(entersBattlefieldEvent.getMinionRef()) &&
				minion.getMinionType() == minionType) {
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
