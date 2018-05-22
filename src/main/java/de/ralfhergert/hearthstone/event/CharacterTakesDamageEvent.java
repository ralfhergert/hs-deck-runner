package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * This event is send after the draw-starting-hand-phase before player one's turn starts.
 * @param <Type> of character (player or minion)
 */
public class CharacterTakesDamageEvent<Type extends Character> extends GameEvent {

	private final Type character;
	private final int hitPointsBefore;

	public CharacterTakesDamageEvent(HearthstoneGameState state, Type character, int hitPointsBefore) {
		super(state);
		this.character = character;
		this.hitPointsBefore = hitPointsBefore;
	}

	public Type getCharacter() {
		return character;
	}

	public int getHitPointsBefore() {
		return hitPointsBefore;
	}
}
