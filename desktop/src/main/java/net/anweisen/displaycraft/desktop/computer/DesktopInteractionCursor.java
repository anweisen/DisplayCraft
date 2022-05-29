package net.anweisen.displaycraft.desktop.computer;

import net.anweisen.displaycraft.api.Cursor;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopInteractionCursor {

  private final Map<Player, Cursor> cursors = new ConcurrentHashMap<>();

  public void update(@Nonnull DesktopInteractionScreen screen, @Nonnull Player player, int maxDistance) {
    Cursor cursor = screen.trace(player, maxDistance);
    if (cursor == null) {
      cursors.remove(player);
      return;
    }

    cursors.put(player, cursor);
  }

  @Nullable
  public Cursor getCursor(@Nonnull Player player) {
    return cursors.get(player);
  }

}
