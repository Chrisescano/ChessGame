package com.christian.app.parser;

import com.christian.app.game.Board;
import com.christian.app.game.Fen;
import com.christian.app.game.util.GameConstants;
import com.christian.app.game.util.GameUtil;
import com.christian.app.piece.Piece;
import com.christian.app.piece.Position;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FenParser {

  private static final Pattern CASTLING_PATTERN = Pattern.compile(GameConstants.CASTLING_RIGHTS_PATTERN);

  public Fen parseFen(String fenString) {
    if (fenString == null || fenString.isEmpty()) {
      return null;
    }

    String[] fenComponents = fenString.split(GameConstants.STRING_SPACE);
    String[] safeComps = new String[GameConstants.FEN_COMPONENT_COUNT];
    System.arraycopy(fenComponents, 0, safeComps, 0,
        Math.min(GameConstants.FEN_COMPONENT_COUNT, fenComponents.length));

    String boardStateString = safeComps[GameConstants.BOARD_STATE_INDEX];
    String activeColorString = safeComps[GameConstants.ACTIVE_COLOR_INDEX];
    String castlingRightsString = safeComps[GameConstants.CASTLING_RIGHTS_INDEX];
    String enPassantString = safeComps[GameConstants.ENPASSANT_INDEX];
    String halfMoveCounterString = safeComps[GameConstants.HALF_MOVE_COUNTER_INDEX];
    String fullMoveClockString = safeComps[GameConstants.FULL_MOVE_CLOCK_INDEX];

    Board board = parseBoardState(boardStateString);
    boolean activeColor = parseActiveColor(activeColorString);
    boolean[] castlingRights = parseCastlingRights(castlingRightsString);
    Position enPassant = parseEnpassant(enPassantString);
    int halfMoveCounter = parseMoveCounters(halfMoveCounterString, 0, GameConstants.HALF_MOVE_MAX);
    int fullMoveClock = parseMoveCounters(fullMoveClockString, 0, GameConstants.FULL_MOVE_MAX);

    return new Fen(board, activeColor, castlingRights, enPassant, halfMoveCounter, fullMoveClock);
  }

  public String toFenString(Fen fen) {
    if (fen == null) {
      return null;
    }

    String boardState = toBoardStateString(fen.getBoard());
    String activeColor = toActiveColorString(fen.isActiveColor());
    String castlingRights = toCastlingRightsString(fen.getCastlingRights());
    String enPassant = toEnPassantString(fen.getEnPassant());

    return String.format("%s %s %s %s %s %s", boardState, activeColor, castlingRights, enPassant,
        fen.getHalfMoveCounter(), fen.getFullMoveClock());
  }

  private Board parseBoardState(String boardStateString) {
    if (boardStateString == null || boardStateString.isEmpty()) {
      return null;
    }

    int x = 0, y = 0, count = 0;
    Board board = new Board();
    for (char token : boardStateString.toCharArray()) {
      Piece piece = Piece.create(token, x, y);
      if (piece != null) {
        board.addPiece(piece);
        x++;
        count++;
      } else if (token >= '1' && token <= '8') {
        int emptyTiles = token - '0';
        x += emptyTiles;
        count += emptyTiles;
      } else if (token == '/') {
        x = 0;
        y++;
      }
    }

    if (count == GameConstants.BOARD_TILE_COUNT) {
      return board;
    } else {
      return null;
    }
  }

  private String toBoardStateString(Board board) {
    if (board == null) {
      return null;
    }

    StringBuilder fenString = new StringBuilder();
    for (int y = 0; y < GameConstants.BOARD_HEIGHT; y++) {
      int spaces = 0;
      for (int x = 0; x < GameConstants.BOARD_WIDTH; x++) {
        Piece piece = board.getPiece(x, y);
        if (piece == null) {
          spaces++;
        } else {
          if (spaces > 0) {
            fenString.append(spaces);
            spaces = 0;
          }
          fenString.append(piece.getSymbol());
        }
      }
      if (spaces > 0) {
        fenString.append(spaces);
      }
      if (y < 7) {
        fenString.append(GameConstants.STRING_SLASH);
      }
    }
    return fenString.toString();
  }

  private boolean parseActiveColor(String activeColorString) {
    if (activeColorString == null || activeColorString.isEmpty()) {
      return false;
    }

    return activeColorString.equals(GameConstants.WHITE_ACTIVE_COLOR);
  }

  private String toActiveColorString(boolean activeColor) {
    return activeColor ? GameConstants.WHITE_ACTIVE_COLOR : GameConstants.BLACK_ACTIVE_COLOR;
  }

  private boolean[] parseCastlingRights(String castlingRightsString) {
    boolean[] castlingRights = new boolean[GameConstants.CASTLING_SIDES_COUNT];

    if (castlingRightsString == null || castlingRightsString.isEmpty()
        || castlingRightsString.equals(GameConstants.STRING_HYPHEN)) {
      return castlingRights;
    }

    Matcher matcher = CASTLING_PATTERN.matcher(castlingRightsString);
    if (matcher.matches()) {
      for (int i = 0; i < 4; i++) {
        castlingRights[i] = matcher.group(i + 1) != null;
      }
    }

    return castlingRights;
  }

  private String toCastlingRightsString(boolean[] castlingRights) {
    StringBuilder builder = new StringBuilder();
    Map<Integer, Character> castlingMap = Map.of(
        GameConstants.CASTLING_WHITE_QUEEN, GameConstants.WHITE_QUEEN_SYMBOL,
        GameConstants.CASTLING_WHITE_KING, GameConstants.WHITE_KING_SYMBOL,
        GameConstants.CASTLING_BLACK_QUEEN, GameConstants.BLACK_QUEEN_SYMBOL,
        GameConstants.CASTLING_BLACK_KING, GameConstants.BLACK_KING_SYMBOL
    );

    for (int i = 0; i < castlingRights.length; i++) {
      boolean side = castlingRights[i];
      if (side) {
        builder.append(castlingMap.get(i));
      }
    }
    return builder.isEmpty() ? GameConstants.STRING_HYPHEN : builder.toString();
  }

  private Position parseEnpassant(String enPassantString) {
    if (enPassantString == null || enPassantString.isEmpty()
        || enPassantString.equals(GameConstants.STRING_HYPHEN) || enPassantString.length() > 2) {
      return new Position();
    }

    int file = GameUtil.toFileIndex(enPassantString.charAt(0));
    int rank = GameUtil.toRankIndex(enPassantString.charAt(1));
    if (GameUtil.isInsideRange(file, 0, GameConstants.BOARD_WIDTH) && (rank == 7 || rank == 1)) {
      return new Position(file, rank);
    }
    return new Position();
  }

  private String toEnPassantString(Position enPassant) {
    if (enPassant == null || GameUtil.toIndex(enPassant) == 0) {
      return GameConstants.STRING_HYPHEN;
    }

    char file = GameUtil.toFileChar(enPassant.getX());
    char rank = GameUtil.toRankChar(enPassant.getY());
    return String.format("%c%c", file, rank);
  }

  private int parseMoveCounters(String moveCounter, int min, int max) {
    try {
      int counter = Integer.parseInt(moveCounter);
      return GameUtil.isInsideRange(counter, min, max) ? counter : min;
    } catch (NumberFormatException ignored) {
    }
    return min;
  }
}
