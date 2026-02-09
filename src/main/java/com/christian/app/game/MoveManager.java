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
  private MoveRecord moveRecord;

  public MoveManager(final Board board, final Boolean activeColor) {
    this.board = board;
    this.activeColor = activeColor;
  }

  public boolean isValidMove(final String move) {
    AlgebraicNotation userMove = parser.parse(move);
    if (userMove != null) {
      List<Piece> results = searchBoard(activeColor, userMove);
      if (!results.isEmpty()) {
        results = results.stream().filter(piece -> isLegalMove(piece, userMove.getEndPosition()))
            .toList();
        if (!results.isEmpty()) {
          if (results.size() == 1) {
            moveRecord = new MoveRecord(MoveStatus.LEGAL_MOVE, move, results.getFirst(),
                userMove.getEndPosition(), Character.MIN_VALUE);
            return true;
          } else {
            Set<Integer> fileSet = results.stream().map(piece -> piece.getPosition().getFile())
                .collect(
                    Collectors.toSet());
            boolean printPieceFiles = fileSet.size() == results.size();
            String log = String.format(
                "for %s did you mean %s", move, results.stream()
                    .map(piece -> printPieceFiles ? piece.toSymbolFileString()
                        : piece.toSymbolRankString())
                    .map(prettyString -> prettyString + GameUtil.toChessNotation(
                        userMove.getEndPosition()))
                    .collect(Collectors.joining(" or "))
            );
            moveRecord = new MoveRecord(MoveStatus.AMBIGUOUS_MOVE, log, null,
                userMove.getEndPosition(), Character.MIN_VALUE);
          }
        } else {
          moveRecord = new MoveRecord(MoveStatus.NO_PIECES_FOUND, move, null,
              userMove.getEndPosition(), Character.MIN_VALUE);
        }
      } else {
        moveRecord = new MoveRecord(MoveStatus.NO_PIECES_FOUND, move, null,
            userMove.getEndPosition(), Character.MIN_VALUE);
      }
    } else {
      moveRecord = new MoveRecord(MoveStatus.UNPARSEABLE_MOVE, move, null, null,
          Character.MIN_VALUE);
    }
    return false;
  }

  private boolean isLegalMove(Piece piece, Position move) {
    Direction direction = piece.directionOf(move);
    List<Position> path = piece.getPath(direction);
    path = path.subList(0, path.indexOf(move) + 1);

    boolean isMoveLegal = false;
    for (int i = 0; i < path.size(); i++) {
      Position tile = path.get(i);
      if (GameUtil.isInsideBoard(tile)) {
        if (piece.getType() == Type.PAWN) {
          if ((direction == Direction.NORTH || direction == Direction.SOUTH) && i == 1) {
            if (piece.isMoved()) {
              isMoveLegal = false;
            } else {
              isMoveLegal = board.isEmpty(tile);
            }
            break;
          } else if (direction != Direction.NORTH && direction != Direction.SOUTH) {
            isMoveLegal = isEnemies(board.getSymbol(tile), piece.getSymbol());
            break;
          }
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

  private boolean isEnemies(char symbolA, char symbolB) {
    return (symbolA != GameConstants.EMPTY_TILE)
        && (symbolB != GameConstants.EMPTY_TILE)
        && (Character.isUpperCase(symbolA) ^ Character.isUpperCase(symbolB));
  }

  public MoveRecord getMoveRecord() {
    return moveRecord;
  }
}
