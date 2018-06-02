package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.action.DrawCardsAction;
import de.ralfhergert.hearthstone.atomic.DamagePlayerAtomic;
import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This is the effect of the warlock hero power.
 */
public class WarlockHeroPowerEffect implements GeneralEffect {

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		PlayerOrdinal ownerOrdinal = state.getPlayerOrdinal(state.getOwner(this));
		HearthstoneGameState nextState = new DrawCardsAction(ownerOrdinal, 1).applyTo(state);
		return new DamagePlayerAtomic(ownerOrdinal, 2).applyTo(nextState);
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
