package net.anweisen.displaycraft.desktop;

import net.anweisen.displaycraft.DisplayCraft;
import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.Direction;
import net.anweisen.displaycraft.api.Position;
import net.anweisen.displaycraft.api.image.Dimensions;
import net.anweisen.displaycraft.api.image.Images;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import net.anweisen.displaycraft.desktop.computer.DesktopInteractionCursor;
import net.anweisen.displaycraft.desktop.computer.DesktopInteractionScreen;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorDisplay;
import net.anweisen.displaycraft.desktop.listener.PlayerInteractionListener;
import org.bukkit.entity.Player;
import org.bukkit.map.MapPalette;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.concurrent.TimeUnit;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DisplayCraftDesktop extends JavaPlugin {

  private DesktopComputer computer;

  @Override
  public void onLoad() {
  }

  @Override
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new PlayerInteractionListener(), this);

    computer = new DesktopComputer(
      DesktopInteractionScreen.withCenter(Images.RESOLUTION, 5, 3, Position.from(1, 65, 0, Direction.WEST)),
      new DesktopInteractionCursor(10, 1000 / 40)
    );
    computer.startTasks();

    computer.getRenderHandler().registerRendererBefore((__, image) -> {
      image.setColor(MapPalette.WHITE);
      image.fillRect(Dimensions.from(image));
    });
    computer.getRenderHandler().registerRendererAfter((__, image) -> {
      for (Cursor cursor : computer.getCursor().getCursorPositions()) {
//        System.out.println(cursor);
        DesktopCursorDisplay.getDefault().draw(image, cursor);
      }
    });
    computer.registerCursorClickListener(((__, player, cursor, right) -> {
      player.sendMessage(cursor.toString() + "   " + right);
    }));
    computer.registerCursorMoveListener((__, player, from, to) -> {
    });

    DisplayCraft.getInstance().getExecutorService().scheduleAtFixedRate(() -> {
//      long start = System.nanoTime();
      computer.render();
//      System.out.println(System.nanoTime() - start + "");
    }, 100, 1000 / 20, TimeUnit.MILLISECONDS);

    DisplayCraft.getInstance().getCommand("display-craft").setExecutor((sender, command, label, args) -> {
      Player player = (Player) sender;
      computer.createScreen(player);
      return true;
    });
  }

  @Override
  public void onDisable() {
    computer.destroy();
  }
}
