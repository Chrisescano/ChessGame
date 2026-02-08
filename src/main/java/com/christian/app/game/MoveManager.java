package com.christian.app.game;

import com.christian.app.parser.AlgebraicNotationParser;
import com.christian.app.piece.Piece;
import com.christian.app.util.AlgebraicNotation;
import com.christian.app.util.Position;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MoveManager {

  private final Board board;
  public final Boolean activeColor;
  private final AlgebraicNotationParser parser = new AlgebraicNotationParser();

  public MoveManager(final Board board, final Boolean activeColor) {
    this.board = board;
    this.activeColor = activeColor;
  }

  public boolean isValidMove(final String move) {
    AlgebraicNotation userMove = parser.parse(move);
    List<Piece> results;

    if (userMove != null) {
      results = searchBoard(activeColor, userMove);
      if (!results.isEmpty()) {
        for (int i = results.size() - 1; i >= 0; i--) {
          if (!isLegalMove(results.get(i), userMove.getEndPosition())) {
            results.remove(i);
          }
        }
      }
    } else {
      results = null;
    }

    if (results != null && !results.isEmpty()) {
      if (results.size() > 1) { //ambiguous move encountered
        Set<Integer> fileSet = results.stream().map(piece -> piece.getPosition().getRank()).collect(
            Collectors.toSet());
        System.out.printf("Did you mean: %s\n",
            results.stream().map(piece -> String.format("%s%s",
                    fileSet.size() == results.size() ? piece.toSymbolFileString() : piece.toSymbolRankString(),
                    GameUtil.toChessNotation(userMove.getEndPosition())))
                .collect(Collectors.joining(" or ")));
        return false;
      }
      return true;
    }

    return false;
  }

  private boolean isLegalMove(Piece piece, Position move) {
    Direction direction = piece.directionOf(move);
    List<Position> path = piece.getPath(direction);
    path = path.subList(0, path.indexOf(move) + 1);

    boolean isMoveLegal = false;
    for (Position tile : path) {
      if (GameUtil.isInsideBoard(tile)) {
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

}
