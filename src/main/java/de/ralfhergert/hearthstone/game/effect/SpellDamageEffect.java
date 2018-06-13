package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * The spell damage effect buffs damage spells.
 */
public class SpellDamageEffect implements Effect {

	private final int spellDamageBuff;

	public SpellDamageEffect(int spellDamageBuff) {
		this.spellDamageBuff = spellDamageBuff;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}

	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		return null;
	}
}
