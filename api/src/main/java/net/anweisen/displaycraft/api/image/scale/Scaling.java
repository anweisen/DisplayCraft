package net.anweisen.displaycraft.api.image.scale;

import net.anweisen.displaycraft.api.image.Image;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Scaling {

  @Nonnull
  static Scaling.Creator absolute(int pixel) {
    return new Creator(image -> pixel);
  }

  @Nonnull
  static Scaling.Creator height(double percentage) {
    return new Creator(image -> (int) Math.round(image.getHeight() * percentage));
  }

  @Nonnull
  static Scaling.Creator width(double percentage) {
    return new Creator(image -> (int) Math.round(image.getWidth() * percentage));
  }

  int resolve(@Nonnull Image image);

  final class Creator implements Scaling {

    private final Scaling origin;
    private final Scaling max, min;

    public Creator(@Nonnull Scaling origin) {
      this(origin, origin, origin);
    }

    public Creator(@Nonnull Scaling origin, @Nonnull Scaling max, @Nonnull Scaling min) {
      this.origin = origin;
      this.max = max;
      this.min = min;
    }

    @Nonnull
    @CheckReturnValue
    public Creator maxByWidth(double percentage) {
      return new Creator(origin, Scaling.width(percentage), min);
    }

    @Nonnull
    @CheckReturnValue
    public Creator maxByHeight(double percentage) {
      return new Creator(origin, Scaling.height(percentage), min);
    }

    @Nonnull
    @CheckReturnValue
    public Creator max(int pixels) {
      return new Creator(origin, Scaling.absolute(pixels), min);
    }

    @Nonnull
    @CheckReturnValue
    public Creator max(@Nonnull Scaling scaling) {
      return new Creator(origin, scaling, min);
    }

    @Override
    public int resolve(@Nonnull Image image) {
      int value = origin.resolve(image);

      if (max != null) {
        int maxValue = max.resolve(image);
        if (value > maxValue)
          return maxValue;
      }

      if (min != null) {
        int minValue = min.resolve(image);
        if (minValue > value)
          return minValue;
      }

      return value;
    }
  }

}
