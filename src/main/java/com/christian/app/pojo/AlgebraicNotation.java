package com.christian.app.pojo;

import com.christian.app.piece.Type;
import com.christian.app.util.Position;

public class AlgebraicNotation {

  private final Type type;
  private final int startFile;
  private final int startRank;
  private final int endFile;
  private final int endRank;
  private final boolean isCapture;
  private final boolean isCheck;
  private final boolean isMate;

  public AlgebraicNotation(Type type, int startFile, int startRank, int endFile, int endRank,
      boolean isCapture, boolean isCheck, boolean isMate) {
    this.type = type;
    this.startFile = startFile;
    this.startRank = startRank;
    this.endFile = endFile;
    this.endRank = endRank;
    this.isCapture = isCapture;
    this.isCheck = isCheck;
    this.isMate = isMate;
  }

  @Override
  public String toString() {
    return "AlgebraicNotation{" +
        "type=" + type +
        ", startFile=" + startFile +
        ", startRank=" + startRank +
        ", endFile=" + endFile +
        ", endRank=" + endRank +
        ", isCapture=" + isCapture +
        ", isCheck=" + isCheck +
        ", isMate=" + isMate +
        '}';
  }

  public Position getEndPosition() {
    return new Position(endFile, endRank);
  }

  public Type getType() {
    return type;
  }

  public int getStartFile() {
    return startFile;
  }

  public int getStartRank() {
    return startRank;
  }

  public int getEndFile() {
    return endFile;
  }

  public int getEndRank() {
    return endRank;
  }

  public boolean isCapture() {
    return isCapture;
  }

  public boolean isCheck() {
    return isCheck;
  }

  public boolean isMate() {
    return isMate;
  }
}
