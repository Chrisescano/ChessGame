package com.christian.app.piece;

import com.christian.app.game.util.Direction;
import java.util.List;

public class Queen extends Piece {

  private Queen(Position position, boolean isWhite, char symbol) {
    super(Type.QUEEN, position, isWhite, symbol);
  }

  // Methods

  public static Queen create(char symbol, int x, int y) {
    Position position = new Position(x, y);
    boolean isWhite = Character.isUpperCase(symbol);
    Queen queen = new Queen(position, isWhite, symbol);

    List<Direction> directions = List.of(
        Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST,
        Direction.NORTH_EAST, Direction.SOUTH_EAST, Direction.SOUTH_WEST, Direction.NORTH_WEST
    );

    for (Direction direction : directions) {
      List<Position> path = Piece.buildPath(position, direction, 7);
      queen.moves.put(direction, path);
    }

    return queen;
  }
}
