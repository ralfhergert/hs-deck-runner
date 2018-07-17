package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Mark of the Wild' is working correctly.
 */
public class MarkOfTheWildTest {

	@Test
	public void testPlayMarkOfTheWild() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(2)
			.addToHand(CardRepository.createByName("Mark of the Wild"))
			.addToBattlefield(((MinionCard)CardRepository.createByName("Acolyte of Pain")).createMinion());
		final Player player2 = new Player()
			.setHitPoints(30);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of actions should be", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		// play the minion card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 0, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one, number of minions", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertTrue("player one, minion one, should have taunt", afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).hasTaunt());
		Assert.assertEquals("player one, minion one, attack", 3, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getAttack());
		Assert.assertEquals("player one, minion one, max health", 5, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getMaxHitPoints());
		Assert.assertEquals("player one, minion one, current health", 5, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getCurrentHitPoints());
		// end the turn and confirm that the buffs are still present.
		HearthstoneGameState finalState = ActionUtil.endTheTurn(afterState);
		Assert.assertTrue("player one, minion one, should have taunt", finalState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).hasTaunt());
		Assert.assertEquals("player one, minion one, attack", 3, finalState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getAttack());
		Assert.assertEquals("player one, minion one, max health", 5, finalState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getMaxHitPoints());
		Assert.assertEquals("player one, minion one, current health", 5, finalState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getCurrentHitPoints());
	}
}
