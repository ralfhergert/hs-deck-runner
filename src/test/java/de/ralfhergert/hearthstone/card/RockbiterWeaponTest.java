package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.CharacterAttacksAction;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Rockbiter Weapon' is working correctly.
 */
public class RockbiterWeaponTest {

	@Test
	public void testPlayMarkOfTheWild() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(2)
			.addToHand(CardRepository.createByName("Rockbiter Weapon"));
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
		Assert.assertEquals("player one, attack", 3, afterState.getPlayer(PlayerOrdinal.One).getAttack());
		Assert.assertTrue("player one, should be able to attack", afterState.getPlayer(PlayerOrdinal.One).canAttack());
		Assert.assertTrue("player should have CharacterAttacksAction as possible next action", new ActionFactory().createAllApplicableActions(afterState)
			.stream().anyMatch(action -> action instanceof CharacterAttacksAction));
		// end the turn and confirm that the buffs are still present.
		HearthstoneGameState finalState = ActionUtil.endTheTurn(afterState);
		Assert.assertEquals("player one, attack", 0, finalState.getPlayer(PlayerOrdinal.One).getAttack());
		Assert.assertFalse("player one, should not be able to attack", finalState.getPlayer(PlayerOrdinal.One).canAttack());
	}
}
