package com.game;

import java.util.Arrays;
import java.util.List;

public class ChessConstants {

  // Fen Constants
  public static final String SPACE = " ";
  public static final String STARTING_FEN_STRING = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - KQkq 0 1";
  public static final String ACTIVE_COLOR_WHITE = "w";
  public static final String ACTIVE_COLOR_BLACK = "b";
  public static final char FORWARD_SLASH = '/';

  public static final List<String> VALID_ACTIVE_COLORS = Arrays.asList(ACTIVE_COLOR_WHITE, ACTIVE_COLOR_BLACK);

  // Piece Constants
  public static final char WHITE_ROOK = 'R';
  public static final char WHITE_KNIGHT = 'N';
  public static final char WHITE_BISHOP = 'B';
  public static final char WHITE_QUEEN = 'Q';
  public static final char WHITE_KING = 'K';
  public static final char WHITE_PAWN = 'P';
  public static final char BLACK_ROOK = 'r';
  public static final char BLACK_KNIGHT = 'n';
  public static final char BLACK_BISHOP = 'b';
  public static final char BLACK_QUEEN = 'q';
  public static final char BLACK_KING = 'k';
  public static final char BLACK_PAWN = 'p';

  public static final List<Character> FEN_VALID_PIECE_TOKENS = List.of(
      WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_PAWN,
      BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_PAWN
  );

  // Board Constants
  public static final int BOARD_TILE_COUNT = 64;
  public static final int BOARD_WIDTH = 8;
  public static final int BOARD_HEIGHT = 8;
  public static final List<Character> BOARD_FILE_TOKENS = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');
  public static final List<Character> BOARD_RANK_TOKENS = List.of('8', '7', '6', '5', '4', '3', '2', '1');

}
