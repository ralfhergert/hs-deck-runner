package de.ralfhergert.hearthstone.game.weapon;

import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.game.model.Weapon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is a generic minion factory.
 */
public class WeaponFactory {

	private String weaponName;
	private int manaCost;
	private int attack;
	private int durability;
	private List<Effect> effects = new ArrayList<>();

	public Weapon create() {
		return new Weapon()
			.setName(weaponName)
			.setAttack(attack)
			.setDurability(durability)
			.addEffects(effects);
	}

	public String getWeaponName() {
		return weaponName;
	}

	public WeaponFactory setWeaponName(String weaponName) {
		this.weaponName = weaponName;
		return this;
	}

	public int getManaCost() {
		return manaCost;
	}

	public WeaponFactory setManaCost(int manaCost) {
		this.manaCost = manaCost;
		return this;
	}

	public int getAttack() {
		return attack;
	}

	public WeaponFactory setAttack(int attack) {
		this.attack = attack;
		return this;
	}

	public int getDurability() {
		return durability;
	}

	public WeaponFactory setDurability(int durability) {
		this.durability = durability;
		return this;
	}

	public List<Effect> getEffects() {
		return effects;
	}

	public WeaponFactory addEffect(Effect effect) {
		effects.add(effect);
		return this;
	}

	public WeaponFactory addEffects(Collection<Effect> effects) {
		this.effects.addAll(effects);
		return this;
	}
}
