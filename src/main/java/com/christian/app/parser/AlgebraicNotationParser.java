package com.christian.app.parser;

import com.christian.app.game.GameConstants;
import com.christian.app.game.GameUtil;
import com.christian.app.piece.Type;
import com.christian.app.util.AlgebraicNotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgebraicNotationParser {

  private static final Pattern pattern = Pattern.compile(GameConstants.ALGEBRAIC_NOTATION_PATTERN);

  public AlgebraicNotation parse(final String value) {
    if (value == null || value.isEmpty()) {
      return null;
    }

    Matcher matcher = pattern.matcher(value);
    if (matcher.matches()) {
      Type type = Type.toType(getCharOrDefault(matcher, GameConstants.ALG_NOT_TYPE_GROUP));
      int startFile = GameUtil.toFileIndex(getCharOrDefault(matcher, GameConstants.ALG_NOT_START_FILE_GROUP));
      int startRank = GameUtil.toRankIndex(getCharOrDefault(matcher, GameConstants.ALG_NOT_START_RANK_GROUP));
      int endFile = GameUtil.toFileIndex(getCharOrDefault(matcher, GameConstants.ALG_NOT_END_FILE_GROUP));
      int endRank = GameUtil.toRankIndex(getCharOrDefault(matcher, GameConstants.ALG_NOT_END_RANK_GROUP));
      char capture = getCharOrDefault(matcher, GameConstants.ALG_NOT_CAPTURE_GROUP);
      char checkMate = getCharOrDefault(matcher, GameConstants.ALG_NOT_CHECK_MATE_GROUP);

      return new AlgebraicNotation(
          type == null ? Type.PAWN : type,
          startFile,
          startRank,
          endFile,
          endRank,
          capture == GameConstants.ALG_NOT_CAPTURE,
          checkMate == GameConstants.ALG_NOT_CHECK,
          checkMate == GameConstants.ALG_NOT_MATE
      );
    }
    return null;
  }

  private char getCharOrDefault(Matcher matcher, int group) {
    String groupValue = matcher.group(group);
    return groupValue == null ? Character.MIN_VALUE : groupValue.charAt(0);
  }
}
