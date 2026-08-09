package com.game.fenstring;

import com.game.ChessConstants;
import com.game.ChessUtils;
import com.game.Piece;
import com.game.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class FenStringParser {

  private static final int EXPECTED_COMPONENT_SIZE = 6;

  private static final Logger LOGGER = LogManager.getLogger();

  private final Pattern castlingRightsPattern = Pattern.compile("^(-|K?Q?k?q?)$");

  public Map<Component, String> parseComponents(final String fenString) {
    if (ChessUtils.isBlank(fenString)) {
      LOGGER.error("FenString cannot be null or empty");
      return null;
    }

    Map<Component, String> result = new HashMap<>();
    String[] components = fenString.split(ChessConstants.SPACE);
    if (components.length != EXPECTED_COMPONENT_SIZE) {
      String[] tmp = new String[EXPECTED_COMPONENT_SIZE];
      System.arraycopy(components, 0, tmp, 0, Math.min(components.length, EXPECTED_COMPONENT_SIZE));
      components = tmp;
    }

    result.put(Component.PIECE_PLACEMENT, components[0]);
    result.put(Component.ACTIVE_COLOR, components[1]);
    result.put(Component.CASTLING_RIGHTS, components[2]);
    result.put(Component.EN_PASSANT_TARGET, components[3]);
    result.put(Component.HALF_MOVE_CLOCK, components[4]);
    result.put(Component.FULL_MOVE_COUNTER, components[5]);
    return result;
  }

  public List<Piece> parsePiecePlacement(String piecePlacement) {
    if (ChessUtils.isBlank(piecePlacement)) {
      LOGGER.error("Piece placement cannot be null or empty");
      return null;
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
      LOGGER.error("Piece placement count is [{}/{}] - Returning empty list", count, ChessConstants.BOARD_TILE_COUNT);
      return null;
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

  public Map<CastlingRights, Boolean> parseCastlingRights(final String castlingRights) {
    final Map<CastlingRights, Boolean> result = createCastlingRightsMap();
    Matcher castlingRightsMatcher;
    if (ChessUtils.isBlank(castlingRights) || !(castlingRightsMatcher = castlingRightsPattern.matcher(castlingRights)).find()) {
      LOGGER.warn("Castling rights is null or empty or does not match expected pattern: [{}]", castlingRights);
      return result;
    }

    String group = castlingRightsMatcher.group(1);
    for (char token : group.toCharArray()) {
      switch (token) {
        case ChessConstants.WHITE_KING -> result.put(CastlingRights.WHITE_KINGSIDE, Boolean.TRUE);
        case ChessConstants.WHITE_QUEEN -> result.put(CastlingRights.WHITE_QUEENSIDE, Boolean.TRUE);
        case ChessConstants.BLACK_KING -> result.put(CastlingRights.BLACK_KINGSIDE, Boolean.TRUE);
        case ChessConstants.BLACK_QUEEN -> result.put(CastlingRights.BLACK_QUEENSIDE, Boolean.TRUE);
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

  private Map<CastlingRights, Boolean> createCastlingRightsMap() {
    Map<CastlingRights, Boolean> result = new HashMap<>();
    result.put(CastlingRights.WHITE_KINGSIDE, Boolean.FALSE);
    result.put(CastlingRights.WHITE_QUEENSIDE, Boolean.FALSE);
    result.put(CastlingRights.BLACK_KINGSIDE, Boolean.FALSE);
    result.put(CastlingRights.BLACK_QUEENSIDE, Boolean.FALSE);
    return result;
  }
}
