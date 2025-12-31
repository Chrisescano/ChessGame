package com.christian.app.game;

import com.christian.app.game.util.GameConstants;
import com.christian.app.parser.FenParser;
import com.christian.app.piece.Position;

public class Fen {

  private static final FenParser parser = new FenParser();

  private final Board board;
  boolean activeColor;
  boolean[] castlingRights;
  private final Position enPassant;
  int halfMoveCounter;
  int fullMoveClock;

  public Fen(Board board, boolean activeColor, boolean[] castlingRights, Position enPassant,
      int halfMoveCounter, int fullMoveClock) {
    this.board = board;
    this.activeColor = activeColor;
    this.castlingRights = castlingRights;
    this.enPassant = enPassant;
    this.halfMoveCounter = halfMoveCounter;
    this.fullMoveClock = fullMoveClock;
  }

  // Methods

  public static Fen parse(String fenString) {
    return parser.parseFen(fenString);
  }

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

  @Override
  public String toString() {
    return "Fen{" + parser.toFenString(this) + "}";
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
