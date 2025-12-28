package com.christian.app.game;

import com.christian.app.piece.Piece;
import com.christian.app.piece.Position;
import java.util.List;

public class Fen {

  private final Board board = new Board();
  boolean activeColor;
  boolean[] castlingRights;
  private final Position enPassant;
  int halfMoveCounter;
  int fullMoveClock;

  public Fen(List<Piece> pieces, boolean activeColor, boolean[] castlingRights, Position enPassant,
      int halfMoveCounter, int fullMoveClock) {
    this.board.addPieces(pieces);
    this.activeColor = activeColor;
    this.castlingRights = castlingRights;
    this.enPassant = enPassant;
    this.halfMoveCounter = halfMoveCounter;
    this.fullMoveClock = fullMoveClock;
  }

  // Methods

  public void flipActiveColor() {
    activeColor = !activeColor;
  }

  public void toggleCastlingSide(int side, boolean enable) {
    if (side == GameConstants.CASTLING_WHITE_QUEEN
    || side == GameConstants.CASTLING_WHITE_KING
    || side == GameConstants.CASTLING_BLACK_QUEEN
    || side == GameConstants.CASTLING_BLACK_KING) {
      castlingRights[side] = enable;
    }
  }

  public void updateEnpassant(int x, int y) {
    enPassant.setX(x);
    enPassant.setY(y);
  }

  public void incrementHalfMoveCounter() {
    halfMoveCounter++;
  }

  public void incrementFullMoveClock() {
    fullMoveClock++;
  }

  // Getter/Setter

  public Board getBoard() {
    return board;
  }

  public boolean isActiveColor() {
    return activeColor;
  }

  public boolean[] getCastlingRights() {
    return castlingRights;
  }

  public Position getEnPassant() {
    return enPassant;
  }

  public int getHalfMoveCounter() {
    return halfMoveCounter;
  }

  public int getFullMoveClock() {
    return fullMoveClock;
  }
}
