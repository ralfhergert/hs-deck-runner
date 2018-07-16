package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionDiscovery;
import de.ralfhergert.hearthstone.action.PlayAbilityCard;
import de.ralfhergert.hearthstone.action.PlayTargetedAbilityCard;
import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.AbilityCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.ArrayList;
import java.util.List;

/**
 * This action discovery creates all play actions for an {@link AbilityCard}.
 */
public class DefaultAbilityCardActionDiscovery implements ActionDiscovery<AbilityCard> {

	@Override
	public List<Action<HearthstoneGameState>> createPossibleActions(AbilityCard card, HearthstoneGameState state) {
		final List<Action<HearthstoneGameState>> actions = new ArrayList<>();
		final Player activePlayer = state.getActivePlayer();
		if (activePlayer != null &&
			activePlayer.hasInHand(card.getCardRef()) &&
			activePlayer.getAvailableMana() >= card.getManaCost()) {
			final Effect effect = card.getEffect();
			if (effect instanceof GeneralEffect) {
				actions.add(new PlayAbilityCard(card.getCardRef()));
			} else if (effect instanceof TargetedEffect) {
				for (TargetRef targetRef : ((TargetedEffect)effect).getPossibleTargets(state)) {
					actions.add(new PlayTargetedAbilityCard(card.getCardRef(), targetRef));
				}
			}
		}
		return actions;
	}
}
