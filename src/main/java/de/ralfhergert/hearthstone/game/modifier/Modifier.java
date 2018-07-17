package de.ralfhergert.hearthstone.game.modifier;

/**
 * Interface for generic, undoable modifiers.
 *
 * @param <Type> of object the modifier works on.
 */
public interface Modifier<Type> {

	void modify(Type object);

	void undo(Type object);
}
