package net.anweisen.displaycraft.desktop.computer;

import net.anweisen.displaycraft.DisplayCraft;
import net.anweisen.displaycraft.api.Cursor;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopInteractionCursor {

  private final Map<DesktopComputer, ScheduledFuture<?>> tasks = new HashMap<>();
  private final Map<Player, Cursor> cursors = new ConcurrentHashMap<>();
  private final int maxDistance;
  private final long updateDelay;


  public DesktopInteractionCursor(int maxDistance, long updateDelay) {
    this.maxDistance = maxDistance;
    this.updateDelay = updateDelay;
  }

  public void startUpdatesTask(@Nonnull DesktopComputer computer) {
    ScheduledExecutorService service = DisplayCraft.getInstance().getExecutorService();
    ScheduledFuture<?> future = service.scheduleAtFixedRate(() -> {
      for (Player player : computer.getPlayerBridge().getPlayers()) {
        try {
          update(computer, player);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }, updateDelay, updateDelay, TimeUnit.MILLISECONDS);
    tasks.put(computer, future);
  }

  public void update(@Nonnull DesktopComputer computer, @Nonnull Player player) {
    Cursor cursor = computer.getScreen().trace(player, maxDistance);
    if (cursor == null) {
      cursors.remove(player);
      return;
    }

    Cursor from = cursors.get(player);
    cursors.put(player, cursor);

    if (from != null) {
      computer.handleCursorMove(player, from, cursor);
    }
  }

  public boolean hasCursor(@Nonnull Player player) {
    return cursors.containsKey(player);
  }

  public boolean hasCursorPosition(@Nonnull Cursor cursor) {
    return cursors.containsValue(cursor);
  }

  @Nullable
  public Cursor getCursorPosition(@Nonnull Player player) {
    return cursors.get(player);
  }

  @Nonnull
  public Collection<Cursor> getCursorPositions() {
    return cursors.values();
  }

  @Nonnull
  public Set<Map.Entry<Player, Cursor>> getPlayerCursorPositions() {
    return cursors.entrySet();
  }

}
