package com.game;

public class Piece {

  private Type type;
  private Position position;
  private char symbol;
  private boolean isWhite;

  public enum Type {
    ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN;

    public static Type of(char token) {
      Type type;
      switch (token) {
        case ChessConstants.WHITE_ROOK, ChessConstants.BLACK_ROOK -> type = Type.ROOK;
        case ChessConstants.WHITE_KNIGHT, ChessConstants.BLACK_KNIGHT ->  type = Type.KNIGHT;
        case ChessConstants.WHITE_BISHOP, ChessConstants.BLACK_BISHOP -> type = Type.BISHOP;
        case ChessConstants.WHITE_QUEEN, ChessConstants.BLACK_QUEEN -> type = Type.QUEEN;
        case ChessConstants.WHITE_KING, ChessConstants.BLACK_KING -> type = Type.KING;
        case ChessConstants.WHITE_PAWN, ChessConstants.BLACK_PAWN -> type = Type.PAWN;
        default -> type = null;
      }
      return type;
    }
  }

  public Piece(Type type, int x, int y, char symbol, boolean isWhite) {
    this.type = type;
    this.position = new Position(x, y);
    this.symbol = symbol;
    this.isWhite = isWhite;
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
