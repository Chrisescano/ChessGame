package com.christian.app.piece;

import com.christian.app.game.util.Direction;
import com.christian.app.game.util.GameConstants;
import com.christian.app.game.util.GameUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Piece {

  protected Map<Direction, List<Position>> moves = new HashMap<>();
  protected final Type type;
  protected final Position position;
  protected final boolean isWhite;
  protected final char symbol;

  public Piece(Type type, Position position, boolean isWhite, char symbol) {
    this.type = type;
    this.position = position;
    this.isWhite = isWhite;
    this.symbol = symbol;
  }

  // Methods

  public static Piece create(char symbol, int x, int y) {
    if (GameUtil.isInsideRange(x, 0, GameConstants.BOARD_WIDTH) && GameUtil.isInsideRange(y, 0, GameConstants.BOARD_HEIGHT) ) {
      return switch (symbol) {
        case GameConstants.WHITE_PAWN_SYMBOL, GameConstants.BLACK_PAWN_SYMBOL -> Pawn.create(symbol, x, y);
        case GameConstants.WHITE_ROOK_SYMBOL, GameConstants.BLACK_ROOK_SYMBOL -> Rook.create(symbol, x, y);
        case GameConstants.WHITE_KNIGHT_SYMBOL, GameConstants.BLACK_KNIGHT_SYMBOL -> Knight.create(symbol, x, y);
        case GameConstants.WHITE_BISHOP_SYMBOL, GameConstants.BLACK_BISHOP_SYMBOL -> Bishop.create(symbol, x, y);
        case GameConstants.WHITE_QUEEN_SYMBOL, GameConstants.BLACK_QUEEN_SYMBOL -> Queen.create(symbol, x, y);
        case GameConstants.WHITE_KING_SYMBOL, GameConstants.BLACK_KING_SYMBOL -> King.create(symbol, x, y);
        default -> null;
      };
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

  protected static List<Position> buildPath(Position start, Direction direction, int steps) {
    List<Position> path = new ArrayList<>();
    for (int i = 1; i <= steps; i++) {
      int stepX = start.getX() + (direction.getPosition().getX() * i);
      int stepY = start.getY() + (direction.getPosition().getY() * i);
      path.add(new Position(stepX, stepY));
    }
    return path;
  }

  // Getter/Setter

  public Map<Direction, List<Position>> getMoves() {
    return moves;
  }

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
