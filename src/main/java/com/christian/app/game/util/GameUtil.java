package com.christian.app.game.util;

import com.christian.app.piece.Position;

public class GameUtil {

  public static boolean isInsideRange(int num, int min, int max) {
    return num >= min && num < max;
  }

  public static int toRankIndex(char rank) {
    return '8' - rank;
  }

  public static char toRankChar(int rank) {
    return (char) ('8' - rank);
  }

  public static int toFileIndex(char file) {
    return file - 'a';
  }

  public static char toFileChar(int file) {
    return (char) ('a' + file);
  }

  public static int toIndex(Position position) {
    return toIndex(position.getY(), position.getY());
  }

  public static int toIndex(int x, int y) {
    return y * GameConstants.BOARD_HEIGHT + x;
  }
}
