package net.anweisen.displaycraft.api.image.ui;

import net.anweisen.displaycraft.api.image.size.Sized;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Scaling {

  @Nonnegative
  int resolve(@Nonnull Sized total, @Nonnull Sized area);

  record Relatives(@Nullable Relative min, @Nonnull Relative main, @Nullable Relative max) implements Scaling {

    public Relatives {
      if (main == null) throw new IllegalArgumentException("Main Relative cannot be null");
    }

    @Override
    @Nonnegative
    public int resolve(@Nonnull Sized total, @Nonnull Sized area) {
      int main = this.main.resolve(total, area);

      if (this.min != null) {
        int min = this.min.resolve(total, area);
        if (main < min) return min;
      }
      if (this.max != null) {
        int max = this.max.resolve(total, area);
        if (main > max) return max;
      }

      return main;
    }

  }

  record Relative(float value, @Nonnull From type) implements Scaling {

    public Relative {
      if (type == From.ABSOLUTE) {
        if (value % 1.0 == 0) throw new IllegalArgumentException("Cannot use decimals for ABSOLUTE");
      } else {
        if (value < 0 || value > 100) throw new IllegalArgumentException("Cannot use numbers out of bound 0 - 100");
      }
    }

    @Override
    @Nonnegative
    public int resolve(@Nonnull Sized total, @Nonnull Sized area) {
      return switch (type) {
        case ABSOLUTE -> (int) value;
        case PERCENT_HEIGHT -> (int) (value * area.getHeight());
        case PERCENT_WIDTH -> (int) (value * area.getWidth());
        case TOTAL_WIDTH -> (int) (value * total.getWidth());
        case TOTAL_HEIGHT -> (int) (value * total.getHeight());
      };
    }

    public enum From {
      PERCENT_WIDTH,    // %
      PERCENT_HEIGHT,   // %
      TOTAL_WIDTH,      // vw
      TOTAL_HEIGHT,     // vh
      ABSOLUTE,         // px
    }

  }
}
