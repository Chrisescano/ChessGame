package com.game.move;

import com.game.Board;
import com.game.ChessConstants;
import com.game.Piece;
import com.game.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveGenerator {

  private static final List<Position> ROOK_DIRECTIONS = List.of(new Position(0, -1),
      new Position(1, 0), new Position(0, 1), new Position(-1, 0));
  private static final List<Position> KNIGHT_DIRECTIONS = List.of(new Position(-1, -2),
      new Position(1, -2), new Position(2, -1), new Position(2, 1), new Position(-1, 2),
      new Position(1, 2), new Position(-2, -1), new Position(-2, 1));
  private static final List<Position> BISHOP_DIRECTIONS = List.of(new Position(-1, -1),
      new Position(1, -1), new Position(1, 1), new Position(-1, 1));
  private static final List<Position> QUEEN_DIRECTIONS = List.of(new Position(0, -1),
      new Position(1, 0), new Position(0, 1), new Position(-1, 0), new Position(-1, -1),
      new Position(1, -1), new Position(1, 1), new Position(-1, 1));
  private static final List<Position> PAWN_NORTH_CAPTURE_DIRECTIONS = List.of(new Position(-1, -1),
      new Position(1, -1));
  private static final List<Position> PAWN_SOUTH_CAPTURE_DIRECTIONS = List.of(new Position(1, 1),
      new Position(-1, 1));
  private static final Position PAWN_NORTH_MOVE_DIRECTION = new Position(0, -1);
  private static final Position PAWN_SOUTH_MOVE_DIRECTION = new Position(0, 1);

  public record Results(Set<Position> moves) {
    public int getSize() {
      return moves == null ? 0 : moves.size();
    }
  }

  public Results generate(Piece piece, Board board) {
    final Results results;
    switch (piece.getType()) {
      case ROOK -> results = generateSliding(piece, ROOK_DIRECTIONS, board);
      case KNIGHT -> results = generateStep(piece, KNIGHT_DIRECTIONS, board);
      case BISHOP -> results = generateSliding(piece, BISHOP_DIRECTIONS, board);
      case QUEEN -> results = generateSliding(piece, QUEEN_DIRECTIONS, board);
      case KING -> results = generateStep(piece, QUEEN_DIRECTIONS, board);
      case PAWN -> results = generatePawn(piece, board);
      default -> results = new Results(null);
    }
    return results;
  }

  private Results generateSliding(Piece piece, List<Position> directions, Board board) {
    Set<Position> moves = new HashSet<>();
    for (Position direction : directions) {
      moves.addAll(generateSteps(piece, direction, board, ChessConstants.BOARD_WIDTH, false));
    }
    return new Results(moves);
  }

  private Results generateStep(Piece piece, List<Position> directions, Board board) {
    Set<Position> moves = new HashSet<>();
    for (Position direction : directions) {
      moves.addAll(generateSteps(piece, direction, board, 1, false));
    }
    return new Results(moves);
  }

  private Results generatePawn(Piece pawn, Board board) {
    Position moveDirection = pawn.isWhite() ? PAWN_NORTH_MOVE_DIRECTION : PAWN_SOUTH_MOVE_DIRECTION;
    Set<Position> moves = new HashSet<>(generateSteps(pawn, moveDirection, board, pawn.isMoved() ? 1 : 2, false));
    List<Position> captureDirections = pawn.isWhite() ? PAWN_NORTH_CAPTURE_DIRECTIONS : PAWN_SOUTH_CAPTURE_DIRECTIONS;
    for (Position captureDirection : captureDirections) {
      moves.addAll(generateSteps(pawn, captureDirection, board, 1, true));
    }
    return new Results(moves);
  }

  private Set<Position> generateSteps(Piece piece, Position direction, Board board, int steps, boolean captureOnly) {
    Set<Position> moves = new HashSet<>();
    Position current = piece.getPosition();
    for (int currentStep = 0; currentStep < steps; currentStep++) {
      current = current.translate(direction.getFile(), direction.getRank());
      if (!board.isInside(current)) {
        break;
      }
      Piece occupant = board.get(current);
      if (occupant == null) {
        if (captureOnly) {
          break;
        }
        moves.add(current);
        continue;
      }
      if (piece.isWhite() != occupant.isWhite()) {
        moves.add(current);
      }
      break;
    }
    return moves;
  }
}
