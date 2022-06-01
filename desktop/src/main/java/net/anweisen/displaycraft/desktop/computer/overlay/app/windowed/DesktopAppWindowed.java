package net.anweisen.displaycraft.desktop.computer.overlay.app.windowed;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.image.Dimensions;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.image.Images;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorDisplay;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorDisplay.DefaultCursors;
import net.anweisen.displaycraft.desktop.computer.overlay.app.DesktopApp;
import org.bukkit.entity.Player;
import org.bukkit.map.MapPalette;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopAppWindowed implements DesktopApp {

  private DesktopComputer computer;
  private AppWindowHandler handler;

  private int bar = 12;
  private int scaleThreshold = 6;

  private final Collection<Player> moving = new LinkedList<>();
  private Map<Player, AppWindowScaleMode> scaleMode = new HashMap<>();

  private Dimensions dimensions;

  public DesktopAppWindowed(@Nonnull AppWindowHandler handler) {
    this.handler = handler;
  }

  @Override
  public void init(@Nonnull DesktopComputer computer, int index) {
    this.computer = computer;
    this.dimensions = computer.getScreen().getFullDimensions().withMargin(64);
  }

  @Override
  public void render(@Nonnull Image screen) {
    screen.setCurrentColor(MapPalette.DARK_GRAY);
    screen.fillRect(dimensions);

    screen.setCurrentColor(MapPalette.GRAY_2);
    screen.fillRect(dimensions.withHeight(bar));

    Dimensions appDimensions = getAppDimensions();
    Image rendererImage = Images.newImage(appDimensions.getWidth(), appDimensions.getHeight());
    handler.render(rendererImage);
    screen.drawImage(appDimensions.getX(), appDimensions.getY(), rendererImage);
  }

  @Override
  public void handleClick(@Nonnull Player player, @Nonnull Cursor cursor, boolean right) {
    AppWindowScaleMode applyingScaleMode = AppWindowScaleMode.findApplying(dimensions, cursor, scaleThreshold);

    if (moving.contains(player)) {
      moving.remove(player);
    } else if (scaleMode.containsKey(player)) {
      scaleMode.remove(player);
    } else if (applyingScaleMode != null) {
      scaleMode.put(player, applyingScaleMode);
    } else if (cursor.getAbsoluteY() - dimensions.getY() < bar) {
      if (!moving.contains(player)) moving.add(player);
    } else if (getAppDimensions().contains(cursor.getAbsoluteX(), cursor.getAbsoluteY())) {
      handler.handleClick(player, getAppCursor(cursor), right);
    }
  }

  @Override
  public void handleMove(@Nonnull Player player, @Nonnull Cursor from, @Nonnull Cursor to) {
    if (moving.contains(player)) {
      Dimensions moved = dimensions.withMoved(to.getAbsoluteX() - from.getAbsoluteX(), to.getAbsoluteY() - from.getAbsoluteY());
      if (!moved.fits(computer.getScreen().getWidth(), computer.getScreen().getHeight()))
        return;

      dimensions = moved;
    } else if (scaleMode.containsKey(player)) {
      AppWindowScaleMode scaleMode = this.scaleMode.get(player);
      Dimensions scaled = dimensions
        .addWidth(scaleMode.getModX() * (to.getAbsoluteX() - from.getAbsoluteX()))
        .addHeight(scaleMode.getModY() * (to.getAbsoluteY() - from.getAbsoluteY()));
      if (scaleMode.getModX() < 0)
        scaled = scaled.addX(-scaleMode.getModX() * (to.getAbsoluteX() - from.getAbsoluteX()));
      if (scaleMode.getModY() < 0)
        scaled = scaled.addY(-scaleMode.getModY() * (to.getAbsoluteY() - from.getAbsoluteY()));

      if (!scaled.fits(computer.getScreen().getWidth(), computer.getScreen().getHeight()))
        return;

      dimensions = scaled;
    } else if (getAppDimensions().contains(from.getAbsoluteX(), from.getAbsoluteY()) && getAppDimensions().contains(to.getAbsoluteX(), to.getAbsoluteY())) {
      handler.handleMove(player, getAppCursor(from), getAppCursor(to));
    }
  }

  @Nonnull
  public Dimensions getAppDimensions() {
    return dimensions.withMargin(bar, 0, 0, 0);
  }

  @Nonnull
  public Cursor getAppCursor(@Nonnull Cursor cursor) {
    Dimensions appDimensions = getAppDimensions();
    return Cursor.fromAbsolutes(
      cursor.getAbsoluteX() - appDimensions.getX(),
      cursor.getAbsoluteY() - appDimensions.getY(),
      appDimensions.getWidth(),
      appDimensions.getHeight()
    );
  }

  @Nonnull
  @Override
  public Dimensions getInteractionDimensions(@Nonnull Player player) {
    if (moving.contains(player) || scaleMode.containsKey(player))
      return computer.getScreen().getFullDimensions();
    return dimensions;
  }

  @Nonnull
  @Override
  public DesktopCursorDisplay getCursorDisplay(@Nonnull Player player, @Nonnull Cursor cursor) {
    if (moving.contains(player))
      return DefaultCursors.getMove();
    if (scaleMode.containsKey(player))
      return scaleMode.get(player).getCursor();

    AppWindowScaleMode applyingScaleMode = AppWindowScaleMode.findApplying(dimensions, cursor, scaleThreshold);
    if (applyingScaleMode != null)
      return applyingScaleMode.getCursor();
    if (cursor.getAbsoluteY() - dimensions.getY() < bar)
      return DefaultCursors.getMove();

    if (getAppDimensions().contains(cursor.getAbsoluteX(), cursor.getAbsoluteY()))
      return handler.getCursorDisplay(player, cursor);

    return DefaultCursors.getDefault();
  }

}
