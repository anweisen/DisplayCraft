package net.anweisen.displaycraft.desktop.computer;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopComputer {

  private final DesktopInteractionScreen screen;

  public DesktopComputer(@Nonnull DesktopInteractionScreen screen) {
    this.screen = screen;
  }

  @Nonnull
  public DesktopInteractionScreen getScreen() {
    return screen;
  }
}
