package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.CharacterAttacksAction;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayWeaponCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Truesilver Champion is working correctly.
 */
public class TruesilverChampionTest {

	@Test
	public void testPlayTruesilverChampionAndAttack() {
		final Player player1 = new Player()
			.setCurrentHitPoints(27)
			.setMaxHitPoints(30)
			.setAvailableMana(4)
			.addToHand(CardRepository.createByName("Truesilver Champion"));
		final Player player2 = new Player()
			.setHitPoints(30);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		Assert.assertNull("player one has no weapon", startState.getPlayer(PlayerOrdinal.One).getWeapon());
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayWeaponCard.class, foundAction.getClass());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 0, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertNotNull("player one has a weapon", afterState.getPlayer(PlayerOrdinal.One).getWeapon());
		Assert.assertEquals("player one, current hit points", 27, afterState.getPlayer(PlayerOrdinal.One).getCurrentHitPoints());
		Assert.assertEquals("player two, current hit points", 30, afterState.getPlayer(PlayerOrdinal.Two).getCurrentHitPoints());
		// attack with the weapon.
		actions = new ActionFactory().createAllApplicableActions(afterState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", CharacterAttacksAction.class, foundAction.getClass());
		HearthstoneGameState finalState = foundAction.apply(afterState);
		Assert.assertNotNull("final state should not be null", finalState);
		Assert.assertEquals("player one, current hit points", 29, finalState.getPlayer(PlayerOrdinal.One).getCurrentHitPoints());
		Assert.assertEquals("player two, current hit points", 26, finalState.getPlayer(PlayerOrdinal.Two).getCurrentHitPoints());
	}
}
