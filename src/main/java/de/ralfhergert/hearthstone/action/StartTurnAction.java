package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;

/**
 * This action creates a new game of hearthstone with both given players.
 */
public class StartTurnAction implements Action<HearthstoneGameState> {

	private final PlayerOrdinal playerOrdinal;

	public StartTurnAction(PlayerOrdinal playerOrdinal) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal can not be null");
		}
		this.playerOrdinal = playerOrdinal;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState previousState) {
		final HearthstoneGameState state = new HearthstoneGameState(previousState, this);
		state.setTurn(Turn.Player1Turn);
		final Player player = state.getPlayer(playerOrdinal);
		player.setNumberOfManaCrystals(1 + player.getNumberOfManaCrystals());
		player.setAvailableMana(player.getNumberOfManaCrystals() - player.getCrystalsLockedNextTurn());
		player.setCrystalsLockedNextTurn(0);
		return state.apply(new DrawCardsAction(playerOrdinal));
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
