package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.minion.SilverHandRecruitMinionFactory;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This is the effect of the paladin hero power.
 */
public class PaladinHeroPowerEffect implements GeneralEffect {

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Player owner = state.getOwner(this); // TODO find by ref
		owner.addToBattlefield(new SilverHandRecruitMinionFactory().create());
		return state;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		Player owner = state.getOwner(this); // TODO find by ref
		return owner.getBattlefield().size() < 7;
	}
}
