package net.anweisen.displaycraft.desktop.computer.component;

import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopComponentHandler {

  private final DesktopComputer computer;

  public DesktopComponentHandler(@Nonnull DesktopComputer computer) {
    this.computer = computer;
  }

  @Nonnull
  public DesktopComputer getComputer() {
    return computer;
  }
}
