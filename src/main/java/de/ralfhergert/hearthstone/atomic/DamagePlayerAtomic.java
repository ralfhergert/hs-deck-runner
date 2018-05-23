package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.PlayerTakesDamageEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This atomic will damage a playerOrdinal.
 */
public class DamagePlayerAtomic implements Action<HearthstoneGameState> {

	private final PlayerOrdinal playerOrdinal;
	private final int damage;

	public DamagePlayerAtomic(PlayerOrdinal playerOrdinal, int damage) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal can not be null");
		}
		if (damage < 1) {
			throw new IllegalArgumentException("damage can not be smaller than 1");
		}
		this.playerOrdinal = playerOrdinal;
		this.damage = damage;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		Player player = nextState.getPlayer(playerOrdinal);
		final int hitPointsBefore = player.getHitPoints();
		final int hitPointsAfter = player.takeDamage(damage);
		if (hitPointsBefore > hitPointsAfter) {
			nextState.onEvent(new PlayerTakesDamageEvent(nextState, player, hitPointsBefore));
		}
		// firing the event might have altered the playerOrdinal's hit points, so don't use hitPointsAfter.
		return (player.getHitPoints() < 1)
			? new DestroyPlayerAtomic(playerOrdinal).applyTo(nextState)
			: nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
