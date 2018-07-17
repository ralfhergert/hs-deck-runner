package de.ralfhergert.hearthstone.game.modifier;

import de.ralfhergert.hearthstone.game.model.Character;

/**
 * This modifier modifies a character's attack.
 */
public class AttackModifier implements Modifier<Character> {

	private final int attack;

	public AttackModifier(int attack) {
		this.attack = attack;
	}

	@Override
	public void modify(Character character) {
		character.setPower(character.getPower() + attack);
	}

	@Override
	public void undo(Character character) {
		character.setPower(character.getPower() - attack);
	}
}
