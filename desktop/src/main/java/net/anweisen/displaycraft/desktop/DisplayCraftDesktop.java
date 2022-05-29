package net.anweisen.displaycraft.desktop;

import net.anweisen.displaycraft.DisplayCraft;
import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.Direction;
import net.anweisen.displaycraft.api.Position;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.image.Images;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import net.anweisen.displaycraft.desktop.computer.DesktopInteractionCursor;
import net.anweisen.displaycraft.desktop.computer.DesktopInteractionScreen;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorDisplay;
import net.anweisen.displaycraft.desktop.listener.PlayerInteractionListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapPalette;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DisplayCraftDesktop extends JavaPlugin {

  @Override
  public void onLoad() {
  }

  @Override
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new PlayerInteractionListener(), this);

    DesktopComputer computer = new DesktopComputer(
      DesktopInteractionScreen.withCenter(Images.RESOLUTION, 5, 3, Position.from(1, 65, 0, Direction.WEST)),
      new DesktopInteractionCursor()
    );
    computer.scheduleCursorUpdates(40, 10);

    Image image = computer.getScreen().newImage();
    image.setColor(MapPalette.LIGHT_GREEN);
    image.fillRect(0, 0, image.getWidth(), image.getHeight());

    computer.registerCursorClickListener((__, player, cursor, right) -> {
      player.sendMessage(cursor + "   right=" + right);
    });

    DisplayCraft.getInstance().getExecutorService().scheduleAtFixedRate(() -> {
      long start = System.currentTimeMillis();
      Image display = image.copy();
      for (Player player : Bukkit.getOnlinePlayers()) {
        Cursor cursor = computer.getCursor().getCursor(player);
//        player.sendMessage(String.valueOf(cursor));
        if (cursor == null) continue;

        try {
          DesktopCursorDisplay cursorDisplay = DesktopCursorDisplay.getDefault();
          display.drawImage(cursor.getAbsoluteX() - cursorDisplay.getHotspotX(), cursor.getAbsoluteY() - cursorDisplay.getHotspotY(), cursorDisplay.getImage());
        } catch (Exception ex) {
        }
      }

      computer.getScreen().render(Bukkit.getOnlinePlayers(), display);
      System.out.println(System.currentTimeMillis() - start + "ms");
    }, 1, 1000 / 20, TimeUnit.MILLISECONDS);

    DisplayCraft.getInstance().getCommand("display-craft").setExecutor((sender, command, label, args) -> {
      Player player = (Player) sender;
      computer.getScreen().create(player);
      computer.getScreen().render(Collections.singletonList(player), image);
      return true;
    });
  }

  @Override
  public void onDisable() {
  }
}
