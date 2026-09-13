package com.game.move;

import com.game.Piece;
import com.game.Position;
import java.util.ArrayList;
import java.util.List;

public class Move {

  public enum Type {
    MOVE, CAPTURE, DOUBLE_JUMP, EN_PASSANT, QUEENSIDE_CASTLE, KINGSIDE_CASTLE
  }

  private List<Position> moves = new ArrayList<>();
  private Type type;
  private Piece.Type pieceType;

  public Move() {

  }

  public Move(Piece.Type pieceType) {
    this.pieceType = pieceType;
  }

  public void add(Position move) {
    this.moves.add(move);
  }

  public void add(Position move, Type type) {
    moves.add(move);
    this.type = type;
  }

  public Position move() {
    return moves.isEmpty() ? null : moves.getLast();
  }

  public int count() {
    return moves.size();
  }

  public List<Position> getMoves() {
    return moves;
  }

  public void setMoves(List<Position> moves) {
    this.moves = moves;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Piece.Type getPieceType() {
    return pieceType;
  }

  public void setPieceType(Piece.Type pieceType) {
    this.pieceType = pieceType;
  }
}
