package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayAbilityCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Bloodlust' is working correctly.
 */
public class BloodlustTest {

	@Test
	public void testPlayBloodlust() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(5)
			.addToHand(CardRepository.createByName("Bloodlust"))
			.addToBattlefield(((MinionCard)CardRepository.createByName("Wisp")).createMinion())
			.addToBattlefield(((MinionCard)CardRepository.createByName("Wisp")).createMinion());
		final Player player2 = new Player()
			.setHitPoints(30);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayAbilityCard.class, foundAction.getClass());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 0, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one, minions on board", 2, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player one, minion one, power", 4, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getAttack());
		Assert.assertEquals("player one, minion two, power", 4, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(1).getAttack());
		// end the turn to confirm the buff is removed.
		actions = new ActionFactory().createAllApplicableActions(afterState);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", EndTurnAction.class, foundAction.getClass());
		HearthstoneGameState finalState = foundAction.apply(afterState);
		Assert.assertNotNull("final state should not be null", finalState);
		Assert.assertEquals("player one, minions on board", 2, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player one, minion one, power", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getAttack());
		Assert.assertEquals("player one, minion two, power", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(1).getAttack());
	}
}
