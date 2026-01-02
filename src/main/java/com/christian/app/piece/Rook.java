package com.christian.app.piece;

import com.christian.app.game.util.Direction;
import java.util.List;

public class Rook extends Piece {

  public Rook(Position position, boolean isWhite, char symbol) {
    super(Type.ROOK, position, isWhite, symbol);
  }

  // Methods

  public static Rook create(char symbol, int x, int y) {
    Position position = new Position(x, y);
    boolean isWhite = Character.isUpperCase(symbol);
    Rook rook = new Rook(position, isWhite, symbol);

    List<Direction> directions = List.of(
        Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
    );

    for (Direction direction : directions) {
      List<Position> path = Piece.buildPath(position, direction, 7);
      rook.moves.put(direction, path);
    }

    return rook;
  }
}
