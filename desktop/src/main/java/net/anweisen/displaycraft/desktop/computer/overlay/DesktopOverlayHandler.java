package net.anweisen.displaycraft.desktop.computer.overlay;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.image.scale.Dimensions;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorDisplay;
import net.anweisen.displaycraft.desktop.computer.overlay.app.DesktopApp;
import net.anweisen.displaycraft.desktop.computer.overlay.home.DesktopHomeComponent;
import org.bukkit.entity.Player;
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
  private final ArrayList<Integer> appRenderIndexOrder = new ArrayList<>();
  private final Map<Integer, DesktopApp> appsByIndex = new HashMap<>();
  private final AtomicInteger appIndexTracker = new AtomicInteger();

  public DesktopOverlayHandler(@Nonnull DesktopComputer computer) {
    this.computer = computer;
  }

  public void registerHandlers() {
    computer.getRenderHandler().registerRendererAfter((output) -> {
      home.get().render(output);

      // reversed loop, render lowest first
      for (int i = appRenderIndexOrder.size() - 1; i >= 0; i--) {
        int index = appRenderIndexOrder.get(i);
        DesktopApp app = appsByIndex.get(index);
        app.render(output);
      }

      cursors:
      for (var entry : computer.getCursor().getPlayerCursorPositions()) {
        Cursor cursor = entry.getValue();
        Player player = entry.getKey();

        for (int index : appRenderIndexOrder) {
          DesktopApp app = appsByIndex.get(index);
          Dimensions dimensions = app.getInteractionDimensions(player);

          if (dimensions.contains(cursor.getAbsoluteX(), cursor.getAbsoluteY())) {
            DesktopCursorDisplay display = app.getCursorDisplay(player, cursor);
            display.draw(output, cursor);
            continue cursors;
          }
        }

        // no app is target, use home component when available or fallback to default
        if (home.get() != null) {
          home.get().getCursorDisplay(player, cursor).draw(output, cursor);
        } else {
          DesktopCursorDisplay.DefaultCursors.getDefault().draw(output, cursor);
        }
      }
    });
    computer.registerCursorClickListener((player, cursor, right) -> {
      for (int i = 0; i < appRenderIndexOrder.size(); i++) {
        int index = appRenderIndexOrder.get(i);
        DesktopApp app = appsByIndex.get(index);
        Dimensions dimensions = app.getInteractionDimensions(player);

        if (dimensions.contains(cursor.getAbsoluteX(), cursor.getAbsoluteY())) {
          // app clicked -> move to top of stack
          if (i != 0 && appRenderIndexOrder.size() > 1) { // if it's not already on top & it's not the only app
            appRenderIndexOrder.remove(i);
            appRenderIndexOrder.add(0, index);
          }

          app.handleClick(player, cursor, right);
          break;
        }
      }
    });
    computer.registerCursorMoveListener((player, from, to) -> {
      for (int index : appRenderIndexOrder) {
        DesktopApp app = appsByIndex.get(index);
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
    int index = appIndexTracker.incrementAndGet();
    app.init(computer, index);

    appsByIndex.put(index, app);
    appRenderIndexOrder.add(0, index);
  }

  public void registerApps(@Nonnull DesktopApp... apps) {
    for (DesktopApp app : apps)
      registerApp(app);
  }

  @Nonnull
  public DesktopComputer getComputer() {
    return computer;
  }
}
