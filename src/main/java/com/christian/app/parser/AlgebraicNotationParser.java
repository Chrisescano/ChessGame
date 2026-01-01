package com.christian.app.parser;

import com.christian.app.game.AlgebraicNotation;
import com.christian.app.game.util.GameConstants;
import com.christian.app.game.util.GameUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgebraicNotationParser {

  private static final Pattern PATTERN = Pattern.compile(GameConstants.ALGEBRAIC_NOTATION_PATTERN);

  public AlgebraicNotation parseAlgebraicNotation(String algebraicNotationString) {
    if (algebraicNotationString == null || algebraicNotationString.length() < 2) {
      return null;
    }

    AlgebraicNotation algebraicNotation = new AlgebraicNotation();

    switch (algebraicNotationString) {
      case GameConstants.WHITE_WINS -> algebraicNotation.setWhiteWin(true);
      case GameConstants.BLACK_WINS -> algebraicNotation.setBlackWin(true);
      case GameConstants.DRAW -> algebraicNotation.setDraw(true);
      case GameConstants.CASTLING_KINGSIDE -> algebraicNotation.setKingsideCastle(true);
      case GameConstants.CASTLING_QUEENSIDE -> algebraicNotation.setQueensideCastle(true);
      default -> parseAndPopulate(algebraicNotationString, algebraicNotation);
    }

    return algebraicNotation;
  }

  public void parseAndPopulate(String algNotStr, AlgebraicNotation notation) {
    Matcher matcher = PATTERN.matcher(algNotStr);
    if (!matcher.matches()) {
      return;
    }

    String symbol = matcher.group(GameConstants.ALG_NOT_PIECE);
    String optFile = matcher.group(GameConstants.ALG_NOT_OPT_FILE);
    String optRank = matcher.group(GameConstants.ALG_NOT_OPT_RANK);
    String capture = matcher.group(GameConstants.ALG_NOT_CAPTURE);
    String destFile = matcher.group(GameConstants.ALG_NOT_DEST_FILE);
    String destRank = matcher.group(GameConstants.ALG_NOT_DEST_RANK);
    String checkOrMate = matcher.group(GameConstants.ALG_NOT_CHECK_OR_MATE);

    if (symbol != null && !symbol.isEmpty()) {
      notation.setSymbol(symbol.charAt(0));
    }

    if (optFile != null && !optFile.isEmpty()) {
      notation.setStartX(GameUtil.toFileIndex(optFile.charAt(0)));
    }

    if (optRank != null && !optRank.isEmpty()) {
      notation.setStartY(GameUtil.toRankIndex(optRank.charAt(0)));
    }

    if (capture != null && !capture.isEmpty()) {
      notation.setCapture(true);
    }

    notation.setEndX(GameUtil.toFileIndex(destFile.charAt(0)));
    notation.setEndY(GameUtil.toRankIndex(destRank.charAt(0)));

    if (checkOrMate != null && !checkOrMate.isEmpty()) {
      if (checkOrMate.equals(GameConstants.ALGEBRAIC_NOTATION_CHECK)) {
        notation.setCheck(true);
      } else if (checkOrMate.equals(GameConstants.ALGEBRAIC_NOTATION_MATE)) {
        notation.setMate(true);
      }
    }
  }
}
