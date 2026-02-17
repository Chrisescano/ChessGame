package com.christian.app.game;

import com.christian.app.parser.AlgebraicNotationParser;
import com.christian.app.piece.Piece;
import com.christian.app.piece.Type;
import com.christian.app.util.AlgebraicNotation;
import com.christian.app.util.MoveRecord;
import com.christian.app.util.MoveStatus;
import com.christian.app.util.Position;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MoveManager {

  private final Board board;
  public final Boolean activeColor;
  private final AlgebraicNotationParser parser = new AlgebraicNotationParser();

  private MoveRecord moveRecord = new MoveRecord();
  private Position enPassantTile;
  private int enPassantTTL;

  public MoveManager(final Board board, final Boolean activeColor) {
    this.board = board;
    this.activeColor = activeColor;
  }

  public boolean isLegalMove(final String move) {
    AlgebraicNotation algNot = parser.parse(move);
    if (algNot == null) {
      moveRecord = new MoveRecord(MoveStatus.UNPARSEABLE_MOVE, move, null, null,
          Character.MIN_VALUE);
      return false;
    }

    List<Piece> results = searchBoard(activeColor, algNot);
    if (results.isEmpty()) {
      moveRecord = new MoveRecord(MoveStatus.NO_PIECES_FOUND, move, null,
          algNot.getEndPosition(), Character.MIN_VALUE);
      return false;
    }

    results = results.stream().filter(piece -> isLegalPieceMove(piece, algNot.getEndPosition()))
        .toList();
    if (results.isEmpty()) {
      moveRecord = new MoveRecord(MoveStatus.NO_PIECES_FOUND, move, null,
          algNot.getEndPosition(), Character.MIN_VALUE);
      return false;
    } else if (results.size() > 1) {
      Set<Integer> fileSet = results.stream().map(piece -> piece.getPosition().getFile())
          .collect(
              Collectors.toSet());
      boolean printPieceFiles = fileSet.size() == results.size();
      String log = String.format(
          "for %s did you mean %s", move, results.stream()
              .map(piece -> printPieceFiles ? piece.toSymbolFileString()
                  : piece.toSymbolRankString())
              .map(prettyString -> prettyString + GameUtil.toChessNotation(
                  algNot.getEndPosition()))
              .collect(Collectors.joining(" or "))
      );
      moveRecord = new MoveRecord(MoveStatus.AMBIGUOUS_MOVE, log, null,
          algNot.getEndPosition(), Character.MIN_VALUE);
      return false;
    }

    moveRecord = new MoveRecord(MoveStatus.LEGAL_MOVE, move, results.getFirst(),
        algNot.getEndPosition(), Character.MIN_VALUE);
    return true;
  }

  private boolean isLegalPieceMove(Piece piece, Position move) {
    if (moveRecord.isMoveValid()) {
      if (enPassantTTL > 0) {
        enPassantTTL--;
      } else {
        enPassantTile = null;
      }
    }

    Direction direction = piece.directionOf(move);
    List<Position> path = piece.getPath(direction);
    path = path.subList(0, path.indexOf(move) + 1);

    boolean isMoveLegal = false;
    for (int i = 0; i < path.size(); i++) {
      Position tile = path.get(i);
      if (GameUtil.isInsideBoard(tile)) {
        if (piece.getType() == Type.PAWN) {
          isMoveLegal = isLegalPawnMove(piece, tile, direction, i);
          continue;
        }

        if (piece.getType() == Type.KING) {
          isMoveLegal = isLegalKingMove(piece, tile, direction);
          continue;
        }

        if (board.isOccupied(tile)) {
          Piece tilePiece = board.getPiece(tile);
          isMoveLegal = piece.isWhite() ^ tilePiece.isWhite();
        } else {
          isMoveLegal = true;
        }
      } else {
        isMoveLegal = false;
        break;
      }
    }

    return isMoveLegal;
  }

  private List<Piece> searchBoard(final boolean activeColor,
      final AlgebraicNotation algebraicNotation) {
    return board.search(
        activeColor,
        algebraicNotation.getType(),
        algebraicNotation.getStartFile(),
        algebraicNotation.getStartRank(),
        algebraicNotation.getEndPosition()
    );
  }

  private boolean isLegalPawnMove(Piece piece, Position move, Direction direction, int index) {
    if (direction == Direction.NORTH || direction == Direction.SOUTH) {
      if (index == 1 && !piece.isMoved() && board.isEmpty(move)) {
        enPassantTile = move;
        enPassantTTL = 1;
        return true;
      } else if (index == 0) {
        return board.isEmpty(move);
      }
    } else { //capturing
      if (board.isOccupied(move)) {
        return piece.isWhite() ^ board.getPiece(move).isWhite();
      } else if (enPassantTile != null && enPassantTile.equals(move)) {
        enPassantTile = null;
        return true;
      }
    }
    return false;
  }

  private boolean isLegalKingMove(Piece piece, Position move, Direction direction) {
    return false;
  }

  public MoveRecord getMoveRecord() {
    return moveRecord;
  }
}
