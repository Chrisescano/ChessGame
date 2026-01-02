package com.christian.app.piece;

import com.christian.app.game.util.Direction;
import java.util.List;

public class King extends Piece {

  private King(Position position, boolean isWhite, char symbol) {
    super(Type.KING, position, isWhite, symbol);
  }

  // Methods

  public static King create(char symbol, int x, int y) {
    Position position = new Position(x, y);
    boolean isWhite = Character.isUpperCase(symbol);
    King king = new King(position, isWhite, symbol);

    List<Direction> directions = List.of(
        Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST,
        Direction.NORTH_EAST, Direction.SOUTH_EAST, Direction.SOUTH_WEST, Direction.NORTH_WEST
    );
    List<Integer> numSteps = List.of(
        1, 2, 1, 3, 1, 1, 1, 1
    );

    for (int i = 0; i < directions.size(); i++) {
      Direction direction = directions.get(i);
      int steps = numSteps.get(i);
      List<Position> path = Piece.buildPath(position, direction, steps);
      king.moves.put(direction, path);
    }

    return king;
  }
}
