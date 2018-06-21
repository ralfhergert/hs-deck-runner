package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionDiscovery;
import de.ralfhergert.hearthstone.action.PlayMinionCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This action discovery creates all play actions for a {@link MinionCard}.
 */
public class DefaultMinionCardActionDiscovery implements ActionDiscovery<MinionCard> {

	@Override
	public List<Action<HearthstoneGameState>> createPossibleActions(MinionCard minionCard, HearthstoneGameState state) {
		final List<Action<HearthstoneGameState>> actions = new ArrayList<>();
		final Player activePlayer = state.getActivePlayer();
		if (activePlayer != null &&
			activePlayer.hasInHand(minionCard.getCardRef()) &&
			activePlayer.getAvailableMana() >= minionCard.getManaCost() &&
			activePlayer.getBattlefield().size() < 7) {
			for (int i = 0; i <= activePlayer.getBattlefield().size(); i++) {
				actions.add(new PlayMinionCard(minionCard.getCardRef(), i));
			}
		}
		return actions;
	}
}
