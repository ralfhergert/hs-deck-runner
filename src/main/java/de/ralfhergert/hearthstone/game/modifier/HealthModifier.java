package de.ralfhergert.hearthstone.game.modifier;

import de.ralfhergert.hearthstone.game.model.Character;

/**
 * This modifier modifies a character's health.
 */
public class HealthModifier implements Modifier<Character> {

	private final int health;

	public HealthModifier(int health) {
		this.health = health;
	}

	@Override
	public void modify(Character character) {
		character.setCurrentHitPoints(character.getCurrentHitPoints() + health);
		character.setMaxHitPoints(character.getMaxHitPoints() + health);
	}

	@Override
	public void undo(Character character) {
		character.setMaxHitPoints(character.getMaxHitPoints() - health);
		final int decrease = Math.max(0, character.getCurrentHitPoints() - character.getMaxHitPoints());
		character.setCurrentHitPoints(character.getCurrentHitPoints() - decrease);
	}
}
