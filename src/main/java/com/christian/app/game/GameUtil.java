package com.christian.app.game;

public class GameUtil {

  public static boolean isInsideRange(int value, int min, int max) {
    return value >= min && value <= max;
  }

  public static boolean isInsideBoardRank(int value) {
    return isInsideRange(value, 0, GameConstants.BOARD_HEIGHT);
  }

  public static boolean isInsideBoardFile(int value) {
    return isInsideRange(value, 0, GameConstants.BOARD_WIDTH);
  }

  public static boolean isInsideBoard(int x, int y) {
    return isInsideBoardFile(x) && isInsideBoardRank(y);
  }

}
