package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.MinionChangedOwnerEvent;
import de.ralfhergert.hearthstone.event.MinionChangesOwnerEvent;
import de.ralfhergert.hearthstone.event.MinionEntersBattlefieldEvent;
import de.ralfhergert.hearthstone.event.MinionLeavesBattlefieldEvent;
import de.ralfhergert.hearthstone.game.effect.modifier.EffectEventListener;
import de.ralfhergert.hearthstone.game.effect.modifier.NoEffectEventListener;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.modifier.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This effect applies the given modifiers onto any character matching the targetFinder.
 */
public class ModifyOthersEffect implements GeneralEffect, GameEventListener<HearthstoneGameState> {

	private final List<Modifier<Character>> modifiers = new ArrayList<>();
	private final EffectEventListener<ModifyOthersEffect> eventListener;

	public ModifyOthersEffect(Modifier<Character>... modifiers) {
		this(new NoEffectEventListener<>(), modifiers);
	}

	public ModifyOthersEffect(EffectEventListener<ModifyOthersEffect> eventListener, Modifier<Character>... modifiers) {
		this.eventListener = eventListener;
		this.modifiers.addAll(Arrays.asList(modifiers));
	}

	/**
	 * Applies the buff.
	 */
	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		final Character effectOwner = state.getEffectOwner(this);
		final Player player = state.getOwner(this);
		for (Character character : player.getBattlefield()) {
			if (!character.getTargetRef().equals(effectOwner.getTargetRef())) {
				modifiers.forEach(modifier -> modifier.modify(character));
			}
		}
		return state;
	}

	/**
	 * Removes the buff.
	 */
	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		final Character effectOwner = state.getEffectOwner(this);
		final Player player = state.getOwner(this);
		for (Character character : player.getBattlefield()) {
			if (!character.getTargetRef().equals(effectOwner.getTargetRef())) {
				modifiers.forEach(modifier -> modifier.undo(character));
			}
		}
		return state;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		HearthstoneGameState nextState = state;
		if (event instanceof MinionEntersBattlefieldEvent) {
			MinionEntersBattlefieldEvent minionEntersBattlefieldEvent = (MinionEntersBattlefieldEvent)event;
			if (nextState.getEffectOwner(this).getTargetRef().getId() == minionEntersBattlefieldEvent.getMinionRef().getId()) {
				nextState = applyTo(nextState);
			} else {
				final Player owner = nextState.getOwner(this);
				final Minion minion = nextState.findTarget(minionEntersBattlefieldEvent.getMinionRef());
				if (owner.getTargetRef().equals(nextState.getOwner(minion).getTargetRef())) {
					modifiers.forEach(modifier -> modifier.modify(minion));
				}
			}
		}
		if (event instanceof MinionLeavesBattlefieldEvent) {
			MinionLeavesBattlefieldEvent minionLeavesBattlefieldEvent = (MinionLeavesBattlefieldEvent)event;
			if (nextState.getEffectOwner(this).getTargetRef().getId() == minionLeavesBattlefieldEvent.getMinionRef().getId()) {
				nextState = unapplyOn(nextState);
			}
		}
		if (event instanceof MinionChangesOwnerEvent) {
			MinionChangesOwnerEvent minionChangesOwnerEvent = (MinionChangesOwnerEvent)event;
			if (nextState.getEffectOwner(this).getTargetRef().getId() == minionChangesOwnerEvent.getMinionRef().getId()) {
				nextState = unapplyOn(nextState);
			} else {
				final Minion minion = nextState.findTarget(minionChangesOwnerEvent.getMinionRef());
				final Player owner = nextState.getOwner(this);
				if (minionChangesOwnerEvent.getOldOwner().equals(owner.getTargetRef())) {
					modifiers.forEach(modifier -> modifier.undo(minion));
				}
			}
		}
		if (event instanceof MinionChangedOwnerEvent) {
			MinionChangedOwnerEvent minionChangedOwnerEvent = (MinionChangedOwnerEvent)event;
			if (nextState.getEffectOwner(this).getTargetRef().getId() == minionChangedOwnerEvent.getMinionRef().getId()) {
				nextState = applyTo(nextState);
			} else {
				final Minion minion = nextState.findTarget(minionChangedOwnerEvent.getMinionRef());
				final Player owner = nextState.getOwner(this);
				if (minionChangedOwnerEvent.getNewOwner().equals(owner.getTargetRef())) {
					modifiers.forEach(modifier -> modifier.modify(minion));
				}
			}
		}
		return eventListener.onEvent(nextState, event, this);
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
