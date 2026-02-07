package com.christian.app.game;

import com.christian.app.piece.Piece;
import com.christian.app.piece.Type;
import com.christian.app.util.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Board {

  private final List<Piece> pieces = new ArrayList<>();
  private final char[][] board = new char[GameConstants.BOARD_HEIGHT][GameConstants.BOARD_WIDTH];

  public void add(Piece piece) {
    if (piece != null && !pieces.contains(piece) && isEmpty(piece.getPosition())) {
      pieces.add(piece);
      board[piece.getPosition().getRank()][piece.getPosition().getFile()] = piece.getSymbol();
    }
  }

  public void remove(Piece piece) {
    if (piece != null && pieces.contains(piece) && getSymbol(piece.getPosition()) == piece.getSymbol()) {
      pieces.remove(piece);
      board[piece.getPosition().getRank()][piece.getPosition().getFile()] = GameConstants.EMPTY_TILE;
    }
  }

  public void clear() {
    pieces.clear();
    for (int rank = 0; rank < GameConstants.BOARD_HEIGHT; rank++) {
      for (int file = 0; file < GameConstants.BOARD_WIDTH; file++) {
        board[rank][file] = GameConstants.EMPTY_TILE;
      }
    }
  }

  public Piece getPiece(Position position) {
    return getPiece(position.getFile(), position.getRank());
  }

  public Piece getPiece(int file, int rank) {
    List<Piece> results = search(null, null, file, rank, null);
    return results.isEmpty() ? null : results.getFirst();
  }

  public List<Piece> search(final Boolean isWhite, final Type type, final Integer file, final Integer rank, final Position move) {
    Stream<Piece> results = pieces.stream();

    if (isWhite != null) {
      results = results.filter(piece -> piece.isWhite() == isWhite);
    }

    if (type != null) {
      results = results.filter(piece -> piece.getType() == type);
    }

    if (file != null && GameUtil.isInsideFile(file)) {
      results = results.filter(piece -> piece.getPosition().getFile() == file);
    }

    if (rank != null && GameUtil.isInsideRank(rank)) {
      results = results.filter(piece -> piece.getPosition().getRank() == rank);
    }

    if (move != null && GameUtil.isInsideBoard(move)) {
      results = results.filter(piece -> piece.movesContain(move));
    }

    return results.toList();
  }

  public boolean isEmpty(int file, int rank) {
    return getSymbol(file, rank) == GameConstants.EMPTY_TILE;
  }

  public boolean isEmpty(Position position) {
    return isEmpty(position.getFile(), position.getRank());
  }

  public char getSymbol(Position position) {
    return getSymbol(position.getFile(), position.getRank());
  }

  public char getSymbol(int file, int rank) {
    if (GameUtil.isInsideBoard(file, rank)) {
      return board[rank][file];
    }
    return GameConstants.EMPTY_TILE;
  }

  public List<Piece> getPieces() {
    return pieces;
  }
}
