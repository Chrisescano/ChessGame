package com.christian.app.game;

public class GameUtil {

  public static boolean isInsideFile(final int value) {
    return isBetween(value, 0, GameConstants.BOARD_WIDTH - 1);
  }

  public static boolean isInsideRank(final int value) {
    return isBetween(value, 0, GameConstants.BOARD_HEIGHT - 1);
  }

  public static boolean isInsideBoard(final int file, final int rank) {
    return isInsideFile(file) && isInsideRank(rank);
  }

  /* Helper Methods */

  private static boolean isBetween(final int value, final int min, final int max) {
    return value >= min && value <= max;
  }

}
