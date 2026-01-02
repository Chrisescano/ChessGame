package com.christian.app.piece;

import com.christian.app.game.util.Direction;
import java.util.List;

public class Bishop extends Piece {

  private Bishop(Position position, boolean isWhite, char symbol) {
    super(Type.BISHOP, position, isWhite, symbol);
  }

  // Methods

  public static Bishop create(char symbol, int x, int y) {
    Position position = new Position(x, y);
    boolean isWhite = Character.isUpperCase(symbol);
    Bishop bishop = new Bishop(position, isWhite, symbol);

    List<Direction> directions = List.of(
        Direction.NORTH_EAST, Direction.SOUTH_EAST, Direction.SOUTH_WEST, Direction.NORTH_WEST
    );

    for (Direction direction : directions) {
      List<Position> path = Piece.buildPath(position, direction, 7);
      bishop.moves.put(direction, path);
    }

    return bishop;
  }
}
