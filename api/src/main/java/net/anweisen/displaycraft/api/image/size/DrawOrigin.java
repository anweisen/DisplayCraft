package net.anweisen.displaycraft.api.image.size;

import javax.annotation.Nonnull;
import java.util.function.IntFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public enum DrawOrigin {

  LEFT_TOP(width -> 0, height -> 0),
  LEFT_BOTTOM(width -> 0, height -> height),
  LEFT_CENTER(width -> 0, height -> height / 2),

  RIGHT_TOP(width -> width, height -> 0),
  RIGHT_BOTTOM(width -> width, height -> height),
  RIGHT_CENTER(width -> width, height -> height / 2),

  CENTER_TOP(width -> width / 2, height -> 0),
  CENTER_BOTTOM(width -> width / 2, height -> height),
  CENTER_CENTER(width -> width / 2, height -> height / 2);

  private final IntFunction<Integer> x, y;

  DrawOrigin(@Nonnull IntFunction<Integer> x, @Nonnull IntFunction<Integer> y) {
    this.x = x;
    this.y = y;
  }

  public int offsetX(int x, int width) {
    return x - this.x.apply(width);
  }

  public int offsetY(int y, int height) {
    return y - this.y.apply(height);
  }

}
