package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.card.CardFactory;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.StartingHandState;

/**
 * This action causes the play to keep his starting hand.
 */
public class KeepStartingHandAction implements Action<HearthstoneGameState> {

	private final CardFactory cardFactory = new CardFactory();

	private final PlayerOrdinal playerOrdinal;

	public KeepStartingHandAction(PlayerOrdinal playerOrdinal) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal can not be null");
		}
		this.playerOrdinal = playerOrdinal;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState previousState) {
		final HearthstoneGameState state = new HearthstoneGameState(previousState, this);
		final Player player = state.getPlayers()[playerOrdinal.ordinal()];
		player.setStartingHandState(StartingHandState.Keep);
		if (playerOrdinal == PlayerOrdinal.Two) {
			player.addToHand(cardFactory.createCoin());
		}
		return state;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
