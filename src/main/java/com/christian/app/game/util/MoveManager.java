package com.christian.app.game.util;

import com.christian.app.game.Board;
import com.christian.app.piece.Piece;
import com.christian.app.piece.Position;
import java.util.ArrayList;
import java.util.List;

public class MoveManager {

  private final Board board;

  public MoveManager(Board board) {
    this.board = board;
  }

  // Methods

  public boolean isMoveLegal(Piece piece, Position destination) {
    if (piece.getPosition().equals(destination)) {
      return false;
    }

    return switch (piece.getType()) {
      case ROOK -> validateRookMove(piece, destination);
      case BISHOP -> validateBishopMove(piece, destination);
      case QUEEN -> validateQueenMove(piece, destination);
      default -> false;
    };
  }

  // Piece specific validations

  private boolean validateRookMove(Piece piece, Position destination) {
    Position position = piece.getPosition();
    Direction direction = directionOfOrthagonal(position, destination);
    if (direction == null) {
      return false;
    }

    List<Position> path = buildPath(position, destination, direction);
    if (path == null) {
      return false;
    }

    return validatePath(piece, path);
  }

  private boolean validateBishopMove(Piece piece, Position destination) {
    Position position = piece.getPosition();
    Direction direction = directionOfDiagonal(position, destination);
    if (direction == null) {
      return false;
    }

    List<Position> path = buildPath(position, destination, direction);
    if (path == null) {
      return false;
    }

    return validatePath(piece, path);
  }

  private boolean validateQueenMove(Piece piece, Position destination) {
    Position position = piece.getPosition();
    Direction direction = directionOfOrthagonal(position, destination);
    if (direction == null) {
      direction = directionOfDiagonal(position, destination);
      if (direction == null) {
        return false;
      }
    }

    List<Position> path = buildPath(position, destination, direction);
    if (path == null) {
      return false;
    }

    return validatePath(piece, path);
  }

  private Direction directionOfOrthagonal(Position position, Position destination) {
    if (isPositionsHorizontal(position, destination)) {
      if (isGreaterThanOnX(position, destination)) {
        return Direction.WEST;
      } else {
        return Direction.EAST;
      }
    }

    if (isPositionsVertical(position, destination)) {
      if (isGreaterThanOnY(position, destination)) {
        return Direction.NORTH;
      } else {
        return Direction.SOUTH;
      }
    }

    return null;
  }

  private Direction directionOfDiagonal(Position position, Position destination) {
    if (isPositionsDiagonal(position, destination)) {
      boolean greaterOnX = isGreaterThanOnX(position, destination);
      boolean greaterOnY = isGreaterThanOnY(position, destination);
      if (greaterOnX) {
        if (greaterOnY) {
          return Direction.NORTH_WEST;
        } else {
          return Direction.SOUTH_WEST;
        }
      } else {
        if (greaterOnY) {
          return Direction.NORTH_EAST;
        } else {
          return Direction.SOUTH_EAST;
        }
      }
    }

    return null;
  }

  private List<Position> buildPath(Position position, Position destination, Direction direction) {
    Position offset = direction.getPosition();
    List<Position> path = new ArrayList<>();
    int depth = 1;
    int maxDepth = 8;
    while (depth <= maxDepth) {
      int stepX = position.getX() + (offset.getX() * depth);
      int stepY = position.getY() + (offset.getY() * depth);
      Position step = new Position(stepX, stepY);
      path.add(step);
      if (step.equals(destination)) {
        break;
      } else {
        depth++;
      }
    }

    return depth >= maxDepth ? null : path;
  }

  private boolean validatePath(Piece piece, List<Position> path) {
    for (int i = 0; i < path.size() - 1; i++) {
      if (board.getTile(path.get(i)) != null) {
        return false;
      }
    }

    Piece lastTile = board.getTile(path.getLast());
    return lastTile == null || isEnemies(piece, lastTile);
  }

  // Utility Methods

  private boolean isPositionsHorizontal(Position posA, Position posB) {
    return posA.getY() == posB.getY();
  }

  private boolean isPositionsVertical(Position posA, Position posB) {
    return posA.getX() == posB.getX();
  }

  private boolean isPositionsDiagonal(Position posA, Position posB) {
    int xDiff = Math.abs(posA.getX() - posB.getX());
    int yDiff = Math.abs(posA.getY() - posB.getY());
    return xDiff == yDiff;
  }

  private boolean isGreaterThanOnX(Position posA, Position posB) {
    return posA.getX() > posB.getX();
  }

  private boolean isGreaterThanOnY(Position posA, Position posB) {
    return posA.getY() > posB.getY();
  }

  private boolean isEnemies(Piece pieceA, Piece pieceB) {
    return pieceA.isWhite() != pieceB.isWhite();
  }
}
