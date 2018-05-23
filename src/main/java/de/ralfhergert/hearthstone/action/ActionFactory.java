package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.atomic.ExecuteQueuedEffectsAtomic;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.HeroPower;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.StartingHandState;
import de.ralfhergert.hearthstone.game.model.Target;
import de.ralfhergert.hearthstone.game.model.Turn;

import java.util.ArrayList;
import java.util.List;

/**
 * This factory will find all applicable action for a given game state.
 */
public class ActionFactory {

	/**
	 * @param state for which all applicable actions should be found.
	 * @return list of actions
	 */
	public List<Action<HearthstoneGameState>> createAllApplicableActions(HearthstoneGameState state) {
		final List<Action<HearthstoneGameState>> foundActions = new ArrayList<>();

		if (!state.getQueuedEffects().isEmpty()) {
			foundActions.add(new ExecuteQueuedEffectsAtomic());
		} else if (state.getTurn() == Turn.DrawStartingHand) {
			for (PlayerOrdinal playerOrdinal : PlayerOrdinal.values()) {
				if (state.getPlayers()[playerOrdinal.ordinal()].getStartingHandState() == StartingHandState.Undecided) {
					foundActions.add(new KeepStartingHandAction(playerOrdinal));
					foundActions.add(new MulliganStartingHandAction(playerOrdinal));
				}
			}
			if (state.getPlayers()[0].getStartingHandState() != StartingHandState.Undecided &&
				state.getPlayers()[1].getStartingHandState() != StartingHandState.Undecided) {
				foundActions.add(new StartGameAction());
			}
		} else { // it is a player's turn.
			final PlayerOrdinal playerOrdinal = state.getTurn() == Turn.Player1Turn ? PlayerOrdinal.One : PlayerOrdinal.Two;
			final Player player = state.getPlayer(playerOrdinal);
			// check for the hero power.
			HeroPower heroPower = player.getHeroPower();
			if (heroPower != null && heroPower.isAvailable() && player.getAvailableMana() >= heroPower.getManaCost()) {
				if (heroPower.isTargeted()) {
					for (Target target : heroPower.getPossibleTargets(state)) {
						foundActions.add(new PlayTargetedHeroPower(target.getTargetRef()));
					}
				} else {
					foundActions.add(new PlayHeroPower());
				}
			}
		}
		return foundActions;
	}
}
