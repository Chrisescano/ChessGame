package com.christian.app.game;

import com.christian.app.piece.Piece;
import com.christian.app.piece.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Board {

  private final List<Piece> pieces = new ArrayList<>();
  private final char[][] board = new char[GameConstants.BOARD_HEIGHT][GameConstants.BOARD_WIDTH];

  public void clear() {
    pieces.clear();
    for (int r = 0; r < GameConstants.BOARD_HEIGHT; r++) {
      for (int c = 0; c < GameConstants.BOARD_WIDTH; c++) {
        board[r][c] = Character.MIN_VALUE;
      }
    }
  }

  public void place(Piece piece) {
    if (piece != null) {
      Position pos = piece.getPosition();
      if (GameUtil.isInsideBoard(pos) && !isOccupied(pos)) {
        pieces.add(piece);
        board[pos.getY()][pos.getX()] = piece.getSymbol();
      }
    }
  }

  public void remove(Piece piece) {
    if (piece != null) {
      Position pos = piece.getPosition();
      if (GameUtil.isInsideBoard(pos) && getSymbol(pos) == piece.getSymbol()) {
        pieces.remove(piece);
        board[pos.getY()][pos.getX()] = Character.MIN_VALUE;
      }
    }
  }

  public List<Piece> searchFor(Boolean isWhite, Type type, Position position) {
    Stream<Piece> stream = pieces.stream();
    if (isWhite != null) {
      stream = stream.filter(piece -> piece.isWhite() == isWhite);
    }

    if (type != null) {
      stream = stream.filter(piece -> piece.getType() == type);
    }

    if (position != null) {
      if (GameUtil.isInsideBoardFile(position.getX())) {
        stream = stream.filter(piece -> piece.getPosition().getX() == position.getX());
      }
      if (GameUtil.isInsideBoardRank(position.getY())) {
        stream = stream.filter(piece -> piece.getPosition().getY() == position.getY());
      }
    }
    return stream.toList();
  }

  private char getSymbol(int x, int y) {
    return board[y][x];
  }

  private char getSymbol(Position position) {
    return getSymbol(position.getX(), position.getY());
  }

  private boolean isOccupied(Position position) {
    return getSymbol(position.getX(), position.getY()) != Character.MIN_VALUE;
  }

}
