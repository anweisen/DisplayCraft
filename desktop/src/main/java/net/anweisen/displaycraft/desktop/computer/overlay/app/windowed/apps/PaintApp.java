package net.anweisen.displaycraft.desktop.computer.overlay.app.windowed.apps;

import net.anweisen.displaycraft.DisplayCraft;
import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.image.DrawHelper;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.image.Images;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorDisplay;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorDisplay.DefaultCursors;
import net.anweisen.displaycraft.desktop.computer.overlay.app.windowed.AppWindowHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.map.MapPalette;
import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class PaintApp implements AppWindowHandler, Listener {

  private final Map<Player, Byte> color = new ConcurrentHashMap<>();
  private final Collection<Player> drawing = new ArrayList<>();
  private Image cache;

  public PaintApp() {
    Bukkit.getServer().getPluginManager().registerEvents(this, DisplayCraft.getInstance());
  }

  @Override
  public void render(@Nonnull Image image) {
    // init image
    if (cache == null) {
      cache = image;
      return;
    }

    // rescaled window -> rescale image
    if (image.getWidth() != cache.getWidth() || image.getHeight() != cache.getHeight()) {
      image.drawImagePart(0, 0, 0, 0, Math.min(image.getWidth(), cache.getWidth()), Math.min(image.getHeight(), cache.getHeight()), cache, false);
      cache = image;
      return;
    }

    image.drawImage(0, 0, cache);
  }

  @Override
  public void handleClick(@Nonnull Player player, @Nonnull Cursor cursor, boolean right) {
    if (player.isSneaking() && right) {
      cache = Images.newImage(cache.getWidth(), cache.getHeight());
      return;
    }
    if (right) {
      cache.setCurrentColor(color.getOrDefault(player, MapPalette.LIGHT_GREEN));
      DrawHelper.fillBucket(cache, cache.getPixel(cursor.getAbsoluteX(), cursor.getAbsoluteY()), cursor.getAbsoluteX(), cursor.getAbsoluteY());
      return;
    }

    if (drawing.contains(player)) drawing.remove(player);
    else drawing.add(player);
  }

  @Override
  public void handleMove(@Nonnull Player player, @Nonnull Cursor from, @Nonnull Cursor to) {
    if (drawing.contains(player)) {
      cache.setCurrentColor(color.getOrDefault(player, MapPalette.LIGHT_GREEN));
      cache.drawStroke(from.getAbsoluteX(), from.getAbsoluteY(), to.getAbsoluteX(), to.getAbsoluteY(), 3);
    }
  }

  @Nonnull
  @Override
  public DesktopCursorDisplay getCursorDisplay(@Nonnull Player player, @Nonnull Cursor cursor) {
    return DefaultCursors.getCrosshair();
  }

  @EventHandler
  public void onChat(@Nonnull AsyncPlayerChatEvent event) {
    color.put(event.getPlayer(), MapPalette.matchColor(Color.decode(event.getMessage())));
  }
}
