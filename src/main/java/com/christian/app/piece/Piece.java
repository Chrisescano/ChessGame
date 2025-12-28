package com.christian.app.piece;

import com.christian.app.game.GameConstants;

public class Piece {

  private final Type type;
  private final Position position;
  private final boolean isWhite;
  private final char symbol;

  public Piece(Type type, boolean isWhite) {
    this(type, new Position(), isWhite);
  }

  public Piece(Type type, Position position, boolean isWhite) {
    this.type = type;
    this.position = position;
    this.isWhite = isWhite;
    symbol = switch (type) {
      case PAWN -> isWhite ? GameConstants.WHITE_PAWN_SYMBOL : GameConstants.BLACK_PAWN_SYMBOL;
      case ROOK -> isWhite ? GameConstants.WHITE_ROOK_SYMBOL : GameConstants.BLACK_ROOK_SYMBOL;
      case KNIGHT -> isWhite ? GameConstants.WHITE_KNIGHT_SYMBOL : GameConstants.BLACK_KNIGHT_SYMBOL;
      case BISHOP -> isWhite ? GameConstants.WHITE_BISHOP_SYMBOL : GameConstants.BLACK_BISHOP_SYMBOL;
      case KING -> isWhite ? GameConstants.WHITE_KING_SYMBOL : GameConstants.BLACK_KING_SYMBOL;
      case QUEEN -> isWhite ? GameConstants.WHITE_QUEEN_SYMBOL : GameConstants.BLACK_QUEEN_SYMBOL;
    };
  }

  // Methods

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
