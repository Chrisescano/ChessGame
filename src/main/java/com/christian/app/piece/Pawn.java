package com.christian.app.piece;

import com.christian.app.game.util.Direction;
import java.util.List;

public class Pawn extends Piece {

  public Pawn(Position position, boolean isWhite, char symbol) {
    super(Type.PAWN, position, isWhite, symbol);
  }

  // Methods

  public static Pawn create(char symbol, int x, int y) {
    Position position = new Position(x, y);
    boolean isWhite = Character.isUpperCase(symbol);
    Pawn pawn = new Pawn(position, isWhite, symbol);

    List<Direction> northDirections = List.of(
        Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_WEST
    );
    List<Direction> southDirections = List.of(
        Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_WEST
    );
    List<Integer> numSteps = List.of(2, 1, 1);

    for (int i = 0; i < northDirections.size(); i++) {
      Direction direction = isWhite ? northDirections.get(i) : southDirections.get(i);
      int steps = numSteps.get(i);
      List<Position> path = Piece.buildPath(position, direction, steps);
    }

    return pawn;
  }
}
