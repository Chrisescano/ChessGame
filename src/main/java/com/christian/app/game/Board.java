package com.christian.app.game;

import com.christian.app.piece.Piece;
import com.christian.app.piece.Position;
import java.util.List;

public class Board {

  private final Piece[] board;

  public Board() {
    board = new Piece[GameConstants.BOARD_TILE_COUNT];
  }

  // Methods

  public void addPieces(List<Piece> pieces) {
    pieces.forEach(this::addPiece);
  }

  public boolean addPiece(Piece piece) {
    int index = GameUtil.toIndex(piece.getPosition());
    if (GameUtil.isInsideRange(index, 0, GameConstants.BOARD_TILE_COUNT)) {
      board[index] = piece;
      return true;
    }
    return false;
  }

  public Piece removePiece(Position position) {
    int index = GameUtil.toIndex(position);
    if (GameUtil.isInsideRange(index, 0, GameConstants.BOARD_TILE_COUNT)) {
      return board[index];
    }
    return null;
  }

  // Getter/Setter

  public Piece getPiece(Position position) {
    return getPiece(position.getX(), position.getY());
  }

  public Piece getPiece(int x, int y) {
    int index = GameUtil.toIndex(x, y);
    if (GameUtil.isInsideRange(index, 0, GameConstants.BOARD_TILE_COUNT)) {
      return board[index];
    }
    return null;
  }
}
