package com.christian.app;

import com.christian.app.piece.Piece;
import com.christian.app.piece.Position;
import com.christian.app.piece.Type;

public class Main {

  public static void main(String[] args) {
    Piece piece = new Piece(Type.KING, new Position(), false);

    Position position = piece.getPosition();
    position = null;

    System.out.println(piece.getPosition());
  }

}
