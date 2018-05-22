package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * This event is send after the draw-starting-hand-phase before player one's turn starts.
 * @param <Type> of character (player or minion)
 */
public class CharacterDestroyedEvent<Type extends Character> extends GameEvent {

	private final Type character;

	public CharacterDestroyedEvent(HearthstoneGameState state, Type character) {
		super(state);
		this.character = character;
	}

	public Type getCharacter() {
		return character;
	}
}
