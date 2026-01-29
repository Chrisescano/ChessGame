package com.christian.app.game;

import com.christian.app.piece.Type;

public record AlgebraicNotation(Type type, Position start, Position end, int winStatus, boolean isCapture, boolean isCheck, boolean isMate) {

  public boolean isMove() {
    return type != null && end != null;
  }
}
