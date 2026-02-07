package com.christian.app.game;

import com.christian.app.util.Position;

public enum Direction {

  NORTH(new Position(0, -1)),
  EAST(new Position(1, 0)),
  SOUTH(new Position(0, 1)),
  WEST(new Position(-1, 0)),
  NORTH_EAST(new Position(1, -1)),
  NORTH_WEST(new Position(-1, -1)),
  SOUTH_EAST(new Position(1, 1)),
  SOUTH_WEST(new Position(-1, 1)),
  NNE(new Position(1, -2)),
  NNW(new Position(-1, -2)),
  EEN(new Position(2, -1)),
  EES(new Position(2, 1)),
  SSE(new Position(1, 2)),
  SSW(new Position(-1, 2)),
  WWN(new Position(-2, -1)),
  WWS(new Position(-2, 1));

  private final Position position;

  Direction(final Position position) {
    this.position = position;
  }

  public Position getPosition() {
    return position;
  }
}
