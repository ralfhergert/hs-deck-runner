package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.CharacterAttacksAction;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayMinionCard;
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
	public void testPlayMogushanWarden() {
		final Player player1 = new Player()
			.setAvailableMana(5)
			.addToHand(CardRepository.createById(346)); // this is a "Mogu'shan Warden" which costs 4
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayMinionCard.class, foundAction.getClass());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.applyTo(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("active player should have less mana", 1, afterState.getActivePlayer().getAvailableMana());
		Assert.assertEquals("active player should have no overload", 0, afterState.getActivePlayer().getCrystalsLockedNextTurn());
		Assert.assertEquals("active player should have no card in hand", 0, afterState.getActivePlayer().getHand().size());
		Assert.assertEquals("active player should one minion on board", 1, afterState.getActivePlayer().getBattlefield().size());
		// confirm that Mogu'shan Warden has summoning sickness.
		List<Action<HearthstoneGameState>> afterActions = new ActionFactory().createAllApplicableActions(afterState);
		Assert.assertNotNull("actions should not be null", afterActions);
		Assert.assertEquals("number of found actions", 1, afterActions.size());
		Assert.assertEquals("found action should by of type", EndTurnAction.class, afterActions.get(0).getClass());
	}

	@Test
	public void testMinionCardCanNotBePlayedBecauseOfInsufficientMana() {
		final Player player1 = new Player()
			.setAvailableMana(3)
			.addToHand(CardRepository.createById(346)); // this is a "Mogu'shan Warden" which costs 4
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

	@Test
	public void testPlayKingKrush() {
		final Player player1 = new Player()
			.setAvailableMana(9)
			.addToHand(CardRepository.createByName("King Krush"));
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayMinionCard.class, foundAction.getClass());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.applyTo(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("active player should have less mana", 0, afterState.getActivePlayer().getAvailableMana());
		Assert.assertEquals("active player should have no overload", 0, afterState.getActivePlayer().getCrystalsLockedNextTurn());
		Assert.assertEquals("active player should have no card in hand", 0, afterState.getActivePlayer().getHand().size());
		Assert.assertEquals("active player should one minion on board", 1, afterState.getActivePlayer().getBattlefield().size());
		// confirm that King Krush can attack immediately.
		List<Action<HearthstoneGameState>> afterActions = new ActionFactory().createAllApplicableActions(afterState);
		afterActions = ActionUtil.remove(afterActions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", afterActions);
		Assert.assertEquals("number of found actions", 1, afterActions.size());
		Assert.assertEquals("found action should by of type", CharacterAttacksAction.class, afterActions.get(0).getClass());
	}
}
