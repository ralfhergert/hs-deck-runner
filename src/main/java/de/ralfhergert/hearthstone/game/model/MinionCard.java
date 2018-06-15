package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.game.minion.MinionFactory;

/**
 * Implementation of a minion card.
 */
public class MinionCard extends Card<MinionCard> {

	private MinionFactory minionFactory;

	public MinionCard() {}

	/**
	 * Copy-constructor.
	 */
	public MinionCard(MinionCard other) {
		super(other);
		minionFactory = other.minionFactory;
	}

	public MinionCard(MinionFactory minionFactory) {
		if (minionFactory != null) {
			setManaCost(minionFactory.getManaCost());
			setName(minionFactory.getMinionName());
			this.minionFactory = minionFactory;
		}
	}

	public MinionFactory getMinionFactory() {
		return minionFactory;
	}

	public MinionCard setMinionFactory(MinionFactory minionFactory) {
		this.minionFactory = minionFactory;
		return this;
	}
}
