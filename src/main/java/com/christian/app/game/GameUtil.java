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

  public static boolean isInsideBoard(int index) {
    return isInsideRange(index, 0, GameConstants.BOARD_TOTAL_SQUARES - 1);
  }

  public static int toIndex(int x, int y) {
    return (y * GameConstants.BOARD_HEIGHT) + x;
  }

  public static int toIndex(Position position) {
    return toIndex(position.getX(), position.getY());
  }

}
