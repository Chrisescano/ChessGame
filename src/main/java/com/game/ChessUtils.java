package com.game;

import com.game.piece.Position;

public class ChessUtils {

  public static boolean isBlank(final String value) {
    return value == null || value.trim().isEmpty();
  }

  public static boolean withinRange(final int value, final int min, final int max) {
    return value >= min && value <= max;
  }

  public static int toFile(final char file) {
    return ChessConstants.BOARD_FILE_TOKENS.indexOf(file);
  }

  public static int toRank(final char rank) {
    return ChessConstants.BOARD_RANK_TOKENS.indexOf(rank);
  }

  public static Position toPosition(final String algebraicNotation) {
    if (ChessUtils.isBlank(algebraicNotation) || algebraicNotation.length() != 2) {
      return null;
    }
    int file = toFile(algebraicNotation.charAt(0));
    int rank = toRank(algebraicNotation.charAt(1));
    if (file != -1 && rank != -1) {
      return new Position(file, rank);
    }
    return null;
  }

}
