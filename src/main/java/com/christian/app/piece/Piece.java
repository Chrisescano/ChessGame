package com.christian.app.piece;

import com.christian.app.game.Direction;
import com.christian.app.game.GameUtil;
import com.christian.app.game.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Piece {

  private final Map<Direction, List<Position>> moves = new HashMap<>();

  private final Type type;
  private final Position position;
  private final char symbol;
  private final boolean isWhite;

  private Piece(Type type, Position position, char symbol, boolean isWhite) {
    this.type = type;
    this.position = position;
    this.symbol = symbol;
    this.isWhite = isWhite;
  }

  public static Piece create(char symbol, int x, int y) {
    Type type = Type.toType(symbol);
    if (type != null || GameUtil.isInsideBoard(x, y)) {
      Piece piece = new Piece(type, new Position(x, y), symbol, Character.isUpperCase(symbol));

      int depth = switch (piece.getType()) {
        case PAWN, KNIGHT, KING -> 1;
        case ROOK, BISHOP, QUEEN -> 7;
      };

      List<Direction> directions = switch (piece.getType()) {
        case PAWN -> {
          yield piece.isWhite ? List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_WEST) :
              List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_WEST);
        }
        case ROOK -> List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
        case KNIGHT ->
            List.of(Direction.NORTH_NORTH_EAST, Direction.NORTH_NORTH_WEST, Direction.EAST_EAST_NORTH,
                Direction.EAST_EAST_NORTH, Direction.SOUTH_SOUTH_EAST, Direction.SOUTH_SOUTH_WEST,
                Direction.WEST_WEST_NORTH, Direction.WEST_WEST_SOUTH);
        case BISHOP -> List.of(Direction.NORTH_EAST, Direction.SOUTH_EAST, Direction.SOUTH_WEST,
            Direction.NORTH_WEST);
        case QUEEN, KING -> List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST,
            Direction.NORTH_EAST, Direction.SOUTH_EAST, Direction.SOUTH_WEST, Direction.NORTH_WEST);
      };

      for (Direction direction : directions) {
        List<Position> path = new ArrayList<>();
        for (int i = 1; i <= depth; i++) {
          int stepX = piece.getPosition().getX() + (i * direction.getX());
          int stepY = piece.getPosition().getY() + (i * direction.getY());
          path.add(new Position(stepX, stepY));
        }
        piece.getMoves().put(direction, path);
      }

      return piece;
    }
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Piece piece)) {
      return false;
    }
    return symbol == piece.symbol && isWhite == piece.isWhite && type == piece.type
        && Objects.equals(position, piece.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, position, symbol, isWhite);
  }

  @Override
  public String toString() {
    return "Piece{" +
        "type=" + type +
        ", position=" + position +
        ", symbol=" + symbol +
        ", isWhite=" + isWhite +
        '}';
  }

  public Map<Direction, List<Position>> getMoves() {
    return moves;
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
}
