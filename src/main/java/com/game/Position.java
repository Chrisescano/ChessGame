package com.game;

import java.util.Objects;

public class Position {

  private int file;
  private int rank;

  public Position(int x, int y) {
    this.file = x;
    this.rank = y;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Position position)) {
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

  public void setFile(int file) {
    this.file = file;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }
}
