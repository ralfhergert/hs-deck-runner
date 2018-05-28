package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.atomic.EquipWeaponAtomic;
import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.Weapon;

/**
 * This is the effect of the rogue hero power.
 */
public class RogueHeroPowerEffect implements GeneralEffect {

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Player owner = state.getOwner(this);
		return state.apply(new EquipWeaponAtomic(state.getPlayerOrdinal(owner), new Weapon().setAttack(1).setDurability(2)));
	}
}
