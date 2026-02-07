package com.christian.app.piece;

import com.christian.app.game.GameConstants;

public enum Type {
  PAWN,
	ROOK,
	KNIGHT,
	BISHOP,
	QUEEN,
	KING;

  public static Type toType(char value) {
    return switch (value) {
      case GameConstants.WHITE_PAWN_SYMBOL, GameConstants.BLACK_PAWN_SYMBOL -> Type.PAWN;
      case GameConstants.WHITE_ROOK_SYMBOL, GameConstants.BLACK_ROOK_SYMBOL -> Type.ROOK;
      case GameConstants.WHITE_KNIGHT_SYMBOL, GameConstants.BLACK_KNIGHT_SYMBOL -> Type.KNIGHT;
      case GameConstants.WHITE_BISHOP_SYMBOL, GameConstants.BLACK_BISHOP_SYMBOL -> Type.BISHOP;
      case GameConstants.WHITE_QUEEN_SYMBOL, GameConstants.BLACK_QUEEN_SYMBOL -> Type.QUEEN;
      case GameConstants.WHITE_KING_SYMBOL, GameConstants.BLACK_KING_SYMBOL -> Type.KING;
      default -> null;
    };
  };
}
