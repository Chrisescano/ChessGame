package com.christian.app.piece;

import com.christian.app.game.util.GameConstants;

public class Piece {

  private final Type type;
  private final Position position;
  private final boolean isWhite;
  private final char symbol;

  public Piece(Type type, Position position, boolean isWhite, char symbol) {
    this.type = type;
    this.position = position;
    this.isWhite = isWhite;
    this.symbol = symbol;
  }

  // Methods

  public static Piece create(char symbol, int x, int y) {
    Type type = switch (symbol) {
      case GameConstants.WHITE_PAWN_SYMBOL, GameConstants.BLACK_PAWN_SYMBOL -> Type.PAWN;
      case GameConstants.WHITE_ROOK_SYMBOL, GameConstants.BLACK_ROOK_SYMBOL -> Type.ROOK;
      case GameConstants.WHITE_KNIGHT_SYMBOL, GameConstants.BLACK_KNIGHT_SYMBOL -> Type.KNIGHT;
      case GameConstants.WHITE_BISHOP_SYMBOL, GameConstants.BLACK_BISHOP_SYMBOL -> Type.BISHOP;
      case GameConstants.WHITE_QUEEN_SYMBOL, GameConstants.BLACK_QUEEN_SYMBOL -> Type.QUEEN;
      case GameConstants.WHITE_KING_SYMBOL, GameConstants.BLACK_KING_SYMBOL -> Type.KING;
      default -> null;
    };

    if (type != null) {
      return new Piece(type, new Position(x, y), Character.isUpperCase(symbol), symbol);
    }
    return null;
  }

  public void updatePos(int x, int y) {
    position.setX(x);
    position.setY(y);
  }

  @Override
  public String toString() {
    return "Piece{" +
        "type=" + type +
        ", position=" + position +
        ", isWhite=" + isWhite +
        '}';
  }

  // Getter/Setter

  public Type getType() {
    return type;
  }

  public Position getPosition() {
    return position;
  }

  public boolean isWhite() {
    return isWhite;
  }

  public char getSymbol() {
    return symbol;
  }
}
