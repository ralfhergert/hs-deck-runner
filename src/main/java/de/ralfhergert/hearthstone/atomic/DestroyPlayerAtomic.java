package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.PlayerDestroyedEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This atomic will destroy a player.
 */
public class DestroyPlayerAtomic implements Action<HearthstoneGameState> {

	private final PlayerOrdinal playerOrdinal;

	public DestroyPlayerAtomic(PlayerOrdinal playerOrdinal) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal can not be null");
		}
		this.playerOrdinal = playerOrdinal;
	}

	@Override
	public HearthstoneGameState apply(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		nextState.setPlayerAsLoser(playerOrdinal);
		return nextState.onEvent(new PlayerDestroyedEvent(nextState.getPlayer(playerOrdinal)));
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
