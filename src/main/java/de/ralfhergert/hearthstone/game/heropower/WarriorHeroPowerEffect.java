package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This is the effect of the warrior hero power.
 */
public class WarriorHeroPowerEffect implements GeneralEffect {

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Player owner = state.getOwner(this);
		owner.setArmor(2 + owner.getArmor());
		return state;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
