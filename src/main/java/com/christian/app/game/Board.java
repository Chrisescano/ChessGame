package com.christian.app.game;

import com.christian.app.piece.Piece;
import java.util.Arrays;

public class Board {

  private final Piece[] board = new Piece[GameConstants.BOARD_TOTAL_SQUARES];

  public void clear() {
    Arrays.fill(board, null);
  }

  public void place(Piece piece) {
    if (piece != null && GameUtil.isInsideBoard(piece.getPosition()) && !isOccupied(piece.getPosition())) {
      board[GameUtil.toIndex(piece.getPosition())] = piece;
    }
  }

  public void remove(Piece piece) {
    if (piece != null && GameUtil.isInsideBoard(piece.getPosition()) && isOccupied(piece.getPosition())) {
      board[GameUtil.toIndex(piece.getPosition())] = null;
    }
  }

  public boolean isOccupied(int x, int y) {
    if (GameUtil.isInsideBoard(x, y)) {
      return board[GameUtil.toIndex(x, y)] != null;
    }
    return false;
  }

  public boolean isOccupied(Position position) {
    return isOccupied(position.getX(), position.getY());
  }

}
