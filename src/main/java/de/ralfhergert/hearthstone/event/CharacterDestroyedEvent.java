package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.Character;

/**
 * This event is send after the draw-starting-hand-phase before player one's turn starts.
 * @param <Type> of character (player or minion)
 */
public class CharacterDestroyedEvent<Type extends Character> extends GameEvent {

	private final Type character;

	public CharacterDestroyedEvent(Type character) {
		this.character = character;
	}

	public Type getCharacter() {
		return character;
	}
}
