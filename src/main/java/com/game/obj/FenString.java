package com.game.obj;

import com.game.piece.Piece;
import com.game.piece.Position;
import java.util.List;

public class FenString {

  private List<Piece> pieces;
  private boolean activeColor;
  private boolean[] castlingRights;
  private Position enPassantTarget;
  private int halfMoveClock;
  private int fullMoveCounter;

  public FenString(List<Piece> pieces, boolean activeColor, boolean[] castlingRights,
      Position enPassantTarget, int halfMoveClock, int fullMoveCounter) {
    this.pieces = pieces;
    this.activeColor = activeColor;
    this.castlingRights = castlingRights;
    this.enPassantTarget = enPassantTarget;
    this.halfMoveClock = halfMoveClock;
    this.fullMoveCounter = fullMoveCounter;
  }

  public List<Piece> getPieces() {
    return pieces;
  }

  public void setPieces(List<Piece> pieces) {
    this.pieces = pieces;
  }

  public boolean getActiveColor() {
    return activeColor;
  }

  public void setActiveColor(boolean activeColor) {
    this.activeColor = activeColor;
  }

  public boolean[] getCastlingRights() {
    return castlingRights;
  }

  public void setCastlingRights(boolean[] castlingRights) {
    this.castlingRights = castlingRights;
  }

  public Position getEnPassantTarget() {
    return enPassantTarget;
  }

  public void setEnPassantTarget(Position enPassantTarget) {
    this.enPassantTarget = enPassantTarget;
  }

  public int getHalfMoveClock() {
    return halfMoveClock;
  }

  public void setHalfMoveClock(int halfMoveClock) {
    this.halfMoveClock = halfMoveClock;
  }

  public int getFullMoveCounter() {
    return fullMoveCounter;
  }

  public void setFullMoveCounter(int fullMoveCounter) {
    this.fullMoveCounter = fullMoveCounter;
  }
}
