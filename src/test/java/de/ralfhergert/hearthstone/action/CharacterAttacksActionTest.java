package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.game.model.GameOutcome;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.game.model.Turn;
import de.ralfhergert.hearthstone.game.model.Weapon;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that {@link CharacterAttacksAction} is working correctly.
 */
public class CharacterAttacksActionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testCharacterAttacksActionRejectsNullAttacker() {
		new CharacterAttacksAction(null, new TargetRef());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCharacterAttacksActionRejectsNullTarget() {
		new CharacterAttacksAction(new TargetRef(), null);
	}

	@Test
	public void testPlayerAttacksPlayer() {
		// both players get a 1/1 weapon and 1 hitPoint.
		final Player player1 = new Player()
			.setCurrentHitPoints(1)
			.setWeapon(new Weapon().setAttack(1).setDurability(1).setActive(true));
		final Player player2 = new Player()
			.setCurrentHitPoints(1)
			.setWeapon(new Weapon().setAttack(1).setDurability(1).setActive(false));
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", CharacterAttacksAction.class, foundAction.getClass());
		// play the attack.
		HearthstoneGameState afterState = foundAction.applyTo(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertTrue("queuedEvents should be empty", afterState.getQueuedEffects().isEmpty());
		Assert.assertEquals("after state gameOutcome should be", GameOutcome.Player1Wins, afterState.getOutcome());
		Assert.assertNull("player one's weapon should be destroyed", afterState.getPlayer(PlayerOrdinal.One).getWeapon());
	}
}
