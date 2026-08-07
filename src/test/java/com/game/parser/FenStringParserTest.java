package com.game.parser;

import com.game.ChessConstants;
import com.game.obj.FenString;
import com.game.piece.Piece;
import com.game.piece.Position;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FenStringParserTest {

  private final String VALID_PIECE_PLACEMENT = "2bqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

  private FenStringParser parser;

  @BeforeClass
  public void setUp() {
    parser = new FenStringParser();
  }

  @Test
  public void testParsingFullFenString() {
    FenString fenString = parser.parse(VALID_PIECE_PLACEMENT + " w - - 0 0");
    Assert.assertNotNull(fenString);
    Assert.assertNotNull(fenString.getPieces());
    Assert.assertEquals(fenString.getPieces().size(), 30);
    Assert.assertTrue(fenString.getActiveColor());
    Assert.assertNotNull(fenString.getCastlingRights());
    Assert.assertNull(fenString.getEnPassantTarget());
    Assert.assertEquals(fenString.getHalfMoveClock(), 0);
    Assert.assertEquals(fenString.getFullMoveCounter(), 0);
  }

  @Test
  public void testParsingDefaultPiecePlacement() {
    List<Piece> pieces = parser.parsePiecePlacement(ChessConstants.DEFAULT_PIECE_PLACEMENT);
    Assert.assertNotNull(pieces);
    Assert.assertEquals(pieces.size(), 32);
  }

  @Test
  public void testParsingValidPiecePlacement() {
    List<Piece> pieces = parser.parsePiecePlacement(VALID_PIECE_PLACEMENT);
    Assert.assertNotNull(pieces);
    Assert.assertEquals(pieces.size(), 30);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Piece placement is either null or empty")
  public void testParsingInvalidPiecePlacement() {
    List<Piece> pieces = parser.parsePiecePlacement(null);
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
    boolean[] castlingRights = parser.parseCastlingRights("KQkq");
    Assert.assertNotNull(castlingRights);
    Assert.assertEquals(castlingRights.length, 4);
    for (boolean castlingRight: castlingRights) {
      Assert.assertTrue(castlingRight);
    }
  }

  @Test
  public void testParsingPartialCastlingRights() {
    boolean[] castlingRights = parser.parseCastlingRights("Kq");
    Assert.assertNotNull(castlingRights);
    Assert.assertEquals(castlingRights.length, 4);
    Assert.assertTrue(castlingRights[0]);
    Assert.assertTrue(castlingRights[3]);
  }

  @Test
  public void testParsingNoCastlingRights() {
    boolean[] castlingRights = parser.parseCastlingRights("-");
    Assert.assertNotNull(castlingRights);
    Assert.assertEquals(castlingRights.length, 4);
    for (boolean castlingRight: castlingRights) {
      Assert.assertFalse(castlingRight);
    }
  }

  @Test
  public void testParsingEnPassantTarget() {
    Position enPassantTarget = parser.parseEnPassantTarget("a1");
    Assert.assertNotNull(enPassantTarget);
    Assert.assertEquals(enPassantTarget.getX(), 0);
    Assert.assertEquals(enPassantTarget.getY(), 7);
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