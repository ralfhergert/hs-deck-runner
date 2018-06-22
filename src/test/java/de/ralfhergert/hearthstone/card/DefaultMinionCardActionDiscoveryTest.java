package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.CharacterAttacksAction;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that {@link DefaultMinionCardActionDiscovery} is working.
 */
public class DefaultMinionCardActionDiscoveryTest {

	@Test
	public void testMinionCardCanNotBePlayedBecauseOfInsufficientMana() {
		final Player player1 = new Player()
			.setAvailableMana(3)
			.addToHand(CardRepository.createById(346)); // this is a "Mogu'shan Warden" which costs 4
		// player two gets a 1/1 taunt minion.
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 0, actions.size());
		Assert.assertEquals("active player should still have one card in hand", 1, startState.getActivePlayer().getHand().size());
	}

	@Test
	public void testMinionCardCanNotBePlayedBecauseBattlefieldIsFull() {
		final Player player1 = new Player()
			.setAvailableMana(5)
			.addToHand(CardRepository.createById(346)) // this is a "Mogu'shan Warden" which costs 4
			.addToBattlefield(new Minion().setPower(1).setHitPoints(1))
			.addToBattlefield(new Minion().setPower(1).setHitPoints(1))
			.addToBattlefield(new Minion().setPower(1).setHitPoints(1))
			.addToBattlefield(new Minion().setPower(1).setHitPoints(1))
			.addToBattlefield(new Minion().setPower(1).setHitPoints(1))
			.addToBattlefield(new Minion().setPower(1).setHitPoints(1))
			.addToBattlefield(new Minion().setPower(1).setHitPoints(1));
		// player two gets a 1/1 taunt minion.
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		actions = ActionUtil.remove(actions, CharacterAttacksAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 0, actions.size());
		Assert.assertEquals("active player should still have one card in hand", 1, startState.getActivePlayer().getHand().size());
	}
}
