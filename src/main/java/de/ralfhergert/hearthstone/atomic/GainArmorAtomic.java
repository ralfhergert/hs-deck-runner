package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.PlayerTakesDamageEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This atomic will increase the armor of the player with playerOrdinal.
 */
public class GainArmorAtomic implements Action<HearthstoneGameState> {

	private final PlayerOrdinal playerOrdinal;
	private final int armor;

	public GainArmorAtomic(PlayerOrdinal playerOrdinal, int armor) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal must not be null");
		}
		if (armor < 0) {
			throw new IllegalArgumentException("damage must not be smaller than 0");
		}
		this.playerOrdinal = playerOrdinal;
		this.armor = armor;
	}

	@Override
	public HearthstoneGameState apply(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		Player player = nextState.getPlayer(playerOrdinal);
		player.setArmor(armor + player.getArmor());
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
