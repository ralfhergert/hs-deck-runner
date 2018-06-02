package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.HeroPower;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This action lets the active player play it's hero power.
 */
public class PlayHeroPower implements Action<HearthstoneGameState> {

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		final HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		final Player player = nextState.getActivePlayer();
		final HeroPower heroPower = player.getHeroPower();
		// let the player pay the mana cost.
		player.setAvailableMana(player.getAvailableMana() - heroPower.getManaCost());
		heroPower.setAvailable(false);
		return ((GeneralEffect)heroPower.getEffect()).applyTo(nextState);
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		final Player player = state.getActivePlayer();
		if (player != null) {
			final HeroPower heroPower = player.getHeroPower();
			return heroPower != null &&
				heroPower.isAvailable() &&
				heroPower.isApplicableTo(state) &&
				!heroPower.isTargeted() &&
				player.getAvailableMana() >= heroPower.getManaCost();
		}
		return false;
	}
}
