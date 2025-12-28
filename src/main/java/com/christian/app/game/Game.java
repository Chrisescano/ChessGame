package com.christian.app.game;

import com.christian.app.parser.FenParser;

public class Game {

  private final FenParser parser = new FenParser();
  private final Fen fen;

  public Game(String fenString) {
    fen = parser.parseFen(fenString);
  }
}
