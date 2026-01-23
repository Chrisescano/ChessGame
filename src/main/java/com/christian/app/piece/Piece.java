package com.christian.app.piece;

import com.christian.app.game.GameUtil;
import com.christian.app.game.Position;

public class Piece {

  private Type type;
  private Position position;
  private char symbol;
  private boolean isWhite;

  private Piece(Type type, Position position, char symbol, boolean isWhite) {
    this.type = type;
    this.position = position;
    this.symbol = symbol;
    this.isWhite = isWhite;
  }

  public static Piece create(char symbol, int x, int y) {
    Type type = Type.toType(symbol);

    if (type == null || !GameUtil.isInsideBoard(x, y)) {
      return null;
    }

    boolean isWhite = Character.isUpperCase(symbol);
    return new Piece(type, new Position(x, y), symbol, isWhite);
  }
}
