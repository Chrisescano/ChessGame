package com.christian.app.game;

import com.christian.app.util.Position;

public class GameUtil {

  //bounds checks

  public static boolean isInsideFile(final int value) {
    return isBetween(value, 0, GameConstants.BOARD_WIDTH - 1);
  }

  public static boolean isInsideRank(final int value) {
    return isBetween(value, 0, GameConstants.BOARD_HEIGHT - 1);
  }

  public static boolean isInsideBoard(final int file, final int rank) {
    return isInsideFile(file) && isInsideRank(rank);
  }

  public static boolean isInsideBoard(final Position position) {
    return isInsideBoard(position.getFile(), position.getRank());
  }

  //conversion

  public static int toFileIndex(final char fileChar) {
    return fileChar - 'a';
  }

  public static char toFileChar(final int fileIndex) {
    return (char) ('a' + fileIndex);
  }

  public static int toRankIndex(final char rankChar) {
    return '8' - rankChar;
  }

  public static char toRankChar(final int rankIndex) {
    return (char) ('8' - rankIndex);
  }

  /* Helper Methods */

  private static boolean isBetween(final int value, final int min, final int max) {
    return value >= min && value <= max;
  }

}
