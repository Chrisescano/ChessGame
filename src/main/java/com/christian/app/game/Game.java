package com.christian.app.game;

import com.christian.app.parser.FenParser;

public class Game {

  private final Fen fen;

  public Game(String fenString) {
    fen = Fen.parse(fenString);
  }
}
