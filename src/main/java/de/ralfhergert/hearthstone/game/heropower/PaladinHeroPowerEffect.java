package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.minion.MinionFactory;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.MinionType;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This is the effect of the paladin hero power.
 */
public class PaladinHeroPowerEffect implements GeneralEffect {

	private final MinionFactory silverHandRecruitFactory = new MinionFactory()
		.setManaCost(1)
		.setMinionName("Silver Hand Recruit")
		.setMinionType(MinionType.SilverHandRecruit)
		.setPower(1)
		.setHitPoints(1);

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Player owner = state.getOwner(this); // TODO find by ref
		owner.addToBattlefield(silverHandRecruitFactory.create());
		return state;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		Player owner = state.getOwner(this); // TODO find by ref
		return owner.getBattlefield().size() < 7;
	}
}
