package net.anweisen.displaycraft.desktop;

import net.anweisen.displaycraft.DisplayCraft;
import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.Direction;
import net.anweisen.displaycraft.api.Position;
import net.anweisen.displaycraft.api.image.DrawHelper;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.image.Images;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import net.anweisen.displaycraft.desktop.computer.DesktopInteractionCursor;
import net.anweisen.displaycraft.api.multipart.MultipartScreen;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorClickListener;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorMoveListener;
import net.anweisen.displaycraft.desktop.computer.render.DesktopRenderer;
import net.anweisen.displaycraft.desktop.listener.PlayerInteractionListener;
import org.bukkit.entity.Player;
import org.bukkit.map.MapPalette;
import org.bukkit.plugin.java.JavaPlugin;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
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
      MultipartScreen.withCenter(Images.RESOLUTION, 7, 3, Position.from(1, 65, 0, Direction.WEST)),
      new DesktopInteractionCursor(10, 1000 / 40)
    );
    computer.startTasks();

//    Drawing drawing = new Drawing();
//    computer.registerCursorClickListener(drawing);
//    computer.registerCursorMoveListener(drawing);
//    computer.getRenderHandler().registerRendererAfter(drawing);
//    computer.getRenderHandler().registerRendererAfter((__, image) -> {
//      for (Cursor cursor : computer.getCursor().getCursorPositions()) {
//        DefaultCursors.getMove().draw(image, cursor);
//      }
//    });

    DisplayCraft.getInstance().getExecutorService().scheduleAtFixedRate(() -> {
      long start = System.nanoTime();
      computer.render();
//      System.out.println((System.nanoTime() - start) / 1_000_000f + "");
    }, 100, 1000 / 20, TimeUnit.MILLISECONDS);

    DisplayCraft.getInstance().getCommand("display-craft").setExecutor((sender, command, label, args) -> {
      Player player = (Player) sender;
      player.setAllowFlight(true);
      computer.createScreen(player);
      return true;
    });
  }

  @Override
  public void onDisable() {
    computer.destroy();
  }

  public class Drawing implements DesktopRenderer, DesktopCursorClickListener, DesktopCursorMoveListener {

    private final Collection<Player> drawing = new ArrayList<>();
    private Image image;

    @Override
    public void render(@Nonnull DesktopComputer computer, @Nonnull Image output) {
      if (image == null) image = output;
      output.drawImage(0, 0, image);
    }

    @Override
    public void handleClick(@Nonnull DesktopComputer computer, @Nonnull Player player, @Nonnull Cursor cursor, boolean right) {
      if (right) {
        image.setCurrentColor(MapPalette.LIGHT_GREEN);
        DrawHelper.fillBucket(image, image.getPixel(cursor.getAbsoluteX(), cursor.getAbsoluteY()), cursor.getAbsoluteX(), cursor.getAbsoluteY());
        return;
      }

      if (drawing.contains(player)) drawing.remove(player);
      else drawing.add(player);
    }

    @Override
    public void handleMove(@Nonnull DesktopComputer computer, @Nonnull Player player, @Nonnull Cursor from, @Nonnull Cursor to) {
      if (drawing.contains(player)) {
        image.setCurrentColor(MapPalette.LIGHT_GREEN);
        image.drawStroke(from.getAbsoluteX(), from.getAbsoluteY(), to.getAbsoluteX(), to.getAbsoluteY(), 3);
      }
    }
  }
}
