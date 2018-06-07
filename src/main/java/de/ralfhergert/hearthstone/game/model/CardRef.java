package de.ralfhergert.hearthstone.game.model;

/**
 * This reference is used to find instances of the same card across different states.
 */
public class CardRef extends ObjectRef {

	public CardRef() {}

	public CardRef(long id) {
		super(id);
	}
}
