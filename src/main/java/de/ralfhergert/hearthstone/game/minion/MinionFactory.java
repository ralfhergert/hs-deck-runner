package de.ralfhergert.hearthstone.game.minion;

import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.MinionType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is a generic minion factory.
 */
public class MinionFactory {

	private String minionName;
	private MinionType minionType;
	private int manaCost;
	private int power;
	private int hitPoints;
	private boolean hasTaunt;
	private List<Effect> effects = new ArrayList<>();

	public Minion create() {
		return new Minion()
			.setMinionFactory(this)
			.setName(minionName)
			.setMinionType(minionType)
			.setPower(power)
			.setCurrentHitPoints(hitPoints)
			.setMaxHitPoints(hitPoints)
			.setHasTaunt(hasTaunt)
			.addEffects(effects);
	}

	public String getMinionName() {
		return minionName;
	}

	public MinionFactory setMinionName(String minionName) {
		this.minionName = minionName;
		return this;
	}

	public MinionType getMinionType() {
		return minionType;
	}

	public MinionFactory setMinionType(MinionType minionType) {
		this.minionType = minionType;
		return this;
	}

	public int getManaCost() {
		return manaCost;
	}

	public MinionFactory setManaCost(int manaCost) {
		this.manaCost = manaCost;
		return this;
	}

	public int getPower() {
		return power;
	}

	public MinionFactory setPower(int power) {
		this.power = power;
		return this;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public MinionFactory setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
		return this;
	}

	public boolean isHasTaunt() {
		return hasTaunt;
	}

	public MinionFactory setHasTaunt(boolean hasTaunt) {
		this.hasTaunt = hasTaunt;
		return this;
	}

	public List<Effect> getEffects() {
		return effects;
	}

	public MinionFactory addEffect(Effect effect) {
		effects.add(effect);
		return this;
	}

	public MinionFactory addEffects(Collection<Effect> effects) {
		this.effects.addAll(effects);
		return this;
	}
}
