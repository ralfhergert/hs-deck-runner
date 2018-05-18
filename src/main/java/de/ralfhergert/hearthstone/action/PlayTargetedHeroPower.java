package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.HeroPower;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.Target;

/**
 * This action lets the active player play it's hero power.
 */
public class PlayTargetedHeroPower implements Action<HearthstoneGameState> {

	private final Target target;

	public PlayTargetedHeroPower(Target target) {
		this.target = target;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		final HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		final Player player = nextState.getActivePlayer();
		final HeroPower heroPower = player.getHeroPower();
		// let the player pay the mana cost.
		player.setAvailableMana(player.getAvailableMana() - heroPower.getManaCost());
		heroPower.setAvailable(false);
		return ((TargetedEffect)heroPower.getEffect()).applyOn(nextState, target);
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		final Player player = state.getActivePlayer();
		if (player != null) {
			final HeroPower heroPower = player.getHeroPower();
			return heroPower != null &&
				heroPower.isAvailable() &&
				heroPower.isTargeted() &&
				player.getAvailableMana() >= heroPower.getManaCost();
		}
		return false;
	}
}
