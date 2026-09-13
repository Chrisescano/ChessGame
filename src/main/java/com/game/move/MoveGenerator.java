package com.game.move;

import com.game.Board;
import com.game.ChessConstants;
import com.game.ChessGame.GameState;
import com.game.Piece;
import com.game.Position;
import com.game.move.Move.Type;
import java.util.ArrayList;
import java.util.List;

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
  private static final List<Position> PAWN_SOUTH_CAPTURE_DIRECTIONS = List.of(new Position(-1, 1),
      new Position(1, 1));
  private static final Position PAWN_NORTH_MOVE_DIRECTION = new Position(0, -1);
  private static final Position PAWN_SOUTH_MOVE_DIRECTION = new Position(0, 1);

  public List<Move> generate(Piece piece, GameState state) {
    final List<Move> moves;
    switch (piece.getType()) {
      case ROOK -> moves = generateSliding(piece, ROOK_DIRECTIONS, state.board());
      case KNIGHT -> moves = generateStep(piece, KNIGHT_DIRECTIONS, state.board());
      case BISHOP -> moves = generateSliding(piece, BISHOP_DIRECTIONS, state.board());
      case QUEEN -> moves = generateSliding(piece, QUEEN_DIRECTIONS, state.board());
      case KING -> moves = generateStep(piece, QUEEN_DIRECTIONS, state.board());
      case PAWN -> moves = generatePawn(piece, state);
      default -> moves = new ArrayList<>();
    }
    return moves;
  }

  private List<Move> generateSliding(Piece piece, List<Position> directions, Board board) {
    List<Move> moves = new ArrayList<>();
    for (Position direction : directions) {
      Move step = generateSteps(piece, direction, board, ChessConstants.BOARD_WIDTH);
      addMoveSafely(moves, step);
    }
    return moves;
  }

  private List<Move> generateStep(Piece piece, List<Position> directions, Board board) {
    List<Move> moves = new ArrayList<>();
    for (Position direction : directions) {
      Move step = generateSteps(piece, direction, board, 1);
      addMoveSafely(moves, step);
    }
    return moves;
  }

  private List<Move> generatePawn(Piece pawn, GameState state) {
    final List<Move> moves = new ArrayList<>();

    // forward move and double jump
    Position walkDirection = pawn.isWhite() ? PAWN_NORTH_MOVE_DIRECTION : PAWN_SOUTH_MOVE_DIRECTION;
    int rank = pawn.getPosition().getRank();
    Move walkMove;
    if (!pawn.isMoved() && (rank == 1 || rank == 6)) {
      walkMove = generatePawnSteps(pawn, walkDirection, state.board(), null, true, false);
      walkMove.setType(Type.DOUBLE_JUMP);
    } else {
      walkMove = generatePawnSteps(pawn, walkDirection, state.board(), null, false, false);
    }
    addMoveSafely(moves, walkMove);

    // sideways capture
    List<Position> captureDirections = pawn.isWhite() ? PAWN_NORTH_CAPTURE_DIRECTIONS : PAWN_SOUTH_CAPTURE_DIRECTIONS;
    Position enPassant = state.previousMove() == null ? null : state.previousMove().move().translate(0, pawn.isWhite() ? -1 : 1);
    for (Position captureDirection : captureDirections) {
      Move captureMove = generatePawnSteps(pawn, captureDirection, state.board(), enPassant, false, true);
      if (isEnPassant(pawn, captureMove.move(), state)) {
        captureMove.setType(Type.EN_PASSANT);
      }
      addMoveSafely(moves, captureMove);
    }
    return moves;
  }

  private Move generateSteps(Piece piece, Position direction, Board board, int steps) {
    final Move move = new Move(piece.getType());
    Position current = piece.getPosition();
    for (int currentStep = 0; currentStep < steps; currentStep++) {
      current = current.translate(direction.getFile(), direction.getRank());
      if (!board.isInside(current)) {
        break;
      }
      Piece occupant = board.get(current);
      if (occupant == null) {
        move.add(current, Type.MOVE);
        continue;
      }
      if (piece.isWhite() != occupant.isWhite()) {
        move.add(current, Type.CAPTURE);
      }
      break;
    }
    return move;
  }

  private Move generatePawnSteps(Piece piece, Position direction, Board board, Position enPassant, boolean isDoubleJump, boolean isSidewaysMove) {
    final Move move = new Move(piece.getType());
    Position current = piece.getPosition();
    for (int currentStep = 0; currentStep < (isDoubleJump ? 2 : 1); currentStep++) {
      current = current.translate(direction.getFile(), direction.getRank());
      if (!board.isInside(current)) {
        break;
      }
      Piece occupant = board.get(current);
      if (occupant == null) {
        if (enPassant != null && enPassant.equals(current)) {
          move.add(current, Type.EN_PASSANT);
          continue;
        }
        if (isSidewaysMove) {
          break;
        }
        move.add(current, Type.MOVE);
        continue;
      }
      if (piece.isWhite() != occupant.isWhite()) {
        move.add(current, Type.CAPTURE);
      }
      break;
    }
    return move;
  }

  private boolean isEnPassant(Piece piece, Position enPassantTile, GameState state) {
    return piece.getType() == Piece.Type.PAWN && state.previousMove() != null
        && state.previousMove().getType() == Type.DOUBLE_JUMP
        && !state.previousMove().getMoves().isEmpty()
        && state.previousMove().getMoves().contains(enPassantTile);
  }

  private void addMoveSafely(List<Move> moves, Move move) {
    if (move != null && move.count() > 0) {
      moves.add(move);
    }
  }
}
