package com.game.parser;

import com.game.ChessConstants;
import com.game.ChessUtils;
import com.game.obj.FenString;
import com.game.piece.Piece;
import com.game.piece.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class FenStringParser {

  private static final int EXPECTED_COMPONENT_SIZE = 6;
  private static final int EXPECTED_CASTLING_RIGHTS_SIZE = 4;

  private static final Logger LOGGER = LogManager.getLogger();

  private final Pattern castlingRightsPattern = Pattern.compile("^(-|K?Q?k?q?)$");

  public FenString parse(String fenString) {
    if (ChessUtils.isBlank(fenString)) {
      LOGGER.warn("FenString cannot be null or blank");
      return null;
    }

    String[] components = splitComponents(fenString);
    List<Piece> pieces;
    try {
      pieces = parsePiecePlacement(components[0]);
    } catch (Exception e) {
      LOGGER.error("Error while parsing fenString {}", fenString, e);
      return null;
    }

    boolean activeColor = parseActiveColor(components[1]);
    boolean[] castlingRights = parseCastlingRights(components[2]);
    Position enPassantTarget = parseEnPassantTarget(components[3]);
    int halfMoveClock = parseHalfMoveClock(components[4]);
    int fullMoveCounter = parseFullMoveCounter(components[5]);
    return new FenString(pieces, activeColor, castlingRights, enPassantTarget, halfMoveClock, fullMoveCounter);
  }

  public List<Piece> parsePiecePlacement(String piecePlacement) {
    if (ChessUtils.isBlank(piecePlacement)) {
      LOGGER.error("Piece placement cannot be null or empty");
      throw new IllegalArgumentException("Piece placement is either null or empty");
    }

    List<Piece> pieces = new ArrayList<>();
    int x = 0, y = 0, count = 0;
    for (char token : piecePlacement.toCharArray()) {
      if (ChessConstants.FEN_VALID_PIECE_TOKENS.contains(token)) {
        Piece piece = new Piece(Piece.Type.of(token), x, y, Character.isUpperCase(token));
        pieces.add(piece);
        x++;
        count++;
      } else if (token > '0' && token < '9') {
        int space = token - '0';
        x += space;
        count += space;
      } else if (ChessConstants.FORWARD_SLASH == token) {
        x = 0;
        y++;
      } else {
        LOGGER.warn("Invalid character in Piece Placement String: [{}]", token);
      }
    }

    if (count != ChessConstants.BOARD_TILE_COUNT) {
      LOGGER.warn("Piece placement count is [{}/{}] - Returning empty list", count, ChessConstants.BOARD_TILE_COUNT);
      return Collections.emptyList();
    }
    return pieces;
  }

  public boolean parseActiveColor(final String activeColor) {
    if (ChessUtils.isBlank(activeColor) || !ChessConstants.VALID_ACTIVE_COLORS.contains(activeColor)) {
      LOGGER.warn("Active color is null or empty: [{}]", activeColor);
      return true;
    }
    return ChessConstants.ACTIVE_COLOR_WHITE.equals(activeColor);
  }

  public boolean[] parseCastlingRights(final String castlingRights) {
    final boolean[] result = new boolean[EXPECTED_CASTLING_RIGHTS_SIZE];
    Matcher castlingRightsMatcher;
    if (ChessUtils.isBlank(castlingRights) || !(castlingRightsMatcher = castlingRightsPattern.matcher(castlingRights)).find()) {
      LOGGER.warn("Castling rights is null or empty or does not match expected pattern: [{}]", castlingRights);
      return result;
    }

    String group = castlingRightsMatcher.group(1);
    for (char token : group.toCharArray()) {
      switch (token) {
        case ChessConstants.WHITE_KING -> result[0] = true;
        case ChessConstants.WHITE_QUEEN -> result[1] = true;
        case ChessConstants.BLACK_KING -> result[2] = true;
        case ChessConstants.BLACK_QUEEN -> result[3] = true;
      }
    }
    return result;
  }

  public Position parseEnPassantTarget(final String enPassantTarget) {
    Position enPassantTargetPosition = ChessUtils.toPosition(enPassantTarget);
    if (enPassantTargetPosition == null) {
      LOGGER.warn("EnPassant target is null or empty or failed to be parsed: [{}]", enPassantTarget);
      return null;
    }
    return enPassantTargetPosition;
  }

  public int parseHalfMoveClock(final String halfMoveClock) {
    return parseNum(halfMoveClock, 100);
  }

  public int parseFullMoveCounter(final String fullMoveClock) {
    return parseNum(fullMoveClock, 50);
  }

  private int parseNum(final String number, int max) {
    try {
      int result = Integer.parseInt(number);
      if (ChessUtils.withinRange(result, 0, max)) {
        return result;
      } else {
        LOGGER.warn("Number [{}] is out of range [{} - {}]", result, 0, max);
      }
    } catch (NumberFormatException e) {
      LOGGER.warn("Invalid numeric value: [{}]", number);
    }
    return 0;
  }

  private String[] splitComponents(final String fenString) {
    String[] components = fenString.split(ChessConstants.SPACE);
    if (components.length != EXPECTED_COMPONENT_SIZE) {
      String[] result = new String[EXPECTED_COMPONENT_SIZE];
      System.arraycopy(components, 0, result, 0, result.length);
      return result;
    }
    return components;
  }
}
