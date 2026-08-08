package com.game;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Board {

  private static final Logger LOGGER = LogManager.getLogger();

  private final Piece[][] board;

  public Board() {
    board = new Piece[ChessConstants.BOARD_HEIGHT][ChessConstants.BOARD_WIDTH];
  }

  public void placePiece(List<Piece> pieces) {
    pieces.forEach(this::placePiece);
  }

  public void placePiece(Piece piece) {
    try {
      Position pos = piece.getPosition();
      board[pos.getY()][pos.getX()] = piece;
    } catch (Exception e) {
      LOGGER.error("Failed to place piece: {}", piece);
    }
  }

}
