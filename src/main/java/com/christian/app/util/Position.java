package com.christian.app.util;

import com.christian.app.game.GameUtil;
import java.util.Objects;

public class Position {

  private int file;
  private int rank;

  public Position(Position position) {
    this(position.getFile(), position.getRank());
  }

  public Position(final int file, final int rank) {
    this.file = file;
    this.rank = rank;
  }

  public static Position create(String chessNotation) {
    if (chessNotation != null && chessNotation.length() == 2) {
      int file = GameUtil.toFileIndex(chessNotation.charAt(0));
      int rank = GameUtil.toRankIndex(chessNotation.charAt(1));
      return new Position(file, rank);
    }
    return null;
  }

  public Position copy() {
    return new Position(this);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Position position)) {
      return false;
    }
    return file == position.file && rank == position.rank;
  }

  @Override
  public int hashCode() {
    return Objects.hash(file, rank);
  }

  @Override
  public String toString() {
    return "Position{" +
        "x=" + file +
        ", y=" + rank +
        '}';
  }

  public int getFile() {
    return file;
  }

  public void setFile(final int file) {
    this.file = file;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(final int rank) {
    this.rank = rank;
  }
}
