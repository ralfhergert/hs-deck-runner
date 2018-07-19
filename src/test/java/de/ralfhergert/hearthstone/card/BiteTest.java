package de.ralfhergert.hearthstone.card;

import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.PlayAbilityCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test checks that 'Bite' is working correctly.
 */
public class BiteTest {

	@Test
	public void testPlayBite() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(4)
			.addToHand(CardRepository.createByName("Bite"));
		final Player player2 = new Player()
			.setHitPoints(30);
		HearthstoneGameState state = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		state.setTurn(Turn.Player1Turn);
		state = ActionUtil.playAction(state, action -> action instanceof PlayAbilityCard);
		Assert.assertNotNull("after state should not be null", state);
		Assert.assertEquals("player one should have less mana", 0, state.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, state.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, state.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one, attack", 4, state.getPlayer(PlayerOrdinal.One).getAttack());
		Assert.assertTrue("player one, can attack", state.getPlayer(PlayerOrdinal.One).canAttack());
		Assert.assertEquals("player one, armor", 4, state.getPlayer(PlayerOrdinal.One).getArmor());
		state = ActionUtil.endTheTurn(state);
		Assert.assertEquals("player one, attack", 0, state.getPlayer(PlayerOrdinal.One).getAttack());
		Assert.assertFalse("player one, can not attack", state.getPlayer(PlayerOrdinal.One).canAttack());
		Assert.assertEquals("player one, armor", 4, state.getPlayer(PlayerOrdinal.One).getArmor());
	}
}
