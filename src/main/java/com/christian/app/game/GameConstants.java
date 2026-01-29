package com.christian.app.game;

public class GameConstants {

  //piece symbols
  public static final char WHITE_PAWN_SYMBOL = 'P';
  public static final char BLACK_PAWN_SYMBOL = 'p';
  public static final char WHITE_ROOK_SYMBOL = 'R';
  public static final char BLACK_ROOK_SYMBOL = 'r';
  public static final char WHITE_KNIGHT_SYMBOL = 'N';
  public static final char BLACK_KNIGHT_SYMBOL = 'n';
  public static final char WHITE_BISHOP_SYMBOL = 'B';
  public static final char BLACK_BISHOP_SYMBOL = 'b';
  public static final char WHITE_KING_SYMBOL = 'K';
  public static final char BLACK_KING_SYMBOL = 'k';
  public static final char WHITE_QUEEN_SYMBOL = 'Q';
  public static final char BLACK_QUEEN_SYMBOL = 'q';

  //board
  public static final int BOARD_WIDTH = 8;
  public static final int BOARD_HEIGHT = 8;
  public static final int BOARD_TOTAL_SQUARES = BOARD_HEIGHT * BOARD_WIDTH;

  //algebraic notation
  public static final String WHITE_WINS = "1-0";
  public static final String BLACK_WINS = "0-1";
  public static final String DRAW = "1/2-1/2";
  public static final String ALGEBRAIC_NOTATION_PATTERN = "^([RNBQK])?([a-h])?([1-8])?(x)?([a-h][1-8])([+#])?$";

}
