package net.anweisen.displaycraft.desktop.computer.render;

import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.desktop.computer.DesktopInteractionScreen;
import net.anweisen.utility.common.collection.pair.Tuple;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopTileStore implements DesktopInteractionScreen.RenderFilter {

  private final Map<Tuple<Integer, Integer>, byte[]> tileTracker = new ConcurrentHashMap<>();
  private final AtomicReference<Image> lastImage = new AtomicReference<>();

  @Override
  public boolean shouldRender(@Nonnull Image image, int x, int y) {
    byte[] rendered = tileTracker.get(Tuple.of(x, y));
    if (rendered == null) {
      storeRenderedTile(image.getContent(), x, y);
      return true;
    }
    boolean equals = Arrays.equals(rendered, image.getContent());
    if (!equals) storeRenderedTile(image.getContent(), x, y);
    return !equals;
  }

  public void storeRenderedTile(@Nonnull byte[] image, int x, int y) {
    byte[] content = new byte[image.length];
    System.arraycopy(image, 0, content, 0, content.length);
    tileTracker.put(Tuple.of(x, y), content);
  }

  public void distribute(@Nonnull DesktopInteractionScreen screen, @Nonnull Collection<? extends Player> viewers, @Nonnull Image image) {
    lastImage.set(image);
    screen.render(viewers, image, this);
  }

  @Nullable
  public Image getLastImage() {
    return lastImage.get();
  }
}
