package net.anweisen.displaycraft.desktop.computer;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.multipart.MultipartScreen;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorClickListener;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorMoveListener;
import net.anweisen.displaycraft.desktop.computer.overlay.DesktopOverlayHandler;
import net.anweisen.displaycraft.api.multipart.render.ScreenRenderHandler;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopComputer {

  private static final Collection<DesktopComputer> instances = new CopyOnWriteArrayList<>();

  private final MultipartScreen screen;
  private final DesktopInteractionCursor cursor;
  private final DesktopPlayerBridge playerBridge;
  private final ScreenRenderHandler renderHandler;
  private final DesktopOverlayHandler overlayHandler;

  private final Collection<DesktopCursorClickListener> clickListeners = new CopyOnWriteArrayList<>();
  private final Collection<DesktopCursorMoveListener> moveListeners = new CopyOnWriteArrayList<>();

  public DesktopComputer(@Nonnull MultipartScreen screen, @Nonnull DesktopInteractionCursor cursor) {
    this.screen = screen;
    this.cursor = cursor;

    this.renderHandler = new ScreenRenderHandler(screen);
    this.playerBridge = new DesktopPlayerBridge(this);
    this.overlayHandler = new DesktopOverlayHandler(this);

    this.overlayHandler.registerHandlers();

    instances.add(this);
  }

  @Nonnull
  public static Collection<DesktopComputer> getInstances() {
    return instances;
  }

  public void startTasks() {
    cursor.startUpdatesTask(this);
  }

  public void handleCursorClick(@Nonnull Player player, boolean right) {
    Cursor cursor = this.cursor.getCursorPosition(player);
    if (cursor == null) return;
    for (DesktopCursorClickListener listener : clickListeners) {
      try {
        listener.handleClick(player, cursor, right);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public void handleCursorMove(@Nonnull Player player, @Nonnull Cursor from, @Nonnull Cursor to) {
    for (DesktopCursorMoveListener listener : moveListeners) {
      try {
        listener.handleMove(player, from, to);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public void registerCursorClickListener(@Nonnull DesktopCursorClickListener listener) {
    clickListeners.add(listener);
  }

  public void registerCursorMoveListener(@Nonnull DesktopCursorMoveListener listener) {
    moveListeners.add(listener);
  }

  public void render() {
    renderHandler.render(playerBridge.getPlayers());
  }

  public void createScreen(@Nonnull Player player) {
    playerBridge.createScreen(player);
    renderHandler.renderLast(Collections.singleton(player));
  }

  public void destroyScreen(@Nonnull Player player) {
    playerBridge.destroyScreen(player);
  }

  public void destroy() {
    for (Player player : playerBridge.getPlayers()) {
      destroyScreen(player);
    }
  }

  @Nonnull
  public MultipartScreen getScreen() {
    return screen;
  }

  @Nonnull
  public DesktopInteractionCursor getCursor() {
    return cursor;
  }

  @Nonnull
  public DesktopPlayerBridge getPlayerBridge() {
    return playerBridge;
  }

  @Nonnull
  public ScreenRenderHandler getRenderHandler() {
    return renderHandler;
  }

  @Nonnull
  public DesktopOverlayHandler getOverlayHandler() {
    return overlayHandler;
  }
}
