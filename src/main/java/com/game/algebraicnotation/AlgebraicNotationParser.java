package com.game.algebraicnotation;

import com.game.ChessConstants;
import com.game.ChessUtils;
import com.game.Piece.Type;
import com.game.Position;
import com.game.algebraicnotation.AlgebraicNotation.Status;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlgebraicNotationParser {

  private static final Logger LOGGER = LogManager.getLogger();
  private static final Pattern pattern = Pattern.compile("^(([RNBQK]|[a-h])?([a-h]|[1-8])?(x)?([a-h][1-8])([+#])?)|(1-0)|(0-1)|(1/2-1/2)$");

  /*
  pattern: (([RNBQK])?([a-h]|[1-8])?(x)?([a-h][1-8])([+#])?)|(1-0)|(0-1)|(1/2-1/2)
  group 1: (([RNBQK])?([a-h]|[1-8])?(x)?([a-h][1-8])([+#])?)
    group 2: piece type or pawn file if capture
    group 3: rank or file
    group 4: capture
    group 5: square piece moved
    group 6: capture or mate
  group 7: white win
  group 8: black win
  group 9: draw
   */
  public AlgebraicNotation parse(String algebraicNotation) {
    if (ChessUtils.isBlank(algebraicNotation)) {
      LOGGER.error("AlgebraicNotation cannot be empty or null");
      return null;
    }

    Matcher matcher;
    if (!(matcher = pattern.matcher(algebraicNotation)).find()) {
      LOGGER.error("AlgebraicNotation does not match expected pattern: {}", algebraicNotation);
      return null;
    }

    if (matcher.group(1) != null) {
      char typeChar = matcher.group(2) == null ? ChessConstants.WHITE_PAWN : matcher.group(1).charAt(0);
      Type type;
      Position startPos;
      boolean isCapture = matcher.group(4) != null;
      if (Character.isUpperCase(typeChar)) {
        type = ChessUtils.typeOf(typeChar);
        char startingToken = matcher.group(3) == null ? Character.MIN_VALUE : matcher.group(3).charAt(0);
        startPos = new Position(ChessUtils.toFile(startingToken), ChessUtils.toRank(startingToken));
      } else {
        type = Type.PAWN;
        startPos = new Position(ChessUtils.toFile(typeChar), -1);
        if (!isCapture) {
          LOGGER.warn("Missing capture symbol when pawn file is added to algebraic notation: [{}]", algebraicNotation);
          return null;
        }
      }

      String endingStr = matcher.group(5) == null ? "  " : matcher.group(5);
      Position endPos = new Position(ChessUtils.toFile(endingStr.charAt(0)), ChessUtils.toRank(endingStr.charAt(1)));
      char captureOrMate = matcher.group(6) == null ? Character.MIN_VALUE : matcher.group(6).charAt(0);
      boolean isMate = captureOrMate == '+';
      boolean isCheck = captureOrMate == '#';
      return new AlgebraicNotation(type, startPos, isCapture, endPos, isMate, isCheck, Status.MOVE);
    } else if (matcher.group(7) != null) {
      return new AlgebraicNotation(null, null, false, null, false, false, Status.WHITE_WIN);
    } else if (matcher.group(8) != null) {
      return new AlgebraicNotation(null, null, false, null, false, false, Status.BLACK_WIN);
    } else if (matcher.group(9) != null) {
      return new AlgebraicNotation(null, null, false, null, false, false, Status.DRAW);
    }
    return null;
  }

}
