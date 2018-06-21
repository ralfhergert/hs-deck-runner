package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.game.model.Card;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.HeroPower;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.StartingHandState;
import de.ralfhergert.hearthstone.game.model.Target;
import de.ralfhergert.hearthstone.game.model.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

		if (state.getTurn() == Turn.DrawStartingHand) {
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
			foundActions.add(new EndTurnAction(playerOrdinal));
			// check for the hero power.
			HeroPower heroPower = player.getHeroPower();
			if (heroPower != null &&
				heroPower.isAvailable() &&
				heroPower.isApplicableTo(state) &&
				player.getAvailableMana() >= heroPower.getManaCost()) {
				if (heroPower.isTargeted()) {
					for (Target target : heroPower.getPossibleTargets(state)) {
						foundActions.add(new PlayTargetedHeroPower(target.getTargetRef()));
					}
				} else {
					foundActions.add(new PlayHeroPower());
				}
			}
			// get possible attack actions.
			final List<Character> possibleTargets = getPossibleAttackTargets(state.getOpposingPlayer(player));
			if (player.canAttack()) {
				for (Character target : possibleTargets) {
					foundActions.add(new CharacterAttacksAction(player.getTargetRef(), target.getTargetRef()));
				}
			}
			for (Minion minion : player.getBattlefield()) {
				if (minion.canAttack()) {
					for (Character target : possibleTargets) {
						foundActions.add(new CharacterAttacksAction(minion.getTargetRef(), target.getTargetRef()));
					}
				}
			}
			for (Card card : player.getHand()) {
				foundActions.addAll(card.getActionDiscovery().createPossibleActions(card, state));
			}
		}
		return foundActions;
	}

	public static List<Character> getPossibleAttackTargets(Player player) {
		final List<Character> targets = new ArrayList<>(player.getBattlefield());
		targets.add(player);
		if (player.hasTauntsOnBoard()) { // if the player has taunts on board the only viable targets are those with taunt.
			return targets.stream().filter(character -> character.hasTaunt()).collect(Collectors.toList());
		} else {
			return targets;
		}
	}
}
