package com.game;

public class Piece {

  private Type type;
  private Position position;
  private char symbol;
  private boolean isWhite;



  public enum Type {
    ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN
  }

  public Piece(char symbol, int x, int y) {
    this.type = ChessUtils.typeOf(symbol);
    this.position = new Position(x, y);
    this.symbol = symbol;
    this.isWhite = Character.isUpperCase(symbol);
  }

  @Override
  public String toString() {
    return "Piece{" +
        "type=" + type +
        ", position=" + position +
        ", isWhite=" + isWhite +
        '}';
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public char getSymbol() {
    return symbol;
  }

  public void setSymbol(char symbol) {
    this.symbol = symbol;
  }

  public boolean isWhite() {
    return isWhite;
  }

  public void setWhite(boolean white) {
    isWhite = white;
  }
}
