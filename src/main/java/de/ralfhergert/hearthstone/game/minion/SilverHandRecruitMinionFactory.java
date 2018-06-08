package de.ralfhergert.hearthstone.game.minion;

import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.MinionType;

/**
 * This is the effect of the paladin hero power.
 */
public class SilverHandRecruitMinionFactory implements MinionFactory {

	@Override
	public Minion create() {
		return new Minion()
			.setMinionType(MinionType.SilverHandRecruit)
			.setPower(1)
			.setCurrentHitPoints(1)
			.setMaxHitPoints(1);
	}
}
