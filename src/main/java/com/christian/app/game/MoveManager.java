package com.christian.app.game;

import com.christian.app.parser.AlgebraicNotationParser;

public class MoveManager {

  private final Board board;
  private final AlgebraicNotationParser parser = new AlgebraicNotationParser();

  public MoveManager(Board board) {
    this.board = board;
  }

  public MoveRecord evaluateMove(String move) {
    //parse input from user
    AlgebraicNotation userMove = parser.parse(move);
    if (!userMove.isMove()) {
      return null;
    }

    //find piece from user input

    return null;
  }

}
