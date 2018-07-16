package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.effect.IncreaseAttackUntilEndOfTurn;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;

import java.util.Collections;

/**
 * This is the effect of the druid hero power.
 */
public class DruidHeroPowerEffect implements GeneralEffect {

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		final Player owner = state.getOwner(this);
		// increment druid's armor by 1.
		owner.setArmor(1 + owner.getArmor());
		// increment druid's attack by 1 until end of turn.
		return new IncreaseAttackUntilEndOfTurn(state.getPlayerOrdinal(owner), 1, gameState -> Collections.singletonList(owner.getTargetRef()))
			.applyOn(state, owner.getTargetRef());
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
