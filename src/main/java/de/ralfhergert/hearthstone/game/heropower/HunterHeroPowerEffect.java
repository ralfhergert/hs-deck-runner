package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.atomic.DamagePlayerAtomic;
import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This is the effect of the warrior hero power.
 */
public class HunterHeroPowerEffect implements GeneralEffect {

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Player owner = state.getOwner(this);
		return state.apply(new DamagePlayerAtomic(state.getPlayerOrdinal(owner) == PlayerOrdinal.One ? PlayerOrdinal.Two : PlayerOrdinal.One, 2));
	}
}
