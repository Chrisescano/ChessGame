package com.christian.app.parser;

import com.christian.app.game.AlgebraicNotation;
import com.christian.app.game.Position;
import com.christian.app.piece.Type;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlgebraicNotationParserTest {

  private AlgebraicNotationParser parser = new AlgebraicNotationParser();

  @Test
  public void testParsingWhiteWins() {
    AlgebraicNotation notation = parser.parse("1-0");
    Assert.assertNotNull(notation);
    Assert.assertEquals(notation.winStatus(), 1);
  }

  @Test
  public void testParsingBlackWins() {
    AlgebraicNotation notation = parser.parse("0-1");
    Assert.assertNotNull(notation);
    Assert.assertEquals(notation.winStatus(), 2);
  }

  @Test
  public void testParsingDraw() {
    AlgebraicNotation notation = parser.parse("1/2-1/2");
    Assert.assertNotNull(notation);
    Assert.assertEquals(notation.winStatus(), 3);
  }
  
  @Test
  public void testParsingPawnMove() {
    AlgebraicNotation notation = parser.parse("e4");
    Assert.assertNotNull(notation);
    Assert.assertEquals(notation.type(), Type.PAWN);
    Assert.assertEquals(notation.end(), new Position(4, 4));
  }

  @Test
  public void testParsingKnightCaptureMove() {
    AlgebraicNotation notation = parser.parse("Nxe4");
    Assert.assertNotNull(notation);
    Assert.assertEquals(notation.type(), Type.KNIGHT);
    Assert.assertTrue(notation.isCapture());
  }

  @Test
  public void testParsingAmbiguousKnightCheck() {
    AlgebraicNotation notation = parser.parse("Nbe4+");
    Assert.assertNotNull(notation);
    Assert.assertEquals(notation.start(), new Position(1, -1));
    Assert.assertTrue(notation.isCheck());
  }

}