package net.anweisen.displaycraft.desktop.computer.overlay.app;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.image.size.Dimensions;
import net.anweisen.displaycraft.api.image.ui.Sprite;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface DesktopApp {

  void init(@Nonnull DesktopComputer computer, int index);

  void render(@Nonnull Image screen);

  void handleClick(@Nonnull Player player, @Nonnull Cursor cursor, boolean right);

  void handleMove(@Nonnull Player player, @Nonnull Cursor from, @Nonnull Cursor to);

  @Nonnull
  Dimensions getInteractionDimensions(@Nonnull Player player);

  @Nonnull
  Sprite getCursorSprite(@Nonnull Player player, @Nonnull Cursor cursor);

}
