package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionDiscovery;
import de.ralfhergert.hearthstone.action.PlayWeaponCard;
import de.ralfhergert.hearthstone.game.model.WeaponCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This action discovery creates all play actions for an {@link WeaponCard}.
 */
public class DefaultWeaponCardActionDiscovery implements ActionDiscovery<WeaponCard> {

	@Override
	public List<Action<HearthstoneGameState>> createPossibleActions(WeaponCard card, HearthstoneGameState state) {
		final List<Action<HearthstoneGameState>> actions = new ArrayList<>();
		final Player activePlayer = state.getActivePlayer();
		if (activePlayer != null &&
			activePlayer.hasInHand(card.getCardRef()) &&
			activePlayer.getAvailableMana() >= card.getManaCost()) {
			actions.add(new PlayWeaponCard(card.getCardRef()));
		}
		return actions;
	}
}
