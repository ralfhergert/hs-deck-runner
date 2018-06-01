package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.atomic.DestroyPlayerAtomic;
import de.ralfhergert.hearthstone.event.EndTurnEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;

/**
 * This action ends the turn of the given player.
 */
public class EndTurnAction implements Action<HearthstoneGameState> {

	private final PlayerOrdinal playerOrdinal;

	public EndTurnAction(PlayerOrdinal playerOrdinal) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal can not be null");
		}
		this.playerOrdinal = playerOrdinal;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState previousState) {
		final HearthstoneGameState state = new HearthstoneGameState(previousState, this)
			.onEvent(new EndTurnEvent(playerOrdinal));
		// check for the turn limit; when one of the players ends his 45th turn, the game is over.
		if (state.getActivePlayer().getNumberOfTurns() >= 45) {
			return state
				.apply(new DestroyPlayerAtomic(PlayerOrdinal.One))
				.apply(new DestroyPlayerAtomic(PlayerOrdinal.Two));
		}
		final Turn nextTurn = playerOrdinal == PlayerOrdinal.One ? Turn.Player2Turn : Turn.Player1Turn;
		state.setTurn(nextTurn);
		return state.apply(new StartTurnAction(nextTurn.getPlayerOrdinal()));
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return state.getTurn().getPlayerOrdinal() == playerOrdinal;
	}
}
