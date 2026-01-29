package com.christian.app.parser;

import com.christian.app.game.AlgebraicNotation;
import com.christian.app.game.GameConstants;
import com.christian.app.game.GameUtil;
import com.christian.app.game.Position;
import com.christian.app.piece.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgebraicNotationParser {

  private final Pattern pattern = Pattern.compile(GameConstants.ALGEBRAIC_NOTATION_PATTERN);

  public AlgebraicNotation parse(String algebraicNotationString) {
    if (algebraicNotationString == null || algebraicNotationString.isEmpty()) {
      return null;
    }

    switch (algebraicNotationString) {
      case GameConstants.WHITE_WINS -> {
        return new AlgebraicNotation(null, null, null, 1, false, false, false);
      }
      case GameConstants.BLACK_WINS -> {
        return new AlgebraicNotation(null, null, null, 2, false, false, false);
      }
      case GameConstants.DRAW -> {
        return new AlgebraicNotation(null, null, null, 3, false, false, false);
      }
    }

    Matcher matcher = pattern.matcher(algebraicNotationString);
    if (matcher.matches()) {
      Type type = Type.toType(matcher.group(1) == null ? GameConstants.WHITE_PAWN_SYMBOL : matcher.group(1).charAt(0));
      int startFile = GameUtil.toFileIndex(matcher.group(2));
      int startRank = GameUtil.toRankIndex(matcher.group(3));
      Position start = new Position(startFile, startRank);
      boolean isCapture = matcher.group(4) != null;
      Position end = GameUtil.toPosition(matcher.group(5));
      boolean isCheck = matcher.group(6) != null && matcher.group(6).equals("+");
      boolean isMate = matcher.group(6) != null && matcher.group(6).equals("#");
      return new AlgebraicNotation(type, start, end, 0, isCapture, isCheck, isMate);
    }
    return null;
  }

}
