package net.anweisen.displaycraft.desktop.computer;

import net.anweisen.displaycraft.DisplayCraft;
import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorClickListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopComputer {

  private static final Collection<DesktopComputer> instances = new CopyOnWriteArrayList<>();

  private final DesktopInteractionScreen screen;
  private final DesktopInteractionCursor cursor;

  private final Collection<DesktopCursorClickListener> clickListeners = new CopyOnWriteArrayList<>();

  private ScheduledFuture<?> cursorTask;

  public DesktopComputer(@Nonnull DesktopInteractionScreen screen, @Nonnull DesktopInteractionCursor cursor) {
    this.screen = screen;
    this.cursor = cursor;

    instances.add(this);
  }

  @Nonnull
  public static Collection<DesktopComputer> getInstances() {
    return instances;
  }

  // TODO maybe in cursor class? idk man
  public void scheduleCursorUpdates(float updatesPerSecond, int maxDistance) {
    if (cursorTask != null)
      cursorTask.cancel(true);

    long delay = (long) (1000 / updatesPerSecond);
    cursorTask = DisplayCraft.getInstance().getExecutorService().scheduleAtFixedRate(() -> {
      for (Player player : Bukkit.getOnlinePlayers()) {
        // TODO somehow keep track of what players even have the computer
        cursor.update(screen, player, maxDistance);
      }
    }, delay, delay, TimeUnit.MILLISECONDS);
  }

  // TODO this is actually internal
  public boolean handleClick(@Nonnull Player player, boolean right) {
    Cursor cursor = this.cursor.getCursor(player);
    if (cursor != null) {
      for (DesktopCursorClickListener listener : clickListeners) {
        try {
          listener.handleClick(this, player, cursor, right);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
    return cursor != null;
  }

  public void registerCursorClickListener(@Nonnull DesktopCursorClickListener listener) {
    clickListeners.add(listener);
  }

  @Nonnull
  public DesktopInteractionScreen getScreen() {
    return screen;
  }

  @Nonnull
  public DesktopInteractionCursor getCursor() {
    return cursor;
  }
}
