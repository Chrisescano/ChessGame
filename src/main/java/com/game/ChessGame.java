package com.game;

import com.game.fenstring.CastlingRights;
import com.game.fenstring.Component;
import com.game.fenstring.FenStringParser;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChessGame {

  private static final Logger LOGGER = LogManager.getLogger();

  private String startingFenString;
  private FenStringParser parser;
  private Board board;
  private Position enPassantTarget;
  private int halfMoveClock;
  private int fullMoveCounter;
  private boolean isWhiteTurn;
  private boolean isInitialized;

  private Map<CastlingRights, Boolean> castlingRights;

  public ChessGame(String startingFenString) {
    this.startingFenString = startingFenString;
  }

  public void init() {
    if (isInitialized) {
      return;
    }

    LOGGER.info("Starting initialization of {}", this.getClass().getName());
    parser = new FenStringParser();
    board = new Board();

    try {
      Map<Component, String> components = parser.parseComponents(startingFenString);
      List<Piece> pieces = parser.parsePiecePlacement(components.get(Component.PIECE_PLACEMENT));
      Objects.requireNonNull(pieces);
      board.placePiece(pieces);
      isWhiteTurn = parser.parseActiveColor(components.get(Component.ACTIVE_COLOR));
      castlingRights = parser.parseCastlingRights(components.get(Component.CASTLING_RIGHTS));
      enPassantTarget = parser.parseEnPassantTarget(components.get(Component.EN_PASSANT_TARGET));
      halfMoveClock = parser.parseHalfMoveClock(components.get(Component.HALF_MOVE_CLOCK));
      fullMoveCounter = parser.parseFullMoveCounter(components.get(Component.FULL_MOVE_COUNTER));

      isInitialized = true;
      LOGGER.info("Successfully initialized {}", this.getClass().getName());
    } catch (Exception e) {
      LOGGER.error("Failed to initialize {}", this.getClass().getName(), e);
      throw new RuntimeException("Failed to initialize " + this.getClass().getName(), e);
    }
  }

  public void run() {

  }

  public String getStartingFenString() {
    return startingFenString;
  }

  public void setStartingFenString(String startingFenString) {
    this.startingFenString = startingFenString;
  }
}
