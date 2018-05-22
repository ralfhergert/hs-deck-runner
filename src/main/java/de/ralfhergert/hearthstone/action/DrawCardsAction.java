package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.game.model.Card;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This action creates a new game of hearthstone with both given players.
 */
public class DrawCardsAction implements Action<HearthstoneGameState> {

	private final PlayerOrdinal playerOrdinal;
	private final int numberOfCards;

	public DrawCardsAction(PlayerOrdinal playerOrdinal) {
		this(playerOrdinal, 1);
	}

	public DrawCardsAction(PlayerOrdinal playerOrdinal, final int numberOfCards) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal can not be null");
		}
		if (numberOfCards < 1) {
			throw new IllegalArgumentException("number of cards must be greater then 0");
		}
		this.playerOrdinal = playerOrdinal;
		this.numberOfCards = numberOfCards;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState previousState) {
		final HearthstoneGameState state = new HearthstoneGameState(previousState, this);
		final Player player = state.getPlayer(playerOrdinal);
		for (int i = 0; i < numberOfCards; i++) {
			Card card = player.removeTopCardFromLibrary();
			if (card == null) {
				player.setCurrentFatigueDamage(1 + player.getCurrentFatigueDamage());
				if (player.takeDamage(player.getCurrentFatigueDamage()) < 1) {
					// fatigue damage was lethal. end the game.
					state.setPlayerAsLoser(playerOrdinal);
				}
			} else {
				// try to add the card to the hand.
				if (player.getHand().size() < 10) {
					player.addToHand(card);
				}
			}
		}
		return state;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
