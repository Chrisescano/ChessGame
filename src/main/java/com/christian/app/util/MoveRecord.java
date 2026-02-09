package com.christian.app.util;

import com.christian.app.piece.Piece;

public record MoveRecord(MoveStatus moveStatus, String log, Piece pieceMoved, Position move,
                         char pieceCaptured) {

  public MoveRecord() {
    this(MoveStatus.UNPARSEABLE_MOVE, null, null, null, Character.MIN_VALUE);
  }

  public boolean isMoveValid() {
    return moveStatus == MoveStatus.LEGAL_MOVE;
  }
}
