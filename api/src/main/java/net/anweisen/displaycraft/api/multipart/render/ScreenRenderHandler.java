package net.anweisen.displaycraft.api.multipart.render;

import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.multipart.MultipartScreen;
import net.anweisen.displaycraft.api.multipart.RenderFilter;
import net.anweisen.displaycraft.api.multipart.RenderFilterStore;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ScreenRenderHandler {

  private final List<ScreenRenderer> renderer = new CopyOnWriteArrayList<>();
  private final RenderFilterStore store = new RenderFilterStore();
  private final MultipartScreen screen;

  public ScreenRenderHandler(@Nonnull MultipartScreen screen) {
    this.screen = screen;
  }

  public void registerRendererAfter(@Nonnull ScreenRenderer renderer) {
    this.renderer.add(renderer);
  }

  public void registerRendererBefore(@Nonnull ScreenRenderer renderer) {
    this.renderer.add(0, renderer);
  }

  public void removeRenderer(@Nonnull ScreenRenderer renderer) {
    this.renderer.remove(renderer);
  }

  public void removeRenderer(@Nonnull Class<?> rendererClass) {
    this.renderer.removeIf(current -> current.getClass() == rendererClass);
  }

  @Nonnull
  public Image renderImage() {
    Image image = screen.newImage();

    for (ScreenRenderer renderer : renderer) {
      try {
        renderer.render(image);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return image;
  }

  public void distributeEntirely(@Nonnull Collection<? extends Player> viewers, @Nonnull Image image) {
    screen.render(viewers, image, RenderFilter.ALL);
  }

  public void distributeEfficiently(@Nonnull Collection<? extends Player> viewers, @Nonnull Image image) {
    screen.render(viewers, image, store);
  }

  public void render(@Nonnull Collection<? extends Player> viewers) {
    distributeEfficiently(viewers, renderImage());
  }

  public void renderLast(@Nonnull Collection<? extends Player> viewers) {
    Image last = store.getLastImage();
    if (last != null) {
      distributeEntirely(viewers, last);
    }
  }

  @Nonnull
  public RenderFilterStore getStore() {
    return store;
  }

  @Nonnull
  public MultipartScreen getScreen() {
    return screen;
  }
}
