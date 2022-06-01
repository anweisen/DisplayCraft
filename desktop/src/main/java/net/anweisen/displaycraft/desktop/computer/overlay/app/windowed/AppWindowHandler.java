package net.anweisen.displaycraft.desktop.computer.overlay.app.windowed;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorDisplay;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface AppWindowHandler {

  void render(@Nonnull Image image);

  void handleClick(@Nonnull Player player, @Nonnull Cursor cursor, boolean right);

  void handleMove(@Nonnull Player player, @Nonnull Cursor from, @Nonnull Cursor to);

  @Nonnull
  DesktopCursorDisplay getCursorDisplay(@Nonnull Player player, @Nonnull Cursor cursor);

}
