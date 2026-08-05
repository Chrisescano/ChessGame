package com.game.piece;

public class Piece {

  private Type type;
  private int x;
  private int y;
  private boolean isWhite;

  public enum Type {
    ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN
  }

  public Piece(Type type, int x, int y, boolean isWhite) {
    this.type = type;
    this.x = x;
    this.y = y;
    this.isWhite = isWhite;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public boolean isWhite() {
    return isWhite;
  }

  public void setWhite(boolean white) {
    isWhite = white;
  }
}
