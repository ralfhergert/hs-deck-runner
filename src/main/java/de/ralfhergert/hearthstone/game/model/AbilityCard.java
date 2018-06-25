package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.effect.Effect;

/**
 * Implementation of an ability card.
 */
public class AbilityCard extends Card<AbilityCard> {

	private Effect effect;

	public AbilityCard(Effect effect) {
		this.effect = effect;
	}

	/**
	 * Copy-constructor.
	 */
	public AbilityCard(AbilityCard other) {
		super(other);
		effect = other.effect;
	}

	public Effect getEffect() {
		return effect;
	}
}
