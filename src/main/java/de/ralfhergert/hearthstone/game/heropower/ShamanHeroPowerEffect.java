package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.atomic.HealCharacterAtomic;
import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.effect.AtTheEndOfYourTurn;
import de.ralfhergert.hearthstone.game.effect.SpellDamageEffect;
import de.ralfhergert.hearthstone.game.minion.MinionFactory;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.MinionType;
import de.ralfhergert.hearthstone.game.model.Player;

import java.util.Arrays;
import java.util.List;

/**
 * This is the effect of the shaman hero power.
 */
public class ShamanHeroPowerEffect implements GeneralEffect {

	private final List<MinionFactory> totemFactories = Arrays.asList(
		new MinionFactory()
			.setManaCost(1)
			.setMinionName("Healing Totem")
			.setMinionType(MinionType.Totem)
			.setPower(0)
			.setHitPoints(2)
			.addEffect(new AtTheEndOfYourTurn() { // heal every friendly minion by 1
				@Override
				public HearthstoneGameState applyTo(HearthstoneGameState state) {
					HearthstoneGameState nextState = state;
					for (Minion minion : state.getOwner(this).getBattlefield()) {
						nextState = nextState.apply(new HealCharacterAtomic(minion.getTargetRef(), 1));
					}
					return nextState;
				}
			}),
		new MinionFactory()
			.setManaCost(1)
			.setMinionName("Searing Totem")
			.setMinionType(MinionType.Totem)
			.setPower(1)
			.setHitPoints(1),
		new MinionFactory()
			.setManaCost(1)
			.setMinionName("Stoneclaw Totem")
			.setMinionType(MinionType.Totem)
			.setPower(0)
			.setHitPoints(2)
			.setHasTaunt(true),
		new MinionFactory()
			.setManaCost(1)
			.setMinionName("Wrath of Air Totem")
			.setMinionType(MinionType.Totem)
			.setPower(0)
			.setHitPoints(2)
			.addEffect(new SpellDamageEffect(1)));

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		Player owner = state.getOwner(this); // TODO find by ref
		// pick one of the available totem factories by random
		MinionFactory factory = totemFactories.get(state.getRandom().nextInt(totemFactories.size()));
		owner.addToBattlefield(factory.create());
		return state;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		Player owner = state.getOwner(this); // TODO find by ref
		return owner.getBattlefield().size() < 7;
	}
}
