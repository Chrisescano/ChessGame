package com.christian.app.game;

public class GameConstants {

  //piece constants
  public static final char WHITE_PAWN_SYMBOL = 'P';
  public static final char WHITE_ROOK_SYMBOL = 'R';
  public static final char WHITE_KNIGHT_SYMBOL = 'N';
  public static final char WHITE_BISHOP_SYMBOL = 'B';
  public static final char WHITE_QUEEN_SYMBOL = 'Q';
  public static final char WHITE_KING_SYMBOL = 'K';
  public static final char BLACK_PAWN_SYMBOL = 'p';
  public static final char BLACK_ROOK_SYMBOL = 'r';
  public static final char BLACK_KNIGHT_SYMBOL = 'n';
  public static final char BLACK_BISHOP_SYMBOL = 'b';
  public static final char BLACK_QUEEN_SYMBOL = 'q';
  public static final char BLACK_KING_SYMBOL = 'k';

  //board constants
  public static final int BOARD_WIDTH = 8;
  public static final int BOARD_HEIGHT = 8;

  public static final char EMPTY_TILE = Character.MIN_VALUE;

  //algebraic notation parser constants
  public static final String ALGEBRAIC_NOTATION_PATTERN = "^([RNBQK])?([a-h])?([1-8])?(x)?([a-h])([1-8])([+#])?$";

  public static final int ALG_NOT_TYPE_GROUP = 1;
  public static final int ALG_NOT_START_FILE_GROUP = 2;
  public static final int ALG_NOT_START_RANK_GROUP = 3;
  public static final int ALG_NOT_CAPTURE_GROUP = 4;
  public static final int ALG_NOT_END_FILE_GROUP = 5;
  public static final int ALG_NOT_END_RANK_GROUP = 6;
  public static final int ALG_NOT_CHECK_MATE_GROUP = 7;

  public static final char ALG_NOT_CAPTURE = 'x';
  public static final char ALG_NOT_CHECK = '+';
  public static final char ALG_NOT_MATE = '#';

}
