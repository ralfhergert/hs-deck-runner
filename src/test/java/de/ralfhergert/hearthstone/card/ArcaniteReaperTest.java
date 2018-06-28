package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayWeaponCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import de.ralfhergert.hearthstone.game.model.WeaponCard;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Arcanite Reaper' is working correctly.
 */
public class ArcaniteReaperTest {

	@Test
	public void testPlayArcaniteReaperOverFieryWarAxe() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(5)
			.setWeapon(((WeaponCard)CardRepository.createByName("Fiery War Axe")).createWeapon())
			.addToHand(CardRepository.createByName("Arcanite Reaper"));
		final Player player2 = new Player()
			.setHitPoints(30);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		Assert.assertNotNull("player one should have a weapon", startState.getPlayer(PlayerOrdinal.One).getWeapon());
		Assert.assertEquals("player one, weapon, name", "Fiery War Axe", startState.getPlayer(PlayerOrdinal.One).getWeapon().getName());
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayWeaponCard.class, foundAction.getClass());
		// play the weapon card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 0, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertNotNull("player one should have a weapon", afterState.getPlayer(PlayerOrdinal.One).getWeapon());
		Assert.assertEquals("player one, weapon, name", "Arcanite Reaper", afterState.getPlayer(PlayerOrdinal.One).getWeapon().getName());
	}
}
