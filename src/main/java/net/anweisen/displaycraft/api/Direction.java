package net.anweisen.displaycraft.api;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public enum Direction {

	NORTH,
	SOUTH,
	WEST,
	EAST,
	UP,
	DOWN;

	@Nonnull
	@CheckReturnValue
	public Direction reverse() {
		return ordinal() % 2 == 0 ? values()[ordinal() + 1] : values()[ordinal() - 1];
	}

}
