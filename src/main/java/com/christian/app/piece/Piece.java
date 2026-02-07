package com.christian.app.piece;

import com.christian.app.game.Direction;
import com.christian.app.game.GameUtil;
import com.christian.app.util.Pair;
import com.christian.app.util.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Piece {

  private final Type type;
  private final Position position;
  private final char symbol;
  private final boolean isWhite;
  private final Map<Direction, List<Position>> moves;

  private boolean isMoved;

  private Piece(final Type type, final Position position, final char symbol, final boolean isWhite,
      final Map<Direction, List<Position>> moves) {
    this.type = type;
    this.position = position;
    this.symbol = symbol;
    this.isWhite = isWhite;
    this.moves = moves;
  }

  public static Piece create(final char symbol, final int file, final int rank) {
    final Type type = Type.toType(symbol);
    if (type != null && GameUtil.isInsideBoard(file, rank)) {
      final boolean isWhite = Character.isUpperCase(symbol);
      final List<Pair<Direction, Integer>> directions = directionsOf(type, isWhite);
      final Map<Direction, List<Position>> moves = new HashMap<>();
      for (Pair<Direction, Integer> pair : directions) {
        final Direction direction = pair.getFirst();
        final List<Position> path = new ArrayList<>();
        for (int i = 1; i <= pair.getSecond(); i++) {
          final int stepFile = file + (direction.getPosition().getFile() * i);
          final int stepRank = rank + (direction.getPosition().getRank() * i);
          path.add(new Position(stepFile, stepRank));
        }
        moves.put(direction, path);
      }
      return new Piece(type, new Position(file, rank), symbol, isWhite, moves);
    }
    return null;
  }

  private static List<Pair<Direction, Integer>> directionsOf(final Type type, final boolean isWhite) {
    return switch (type) {
      case PAWN -> isWhite ? List.of(
          Pair.of(Direction.NORTH, 2),
          Pair.of(Direction.NORTH_EAST, 1),
          Pair.of(Direction.NORTH_WEST, 1)
      ) : List.of(
          Pair.of(Direction.SOUTH, 2),
          Pair.of(Direction.SOUTH_EAST, 1),
          Pair.of(Direction.SOUTH_WEST, 1)
      );
      case ROOK -> List.of(
          Pair.of(Direction.NORTH, 7),
          Pair.of(Direction.EAST, 7),
          Pair.of(Direction.SOUTH, 7),
          Pair.of(Direction.WEST, 7)
      );
      case KNIGHT -> List.of(
          Pair.of(Direction.NNE, 1),
          Pair.of(Direction.NNW, 1),
          Pair.of(Direction.EEN, 1),
          Pair.of(Direction.EES, 1),
          Pair.of(Direction.SSE, 1),
          Pair.of(Direction.SSW, 1),
          Pair.of(Direction.WWN, 1),
          Pair.of(Direction.WWS, 1)
      );
      case BISHOP -> List.of(
          Pair.of(Direction.NORTH_EAST, 7),
          Pair.of(Direction.SOUTH_EAST, 7),
          Pair.of(Direction.SOUTH_WEST, 7),
          Pair.of(Direction.NORTH_WEST, 7)
      );
      case QUEEN -> List.of(
          Pair.of(Direction.NORTH, 7),
          Pair.of(Direction.EAST, 7),
          Pair.of(Direction.SOUTH, 7),
          Pair.of(Direction.WEST, 7),
          Pair.of(Direction.NORTH_EAST, 7),
          Pair.of(Direction.SOUTH_EAST, 7),
          Pair.of(Direction.SOUTH_WEST, 7),
          Pair.of(Direction.NORTH_WEST, 7)
      );
      case KING -> List.of(
          Pair.of(Direction.NORTH, 1),
          Pair.of(Direction.EAST, 2),
          Pair.of(Direction.SOUTH, 1),
          Pair.of(Direction.WEST, 3),
          Pair.of(Direction.NORTH_EAST, 1),
          Pair.of(Direction.SOUTH_EAST, 1),
          Pair.of(Direction.SOUTH_WEST, 1),
          Pair.of(Direction.NORTH_WEST, 1)
      );
    };
  }

  public void toggledMoved() {
    isMoved = true;
  }

  public Type getType() {
    return type;
  }

  public Position getPosition() {
    return position;
  }

  public char getSymbol() {
    return symbol;
  }

  public boolean isWhite() {
    return isWhite;
  }

  public Map<Direction, List<Position>> getMoves() {
    return moves;
  }

  public boolean isMoved() {
    return isMoved;
  }
}
