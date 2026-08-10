package com.game;

import com.game.Piece.Type;
import com.game.algebraicnotation.AlgebraicNotation;
import com.game.algebraicnotation.AlgebraicNotation.Status;
import com.game.algebraicnotation.AlgebraicNotationParser;
import com.game.fenstring.CastlingRights;
import com.game.fenstring.Component;
import com.game.fenstring.FenStringParser;
import com.game.io.TerminalIO;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChessGame {

  private static final Logger LOGGER = LogManager.getLogger();

  private String startingFenString;
  private Board board;
  private Position enPassantTarget;
  private int halfMoveClock;
  private int fullMoveCounter;
  private boolean isWhiteStarting;
  private boolean isWhiteTurn;

  private FenStringParser fenParser;
  private AlgebraicNotationParser algParser;
  private TerminalIO  screen;
  private boolean isInitialized;
  private boolean isRunning;

  private Map<CastlingRights, Boolean> castlingRights;

  public static void main(String[] args) {
    ChessGame game = new ChessGame(ChessConstants.STARTING_FEN_STRING);
    game.init();
    game.run();
  }

  public ChessGame(String startingFenString) {
    this.startingFenString = startingFenString;
  }

  public void init() {
    if (isInitialized) {
      return;
    }

    LOGGER.info("Starting initialization of {}", this.getClass().getName());
    fenParser = new FenStringParser();
    algParser = new AlgebraicNotationParser();
    screen = new TerminalIO();
    board = new Board();

    try {
      Map<Component, String> components = fenParser.parseComponents(startingFenString);
      List<Piece> pieces = fenParser.parsePiecePlacement(components.get(Component.PIECE_PLACEMENT));
      Objects.requireNonNull(pieces);
      board.placePiece(pieces);
      isWhiteStarting = isWhiteTurn = fenParser.parseActiveColor(components.get(Component.ACTIVE_COLOR));
      castlingRights = fenParser.parseCastlingRights(components.get(Component.CASTLING_RIGHTS));
      enPassantTarget = fenParser.parseEnPassantTarget(components.get(Component.EN_PASSANT_TARGET));
      halfMoveClock = fenParser.parseHalfMoveClock(components.get(Component.HALF_MOVE_CLOCK));
      fullMoveCounter = fenParser.parseFullMoveCounter(components.get(Component.FULL_MOVE_COUNTER));

      isInitialized = true;
      isRunning = true;
      LOGGER.info("Successfully initialized {}", this.getClass().getName());
    } catch (Exception e) {
      LOGGER.error("Failed to initialize {}", this.getClass().getName(), e);
      throw new RuntimeException("Failed to initialize " + this.getClass().getName(), e);
    }
  }

  public void run() {
    while (isRunning) {
      AlgebraicNotation input = getUserInput();
      List<Piece> results = queryBoard(input);
      if (results == null || results.isEmpty()) {
        LOGGER.warn("No pieces found for input {}", input);
        continue;
      }

      flipTurn();
      incrementCounter(input.getType() == Type.PAWN, input.isCapture()); // TODO: check status = Move as well
      incrementClock();
    }
  }

  private AlgebraicNotation getUserInput() {
    AlgebraicNotation result = null;
    final String playerColor = isWhiteTurn ? "White" : "Black";
    final String prompt = playerColor + " > please enter move: ";
    while (result == null) {
      final String input = screen.prompt(prompt);
      result = algParser.parse(input);
    }
    return result;
  }

  private List<Piece> queryBoard(AlgebraicNotation alg) {
    if (alg.getStatus() == Status.MOVE) {
      if (ChessUtils.isWithinBoardFiles(alg.getStartPosition().getFile())) {
        return board.searchFor(alg.getType(), alg.getStartPosition().getFile(), null, isWhiteTurn);
      } else if (ChessUtils.isWithinBoardRanks(alg.getStartPosition().getRank())) {
        return board.searchFor(alg.getType(), null, alg.getStartPosition().getRank(), isWhiteTurn);
      } else {
        return board.searchFor(alg.getType(), null, null, isWhiteTurn);
      }
    }
    return Collections.emptyList();
  }

  private void flipTurn() {
    isWhiteTurn = !isWhiteTurn;
  }

  private void incrementCounter(boolean isPawnMove, boolean isCapture) {
    // inc if non-pawn moved or non-capture move
    // reset to 0 if pawn moved or capture move
    // trigger draw when clock reaches 100
    if (isPawnMove || isCapture) {
      fullMoveCounter = 0;
    } else {
      fullMoveCounter++;
    }
  }

  private void incrementClock() {
    fullMoveCounter += isWhiteStarting == isWhiteTurn ? 1 : 0;
  }

  /*-- Getters/Setters --*/

  public String getStartingFenString() {
    return startingFenString;
  }

  public void setStartingFenString(String startingFenString) {
    this.startingFenString = startingFenString;
  }
}
