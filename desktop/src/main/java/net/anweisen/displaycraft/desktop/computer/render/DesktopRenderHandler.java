package net.anweisen.displaycraft.desktop.computer.render;

import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopRenderHandler {

  private final List<DesktopRenderer> renderer = new CopyOnWriteArrayList<>();
  private final DesktopTileStore store = new DesktopTileStore();
  private final DesktopComputer computer;

  public DesktopRenderHandler(@Nonnull DesktopComputer computer) {
    this.computer = computer;
  }

  public void registerRendererAfter(@Nonnull DesktopRenderer renderer) {
    this.renderer.add(renderer);
  }

  public void registerRendererBefore(@Nonnull DesktopRenderer renderer) {
    this.renderer.add(0, renderer);
  }

  public void removeRenderer(@Nonnull DesktopRenderer renderer) {
    this.renderer.remove(renderer);
  }

  public void removeRenderer(@Nonnull Class<?> rendererClass) {
    this.renderer.removeIf(current -> current.getClass() == rendererClass);
  }

  @Nonnull
  public Image renderImage() {
    Image image = computer.getScreen().newImage();

    for (DesktopRenderer renderer : renderer) {
      try {
        renderer.render(computer, image);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return image;
  }

  public void distributeEntirely(@Nonnull Collection<? extends Player> viewers, @Nonnull Image image) {
    computer.getScreen().render(viewers, image, (tile, x, y) -> true);
  }

  public void distributeEfficent(@Nonnull Collection<? extends Player> viewers, @Nonnull Image image) {
    store.distribute(computer.getScreen(), viewers, image);
  }

  public void render(@Nonnull Collection<? extends Player> viewers) {
    distributeEfficent(viewers, renderImage());
  }

  public void renderLast(@Nonnull Collection<? extends Player> viewers) {
    Image last = store.getLastImage();
    if (last != null) {
      distributeEntirely(viewers, last);
    }
  }

  @Nonnull
  public DesktopTileStore getStore() {
    return store;
  }

  @Nonnull
  public DesktopComputer getComputer() {
    return computer;
  }

}
