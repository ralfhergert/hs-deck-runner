package de.ralfhergert.hearthstone.runner;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.CreateGameAction;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the entry point into the deck runner.
 */
public class HearthstoneDeckRunner {

	private final ActionFactory actionFactory = new ActionFactory();

	private final List<HearthstoneGameState> finalStates = new ArrayList<>();
	private List<HearthstoneGameState> generation = new ArrayList<>();

	public List<HearthstoneGameState> run(Player player1, Player player2) {
		generation.add(new CreateGameAction(player1, player2).apply(null));

		while (!generation.isEmpty()) {
			final List<HearthstoneGameState> nextGeneration = new ArrayList<>();
			for (HearthstoneGameState gameState : generation) {
				List<Action<HearthstoneGameState>> applicableActions = actionFactory.createAllApplicableActions(gameState);
				for (Action<HearthstoneGameState> action : applicableActions) {
					final HearthstoneGameState nextState = action.apply(gameState);
					if (nextState.getOutcome().isFinal()) {
						finalStates.add(nextState);
					} else {
						nextGeneration.add(action.apply(gameState));
					}
				}
				generation = nextGeneration;
			}
		}

		return finalStates;
	}
}
