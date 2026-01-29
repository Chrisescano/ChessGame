package com.christian.app.game;

public class GameUtil {

  public static boolean isInsideRange(int value, int min, int max) {
    return value >= min && value <= max;
  }

  public static boolean isInsideBoardRank(int value) {
    return isInsideRange(value, 0, GameConstants.BOARD_HEIGHT - 1);
  }

  public static boolean isInsideBoardFile(int value) {
    return isInsideRange(value, 0, GameConstants.BOARD_WIDTH - 1);
  }

  public static boolean isInsideBoard(int x, int y) {
    return isInsideBoardFile(x) && isInsideBoardRank(y);
  }

  public static boolean isInsideBoard(Position position) {
    return isInsideBoard(position.getX(), position.getY());
  }

  public static int toIndex(int x, int y) {
    return (y * GameConstants.BOARD_HEIGHT) + x;
  }

  public static int toIndex(Position position) {
    return toIndex(position.getX(), position.getY());
  }

  public static int toFileIndex(char file) {
    if (file >= 'a' && file <= 'h') {
      return file - 'a';
    }
    return -1;
  }

  public static int toFileIndex(String file) {
    if (file != null && !file.isEmpty()) {
      return toFileIndex(file.charAt(0));
    }
    return -1;
  }

  public static int toRankIndex(char rank) {
    if (rank >= '1' && rank <= '8') {
      return '8' - rank;
    }
    return -1;
  }

  public static int toRankIndex(String rank)  {
    if (rank != null && !rank.isEmpty()) {
      return toRankIndex(rank.charAt(0));
    }
    return -1;
  }

  public static Position toPosition(String value) {
    if (value != null && value.length() == 2) {
      int file = toFileIndex(value.charAt(0));
      int rank = toRankIndex(value.charAt(1));
      return new Position(file, rank);
    }
    return new Position(-1, -1);
  }

}
