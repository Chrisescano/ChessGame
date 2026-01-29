package com.christian.app.game;

public enum Direction {

  NORTH(new Position(0, -1)),
  EAST(new Position(1, 0)),
  SOUTH(new Position(0, 1)),
  WEST(new Position(-1, 0)),
  NORTH_EAST(new Position(1, -1)),
  NORTH_WEST(new Position(-1, -1)),
  SOUTH_EAST(new Position(1, -1)),
  SOUTH_WEST(new Position(-1, -1)),

  NORTH_NORTH_EAST(new Position(1, -2)),
  NORTH_NORTH_WEST(new Position(-1, 2)),
  EAST_EAST_NORTH(new Position(2, -1)),
  EAST_EAST_SOUTH(new Position(2, 1)),
  SOUTH_SOUTH_EAST(new Position(1, 2)),
  SOUTH_SOUTH_WEST(new Position(-1, 2)),
  WEST_WEST_NORTH(new Position(-2, -1)),
  WEST_WEST_SOUTH(new Position(-2, 1));

  private final Position position;

  Direction(Position position) {
    this.position = position;
  }

  public int getX() {
    return position.getX();
  }

  public int getY() {
    return position.getY();
  }
}
