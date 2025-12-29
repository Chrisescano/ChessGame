package com.christian.app.game;

import com.christian.app.piece.Piece;
import com.christian.app.piece.Position;
import com.christian.app.piece.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveManager {

  private static final List<Direction> WHITE_PAWN_DIRECTIONS = List.of(Direction.NORTH,
      Direction.NORTH_EAST, Direction.NORTH_WEST);
  private static final List<Direction> BLACK_PAWN_DIRECTIONS = List.of(Direction.SOUTH,
      Direction.SOUTH_EAST, Direction.SOUTH_WEST);
  private static final List<Direction> ROOK_DIRECTIONS = List.of(Direction.NORTH, Direction.EAST,
      Direction.SOUTH, Direction.WEST);
  private static final List<Direction> KNIGHT_DIRECTIONS = List.of(Direction.NORTH_NORTH_EAST,
      Direction.NORTH_NORTH_WEST, Direction.EAST_NORTH_EAST, Direction.EAST_SOUTH_EAST,
      Direction.SOUTH_SOUTH_EAST, Direction.SOUTH_SOUTH_WEST, Direction.WEST_SOUTH_WEST,
      Direction.WEST_NORTH_WEST);
  private static final List<Direction> BISHOP_DIRECTIONS = List.of(Direction.NORTH_EAST,
      Direction.NORTH_WEST, Direction.SOUTH_WEST,
      Direction.SOUTH_EAST);
  private static final List<Direction> QUEEN_KING_DIRECTIONS = List.of(Direction.NORTH,
      Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH_EAST, Direction.NORTH_WEST,
      Direction.SOUTH_EAST, Direction.SOUTH_WEST);

  private final Board board;

  public MoveManager(Board board) {
    this.board = board;
  }

  public static void main(String[] args) {
    Piece piece = new Piece(Type.PAWN, new Position(1, 1), true);
    Position destination = new Position(2, 2);

    MoveManager moveManager = new MoveManager(new Board());
    Map<Direction, List<Position>> moves = moveManager.buildPieceMoves(piece);
    piece.setMoves(moves);

    System.out.println(moveManager.directionOfPawnMove(piece, destination));
  }

  // Methods

  public Map<Direction, List<Position>> buildPieceMoves(Piece piece) {
    Map<Direction, List<Position>> moves = new HashMap<>();

    int lengthByType = switch (piece.getType()) {
      case PAWN, KNIGHT, KING -> 1;
      case ROOK, BISHOP, QUEEN -> 7;
    };

    List<Direction> directionsByType = switch (piece.getType()) {
      case PAWN -> piece.isWhite() ? WHITE_PAWN_DIRECTIONS : BLACK_PAWN_DIRECTIONS;
      case ROOK -> ROOK_DIRECTIONS;
      case KNIGHT -> KNIGHT_DIRECTIONS;
      case BISHOP -> BISHOP_DIRECTIONS;
      case QUEEN, KING -> QUEEN_KING_DIRECTIONS;
    };

    for (Direction direction : directionsByType) {
      List<Position> path = buildPath(piece.getPosition(), direction, lengthByType);
      moves.put(direction, path);
    }

    return moves;
  }

  public Direction directionOfMove(Piece piece, Position destination) {
    return switch (piece.getType()) {
      case PAWN -> directionOfPawnMove(piece, destination);
      case ROOK -> directionOfRookMove(piece, destination);
      case KNIGHT -> directionOfKnightMove(piece, destination);
      case BISHOP -> directionOfBishopMove(piece, destination);
      case QUEEN -> directionOfQueenMove(piece, destination);
      case KING -> directionOfKingMove(piece, destination);
    };
  }

  private Direction directionOfPawnMove(Piece piece, Position destination) {
    Position piecePos = piece.getPosition();
    int xDiff = destination.getX() - piecePos.getX();
    int yDiff = destination.getY() - piecePos.getY();
    Position diffPos = new Position(xDiff, yDiff);

    for (Direction direction : piece.isWhite() ? WHITE_PAWN_DIRECTIONS : BLACK_PAWN_DIRECTIONS) {
      if (direction.getPosition().equals(diffPos)) {
        return direction;
      }
    }

    return null;
  }

  private Direction directionOfRookMove(Piece piece, Position destination) {
    Position piecePos = piece.getPosition();

    if (pointsAreHorizontal(piecePos, destination)) {
      if (pointBeforeOnXAxis(piecePos, destination)) {
        return Direction.EAST;
      } else {
        return Direction.WEST;
      }
    }

    if (pointsAreVertical(piecePos, destination)) {
      if (pointBeforeOnYAxis(piecePos, destination)) {
        return Direction.SOUTH;
      } else {
        return Direction.NORTH;
      }
    }

    return null;
  }

  public Direction directionOfKnightMove(Piece piece, Position destination) {
    Position piecePos = piece.getPosition();
    int xDiff = destination.getX() - piecePos.getX();
    int yDiff = destination.getY() - piecePos.getY();
    Position diffPos = new Position(xDiff, yDiff);

    for (Direction direction : KNIGHT_DIRECTIONS) {
      if (direction.getPosition().equals(diffPos)) {
        return direction;
      }
    }

    return null;
  }

  private Direction directionOfBishopMove(Piece piece, Position destination) {
    Position piecePos = piece.getPosition();
    double slope = getSlope(piecePos, destination);

    if (slope == -1) {
      if (pointBeforeOnXAxis(piecePos, destination)) {
        return Direction.NORTH_EAST;
      } else {
        return Direction.SOUTH_WEST;
      }
    }

    if (slope == 1) {
      if (pointBeforeOnXAxis(piecePos, destination)) {
        return Direction.SOUTH_EAST;
      } else {
        return Direction.NORTH_WEST;
      }
    }

    return null;
  }

  private Direction directionOfQueenMove(Piece piece, Position destination) {
    Direction direction = directionOfRookMove(piece, destination);
    if (direction == null) {
      direction = directionOfBishopMove(piece, destination);
    }
    return direction;
  }

  private Direction directionOfKingMove(Piece piece, Position destination) {
    Position piecePos = piece.getPosition();
    int xDiff = Math.abs(destination.getX() - piecePos.getX());
    int yDiff = Math.abs(destination.getY() - piecePos.getY());

    if ((xDiff == 0 || xDiff == 1) && (yDiff == 0 || yDiff == 1)) {
      return directionOfQueenMove(piece, destination);
    }
    return null;
  }

  private List<Position> buildPath(Position position, Direction direction, int length) {
    List<Position> path = new ArrayList<>();
    for (int i = 0; i < length; i++) {
      int x = position.getX() + (direction.getPosition().getX() * (i + 1));
      int y = position.getY() + (direction.getPosition().getY() * (i + 1));
      path.add(new Position(x, y));
    }
    return path;
  }

  private boolean pointsAreHorizontal(Position pointA, Position pointB) {
    return pointA.getY() == pointB.getY();
  }

  private boolean pointsAreVertical(Position pointA, Position pointB) {
    return pointA.getX() == pointB.getX();
  }

  private double getSlope(Position pointA, Position pointB) {
    if (pointA.getX() != pointB.getX()) {
      return (double) (pointB.getY() - pointA.getY()) / (pointB.getX() - pointA.getX());
    }
    return Integer.MAX_VALUE;
  }

  private boolean pointBeforeOnXAxis(Position pointA, Position pointB) {
    return pointA.getX() < pointB.getX();
  }

  private boolean pointBeforeOnYAxis(Position pointA, Position pointB) {
    return pointA.getY() < pointB.getY();
  }
}
