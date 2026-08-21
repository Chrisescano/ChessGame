package com.game.algebraicnotation;

import com.game.Piece;
import com.game.Position;
import java.util.Objects;

public class AlgebraicNotation {

  private Piece.Type type;
  private Position startPosition;
  private boolean isCapture;
  private Position endPosition;
  private boolean isMate;
  private boolean isCheck;
  private Type status;

  public enum Type {
    MOVE, WHITE_WIN, BLACK_WIN, DRAW
  }

  public AlgebraicNotation(Piece.Type type, Position startPosition, boolean isCapture, Position endPosition, boolean isMate,
      boolean isCheck, Type status) {
    this.type = type;
    this.startPosition = startPosition;
    this.isCapture = isCapture;
    this.endPosition = endPosition;
    this.isMate = isMate;
    this.isCheck = isCheck;
    this.status = status;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof AlgebraicNotation that)) {
      return false;
    }
    return isCapture == that.isCapture && isMate == that.isMate && isCheck == that.isCheck
        && type == that.type && Objects.equals(startPosition, that.startPosition)
        && Objects.equals(endPosition, that.endPosition) && status == that.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, startPosition, isCapture, endPosition, isMate, isCheck, status);
  }

  @Override
  public String toString() {
    return "AlgebraicNotation{" +
        "type=" + type +
        ", startPosition=" + startPosition +
        ", isCapture=" + isCapture +
        ", endPosition=" + endPosition +
        ", isMate=" + isMate +
        ", isCheck=" + isCheck +
        ", status=" + status +
        '}';
  }

  public Piece.Type getPieceType() {
    return type;
  }

  public void setType(Piece.Type type) {
    this.type = type;
  }

  public Position getStartPosition() {
    return startPosition;
  }

  public void setStartPosition(Position startPosition) {
    this.startPosition = startPosition;
  }

  public boolean isCapture() {
    return isCapture;
  }

  public void setCapture(boolean capture) {
    isCapture = capture;
  }

  public Position getEndPosition() {
    return endPosition;
  }

  public void setEndPosition(Position endPosition) {
    this.endPosition = endPosition;
  }

  public boolean isMate() {
    return isMate;
  }

  public void setMate(boolean mate) {
    isMate = mate;
  }

  public boolean isCheck() {
    return isCheck;
  }

  public void setCheck(boolean check) {
    isCheck = check;
  }

  public Type getType() {
    return status;
  }

  public void setType(Type status) {
    this.status = status;
  }
}
