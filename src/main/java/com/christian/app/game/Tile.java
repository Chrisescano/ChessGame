package com.christian.app.game;

import com.christian.app.piece.Piece;
import java.util.HashSet;
import java.util.Set;

public class Tile {

  private final Set<Integer> whitePieceIdsAttack = new HashSet<>();
  private final Set<Integer> blackPieceIdsAttack = new HashSet<>();
  private Piece piece;

  // Methods

  public boolean addAttackPiece(Piece piece) {
    if (piece.isWhite()) {
      return whitePieceIdsAttack.add(0);
    } else {
      return blackPieceIdsAttack.add(0);
    }
  }

  public boolean removeAttackPiece(Piece piece) {
    if (piece.isWhite()) {
      return whitePieceIdsAttack.remove(0);
    } else {
      return blackPieceIdsAttack.remove(0);
    }
  }

  // Getters/Setters

  public Piece getPiece() {
    return piece;
  }

  public void setPiece(Piece piece) {
    this.piece = piece;
  }
}
