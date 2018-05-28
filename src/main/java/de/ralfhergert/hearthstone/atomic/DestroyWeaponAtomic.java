package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.WeaponDestroyedEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This atomic will destroy the player's weapon.
 */
public class DestroyWeaponAtomic implements Action<HearthstoneGameState> {

	private final PlayerOrdinal playerOrdinal;

	public DestroyWeaponAtomic(PlayerOrdinal playerOrdinal) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal can not be null");
		}
		this.playerOrdinal = playerOrdinal;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		final Player player = nextState.getPlayer(playerOrdinal);
		if (player.getWeapon() != null) {
			player.setWeapon(null);
			nextState.onEvent(new WeaponDestroyedEvent(nextState, playerOrdinal));
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
