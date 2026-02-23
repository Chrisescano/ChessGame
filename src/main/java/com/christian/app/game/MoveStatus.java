package com.christian.app.game;

public enum MoveStatus {
  UNPARSEABLE_MOVE(false),
  NO_PIECES_FOUND(false),
  NO_LEGAL_PIECES_FOUND(false),
  AMBIGUOUS_MOVE(false),
  ILLEGAL_MOVE(false),
  LEGAL_MOVE(true),
  LEGAL_CAPTURE(true),
  LEGAL_DOUBLE_JUMP(true),
  LEGAL_EN_PASSANT(true),
  LEGAL_QUEEN_SIDE_CASTLE(true),
  LEGAL_KING_SIDE_CASTLE(true);

  private final boolean isLegalMove;

  MoveStatus(final boolean isLegalMove) {
    this.isLegalMove = isLegalMove;
  }

  public boolean isLegalMove() {
    return isLegalMove;
  }
}
