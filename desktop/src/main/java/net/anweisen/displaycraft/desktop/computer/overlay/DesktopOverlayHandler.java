package net.anweisen.displaycraft.desktop.computer.overlay;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.image.Dimensions;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorDisplay;
import net.anweisen.displaycraft.desktop.computer.overlay.app.DesktopApp;
import net.anweisen.displaycraft.desktop.computer.overlay.app.windowed.DesktopAppWindowed;
import net.anweisen.displaycraft.desktop.computer.overlay.app.windowed.apps.PaintApp;
import net.anweisen.displaycraft.desktop.computer.overlay.home.DesktopHomeComponent;
import org.bukkit.entity.Player;
import org.bukkit.map.MapPalette;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopOverlayHandler {

  private final DesktopComputer computer;

  private final AtomicReference<DesktopHomeComponent> home = new AtomicReference<>(new DesktopHomeComponent.Default());
  private final ArrayList<Integer> renderIndex = new ArrayList<>();
  private final Map<Integer, DesktopApp> apps = new HashMap<>();
  private final AtomicInteger appId = new AtomicInteger();

  public DesktopOverlayHandler(@Nonnull DesktopComputer computer) {
    this.computer = computer;

    registerApp(new DesktopAppWindowed(new PaintApp()));
  }

  public void registerHandlers() {
    computer.getRenderHandler().registerRendererAfter((__, output) -> {
      home.get().render(output);

      // reversed loop, render lowest first
      for (int i = renderIndex.size() - 1; i >= 0; i--) {
        int index = renderIndex.get(i);
        DesktopApp app = apps.get(index);
        app.render(output);
      }

      cursors:
      for (var entry : computer.getCursor().getPlayerCursorPositions()) {
        Cursor cursor = entry.getValue();
        Player player = entry.getKey();

        for (int index : renderIndex) {
          DesktopApp app = apps.get(index);
          Dimensions dimensions = app.getInteractionDimensions(player);

          if (dimensions.contains(cursor.getAbsoluteX(), cursor.getAbsoluteY())) {
            DesktopCursorDisplay display = app.getCursorDisplay(player, cursor);
            display.draw(output, cursor);
            continue cursors;
          }
        }

        // no app is target, use default
        // TODO home screen
        DesktopCursorDisplay.DefaultCursors.getDefault().draw(output, cursor);
      }
    });
    computer.registerCursorClickListener((__, player, cursor, right) -> {
      for (int i = 0; i < renderIndex.size(); i++) {
        int index = renderIndex.get(i);
        DesktopApp app = apps.get(index);
        Dimensions dimensions = app.getInteractionDimensions(player);

        if (dimensions.contains(cursor.getAbsoluteX(), cursor.getAbsoluteY())) {
          // app clicked -> move to top of stack
          if (i != 0 && renderIndex.size() > 1) {
            renderIndex.remove(i);
            renderIndex.add(0, index);
          }

          app.handleClick(player, cursor, right);
          break;
        }
      }
    });
    computer.registerCursorMoveListener((__, player, from, to) -> {
      for (int index : renderIndex) {
        DesktopApp app = apps.get(index);
        Dimensions dimensions = app.getInteractionDimensions(player);

        if (dimensions.contains(from.getAbsoluteX(), from.getAbsoluteY())
          && dimensions.contains(to.getAbsoluteX(), to.getAbsoluteY())) {

          app.handleMove(player, from, to);
          break;
        }
      }
    });
  }

  public void registerApp(@Nonnull DesktopApp app) {
    int index = appId.incrementAndGet();
    app.init(computer, index);

    apps.put(index, app);
    renderIndex.add(0, index);
  }

  @Nonnull
  public DesktopComputer getComputer() {
    return computer;
  }
}
