package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayMinionCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import de.ralfhergert.hearthstone.game.model.WeaponCard;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Acidic Swamp Ooze' is working correctly.
 */
public class AcidicSwampOozeTest {

	@Test
	public void testPlayAcidicSwampOozeAgainstFieryWarAxe() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(5)
			.addToHand(CardRepository.createByName("Acidic Swamp Ooze"));
		final Player player2 = new Player()
			.setWeapon(((WeaponCard)CardRepository.createByName("Fiery War Axe")).createWeapon())
			.setHitPoints(30);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		Assert.assertNotNull("player two should have a weapon", startState.getPlayer(PlayerOrdinal.Two).getWeapon());
		Assert.assertEquals("player two, weapon, name", "Fiery War Axe", startState.getPlayer(PlayerOrdinal.Two).getWeapon().getName());
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayMinionCard.class, foundAction.getClass());
		// play the weapon card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 3, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one, number of minions", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertNull("player two should no longer have a weapon", afterState.getPlayer(PlayerOrdinal.Two).getWeapon());
	}
}
