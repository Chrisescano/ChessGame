package com.christian.app.piece;

import java.util.Objects;

public class Position {

  private int x;
  private int y;
  private boolean isEnabled;

  public Position() {
  }

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // Methods

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Position position)) {
      return false;
    }
    return x == position.x && y == position.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "Position{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }

  /// Getter/Setter

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }
}
