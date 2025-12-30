package com.christian.app.game.util;

public class GameConstants {

  public static final String STRING_SPACE = " ";
  public static final String WHITE_ACTIVE_COLOR = "w";
  public static final String BLACK_ACTIVE_COLOR = "b";
  public static final String STRING_HYPHEN = "-";
  public static final String STRING_SLASH = "/";
  public static final String CASTLING_RIGHTS_PATTERN = "^Q?K?q?k?$";

  public static final int CASTLING_WHITE_QUEEN = 0;
  public static final int CASTLING_WHITE_KING = 1;
  public static final int CASTLING_BLACK_QUEEN = 2;
  public static final int CASTLING_BLACK_KING = 3;
  public static final int CASTLING_SIDES_COUNT = 4;
  public static final int BOARD_WIDTH = 8;
  public static final int BOARD_HEIGHT = 8;
  public static final int BOARD_TILE_COUNT = 64;
  public static final int FEN_COMPONENT_COUNT = 6;
  public static final int BOARD_STATE_INDEX = 0;
  public static final int ACTIVE_COLOR_INDEX = 1;
  public static final int CASTLING_RIGHTS_INDEX = 2;
  public static final int ENPASSANT_INDEX = 3;
  public static final int HALF_MOVE_COUNTER_INDEX = 4;
  public static final int HALF_MOVE_MAX = 100;
  public static final int FULL_MOVE_CLOCK_INDEX = 5;
  public static final int FULL_MOVE_MAX = 50;

  public static final char WHITE_PAWN_SYMBOL = 'P';
  public static final char BLACK_PAWN_SYMBOL = 'p';
  public static final char WHITE_ROOK_SYMBOL = 'R';
  public static final char BLACK_ROOK_SYMBOL = 'r';
  public static final char WHITE_KNIGHT_SYMBOL = 'N';
  public static final char BLACK_KNIGHT_SYMBOL = 'n';
  public static final char WHITE_BISHOP_SYMBOL = 'B';
  public static final char BLACK_BISHOP_SYMBOL = 'b';
  public static final char WHITE_QUEEN_SYMBOL = 'Q';
  public static final char BLACK_QUEEN_SYMBOL = 'q';
  public static final char WHITE_KING_SYMBOL = 'K';
  public static final char BLACK_KING_SYMBOL = 'k';
}
