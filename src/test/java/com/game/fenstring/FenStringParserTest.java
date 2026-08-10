package com.game.fenstring;

import com.game.ChessConstants;
import com.game.Piece;
import com.game.Position;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FenStringParserTest {

  private FenStringParser parser;

  @BeforeClass
  public void setUp() {
    parser = new FenStringParser();
  }

  @Test
  public void testParsingDefaultPiecePlacement() {
    List<Piece> pieces = parser.parsePiecePlacement("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    Assert.assertNotNull(pieces);
    Assert.assertEquals(pieces.size(), 32);
  }

  @Test
  public void testParsingValidPiecePlacement() {
    String VALID_PIECE_PLACEMENT = "2bqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    List<Piece> pieces = parser.parsePiecePlacement(VALID_PIECE_PLACEMENT);
    Assert.assertNotNull(pieces);
    Assert.assertEquals(pieces.size(), 30);
  }

  @Test
  public void testParsingInvalidPiecePlacement() {
    List<Piece> pieces = parser.parsePiecePlacement(null);
    Assert.assertNull(pieces);
  }

  @Test
  public void testParsingActiveColorWhite() {
    boolean activeColor = parser.parseActiveColor(ChessConstants.ACTIVE_COLOR_WHITE);
    Assert.assertTrue(activeColor);
  }

  @Test
  public void testParsingActiveColorBlack() {
    boolean activeColor = parser.parseActiveColor(ChessConstants.ACTIVE_COLOR_BLACK);
    Assert.assertFalse(activeColor);
  }

  @Test
  public void testParsingActiveColorInvalid() {
    boolean activeColor = parser.parseActiveColor(null);
    Assert.assertTrue(activeColor);
  }

  @Test
  public void testParsingAllCastlingRights() {
    Map<CastlingRights, Boolean> castlingRights = parser.parseCastlingRights("KQkq");
    Assert.assertNotNull(castlingRights);
    Assert.assertEquals(castlingRights.size(), 4);
    for (boolean castlingRight: castlingRights.values()) {
      Assert.assertTrue(castlingRight);
    }
  }

  @Test
  public void testParsingPartialCastlingRights() {
    Map<CastlingRights, Boolean> castlingRights = parser.parseCastlingRights("Kq");
    Assert.assertNotNull(castlingRights);
    Assert.assertEquals(castlingRights.size(), 4);
    Assert.assertTrue(castlingRights.get(CastlingRights.WHITE_KINGSIDE));
    Assert.assertTrue(castlingRights.get(CastlingRights.BLACK_QUEENSIDE));
  }

  @Test
  public void testParsingNoCastlingRights() {
    Map<CastlingRights, Boolean> castlingRights = parser.parseCastlingRights("-");
    Assert.assertNotNull(castlingRights);
    Assert.assertEquals(castlingRights.size(), 4);
    for (boolean castlingRight: castlingRights.values()) {
      Assert.assertFalse(castlingRight);
    }
  }

  @Test
  public void testParsingEnPassantTarget() {
    Position enPassantTarget = parser.parseEnPassantTarget("a1");
    Assert.assertNotNull(enPassantTarget);
    Assert.assertEquals(enPassantTarget.getFile(), 0);
    Assert.assertEquals(enPassantTarget.getRank(), 7);
  }

  @Test
  public void testParsingEnPassantTargetInvalid() {
    Position enPassantTarget = parser.parseEnPassantTarget("a0");
    Assert.assertNull(enPassantTarget);
  }

  @Test
  public void testParsingHalfMoveClockOutOfRange() {
    int halfMoveClock = parser.parseHalfMoveClock("101");
    Assert.assertEquals(halfMoveClock, 0);
  }

  @Test
  public void testParsingInvalidHalfMoveClock() {
    int halfMoveClock = parser.parseHalfMoveClock("hello");
    Assert.assertEquals(halfMoveClock, 0);
  }

  @Test
  public void testParsingNegativeHalfMoveClock() {
    int halfMoveClock = parser.parseHalfMoveClock("-1");
    Assert.assertEquals(halfMoveClock, 0);
  }

  @Test
  public void testParsingHalfMoveClock() {
    int halfMoveClock = parser.parseHalfMoveClock("100");
    Assert.assertEquals(halfMoveClock, 100);
  }
}