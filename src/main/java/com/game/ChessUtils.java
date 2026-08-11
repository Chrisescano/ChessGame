package com.game;

import com.game.Piece.Type;

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

  public static boolean isWithinBoardFiles(int value) {
    return ChessUtils.withinRange(value, 0, ChessConstants.BOARD_WIDTH - 1);
  }

  public static boolean isWithinBoardRanks(int value) {
    return ChessUtils.withinRange(value, 0, ChessConstants.BOARD_HEIGHT - 1);
  }

  public static Type typeOf(char token) {
    Type type;
    switch (token) {
      case ChessConstants.WHITE_ROOK, ChessConstants.BLACK_ROOK -> type = Type.ROOK;
      case ChessConstants.WHITE_KNIGHT, ChessConstants.BLACK_KNIGHT ->  type = Type.KNIGHT;
      case ChessConstants.WHITE_BISHOP, ChessConstants.BLACK_BISHOP -> type = Type.BISHOP;
      case ChessConstants.WHITE_QUEEN, ChessConstants.BLACK_QUEEN -> type = Type.QUEEN;
      case ChessConstants.WHITE_KING, ChessConstants.BLACK_KING -> type = Type.KING;
      case ChessConstants.WHITE_PAWN, ChessConstants.BLACK_PAWN -> type = Type.PAWN;
      default -> type = null;
    }
    return type;
  }

}
