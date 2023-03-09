package net.anweisen.displaycraft.api.image.ui.component;

import net.anweisen.displaycraft.api.image.Image;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class Area implements Component {

  private final Collection<Component> components = new ArrayList<>();

  public void addComponent(@Nonnull Component component) {
    if (component == this) throw new IllegalArgumentException("Cannot add itself");
    components.add(component);
  }

  @Override
  public void render(@Nonnull Image image) {

  }
}
