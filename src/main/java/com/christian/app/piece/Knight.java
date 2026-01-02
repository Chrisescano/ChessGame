package com.christian.app.piece;

import com.christian.app.game.util.Direction;
import java.util.List;

public class Knight extends Piece {

  public Knight(Position position, boolean isWhite, char symbol) {
    super(Type.KNIGHT, position, isWhite, symbol);
  }

  // Methods

  public static Knight create(char symbol, int x, int y) {
    Position position = new Position(x, y);
    boolean isWhite = Character.isUpperCase(symbol);
    Knight knight = new Knight(position, isWhite, symbol);

    List<Direction> directions = List.of(
        Direction.NORTH_NORTH_EAST, Direction.NORTH_NORTH_WEST, Direction.EAST_NORTH_EAST,
        Direction.EAST_SOUTH_EAST, Direction.SOUTH_SOUTH_EAST, Direction.SOUTH_SOUTH_WEST,
        Direction.WEST_NORTH_WEST, Direction.WEST_SOUTH_WEST
    );

    for (Direction direction : directions) {
      List<Position> path = Piece.buildPath(position, direction, 1);
      knight.moves.put(direction, path);
    }

    return knight;
  }
}
