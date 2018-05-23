package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.PlayerDestroyedEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This atomic will destroy a player.
 */
public class DestroyPlayerAtomic implements Action<HearthstoneGameState> {

	private final Player player;

	public DestroyPlayerAtomic(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("player can not be null");
		}
		this.player = player;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		nextState.setPlayerAsLoser(nextState.getPlayerOrdinal(player));
		nextState.onEvent(new PlayerDestroyedEvent(nextState, player));
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
