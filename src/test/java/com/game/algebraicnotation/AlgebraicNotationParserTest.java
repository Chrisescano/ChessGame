package com.game.algebraicnotation;

import com.game.Position;
import com.game.algebraicnotation.AlgebraicNotation.Type;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AlgebraicNotationParserTest {

  private AlgebraicNotationParser parser;

  private static final Position DEFAULT = new Position(-1, -1);
  private static final Position MOVE_TO = new Position(4, 4);

  @BeforeClass
  public void setUp() {
    parser = new AlgebraicNotationParser();
  }

  @Test
  public void testParsingPawnMove() {
    AlgebraicNotation result = parser.parse("e4");
    AlgebraicNotation expected = new AlgebraicNotation(com.game.Piece.Type.PAWN, DEFAULT, false, MOVE_TO, false, false, Type.MOVE);
    Assert.assertNotNull(result);
    Assert.assertEquals(result, expected, "Expected " + expected + " but got " + result);
  }

  @Test
  public void testParsingQueenMove() {
    AlgebraicNotation result = parser.parse("Qe4");
    AlgebraicNotation expected = new AlgebraicNotation(com.game.Piece.Type.QUEEN, DEFAULT, false, MOVE_TO, false, false, Type.MOVE );
    Assert.assertNotNull(result);
    Assert.assertEquals(result, expected, "Expected " + expected + " but got " + result);
  }

  @Test
  public void testParsingPawnCapture() {
    AlgebraicNotation result = parser.parse("bxe4");
    AlgebraicNotation expected = new AlgebraicNotation(com.game.Piece.Type.PAWN, new Position(1, -1), true, MOVE_TO, false, false, Type.MOVE);
    Assert.assertNotNull(result);
    Assert.assertEquals(result, expected, "Expected " + expected + " but got " + result);
  }

  @Test
  public void testParsingIllegalPawnCapture() {
    AlgebraicNotation result = parser.parse("be4");
    Assert.assertNull(result);
  }

  @Test
  public void testParsingCaptureMove() {
    AlgebraicNotation result = parser.parse("Bxe4");
    AlgebraicNotation expected = new AlgebraicNotation(com.game.Piece.Type.BISHOP, DEFAULT, true, MOVE_TO, false, false, Type.MOVE);
    Assert.assertNotNull(result);
    Assert.assertEquals(result, expected, "Expected " + expected + " but got " + result);
  }

  @Test
  public void testParsingCheckMove() {
    AlgebraicNotation result = parser.parse("Qe4+");
    AlgebraicNotation expected = new AlgebraicNotation(com.game.Piece.Type.QUEEN, DEFAULT, false, MOVE_TO, true, false, Type.MOVE);
    Assert.assertNotNull(result);
    Assert.assertEquals(result, expected, "Expected " + expected + " but got " + result);
  }

  @Test
  public void testParsingMateMove() {
    AlgebraicNotation result = parser.parse("Qe4#");
    AlgebraicNotation expected = new AlgebraicNotation(com.game.Piece.Type.QUEEN, DEFAULT, false, MOVE_TO, false, true, Type.MOVE);
    Assert.assertNotNull(result);
    Assert.assertEquals(result, expected, "Expected " + expected + " but got " + result);
  }

  @Test
  public void testParsingTwoPiecesMoveToSameSquare() {
    AlgebraicNotation result = parser.parse("Qae4");
    AlgebraicNotation expected = new AlgebraicNotation(com.game.Piece.Type.QUEEN, new Position(0, -1), false, MOVE_TO, false, false, Type.MOVE);
    Assert.assertNotNull(result);
    Assert.assertEquals(result, expected, "Expected " + expected + " but got " + result);
  }

  @Test
  public void testParsingTwoPiecesMoveToSameSquareAndAreSameFile()  {
    AlgebraicNotation result = parser.parse("Q8e4");
    AlgebraicNotation expected = new AlgebraicNotation(com.game.Piece.Type.QUEEN, new Position(-1, 0), false, MOVE_TO, false, false, Type.MOVE);
    Assert.assertNotNull(result);
    Assert.assertEquals(result, expected, "Expected " + expected + " but got " + result);
  }

  @Test
  public void testWhiteWin() {
    AlgebraicNotation result = parser.parse("1-0");
    AlgebraicNotation expected = new AlgebraicNotation(null, null, false, null, false, false, Type.WHITE_WIN);
    Assert.assertNotNull(result);
    Assert.assertEquals(result, expected, "Expected " + expected + " but got " + result);
  }

  @Test
  public void testBlackWin() {
    AlgebraicNotation result = parser.parse("0-1");
    AlgebraicNotation expected = new AlgebraicNotation(null, null, false, null, false, false, Type.BLACK_WIN);
    Assert.assertNotNull(result);
    Assert.assertEquals(result, expected, "Expected " + expected + " but got " + result);
  }

  @Test
  public void testDraw() {
    AlgebraicNotation result = parser.parse("1/2-1/2");
    AlgebraicNotation expected = new AlgebraicNotation(null, null, false, null, false, false, Type.DRAW);
    Assert.assertNotNull(result);
    Assert.assertEquals(result, expected, "Expected " + expected + " but got " + result);
  }

}